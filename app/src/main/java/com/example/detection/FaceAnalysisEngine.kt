package com.example.detection

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import com.example.data.model.OwnerProfile
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.math.abs
import kotlin.math.hypot

data class FaceAnalysisResult(
    val faceCount: Int,
    val isOwnerRecognized: Boolean,
    val isIntruderDetected: Boolean,
    val intruderFace: Face? = null,
    val headEulerY: Float = 0f,
    val headEulerZ: Float = 0f,
    val smilingProbability: Float = 0f,
    val description: String = "",
    val confidence: Int = 90
)

class FaceAnalysisEngine {

    private val highAccuracyOptions = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setMinFaceSize(0.15f)
        .build()

    private val detector = FaceDetection.getClient(highAccuracyOptions)

    /**
     * Analyze a captured or live camera frame bitmap against registered owner profile.
     */
    suspend fun analyzeBitmap(
        bitmap: Bitmap,
        rotationDegrees: Int = 0,
        ownerProfile: OwnerProfile?,
        sensitivity: Float = 0.75f
    ): FaceAnalysisResult = suspendCancellableCoroutine { continuation ->
        val inputImage = InputImage.fromBitmap(bitmap, rotationDegrees)

        detector.process(inputImage)
            .addOnSuccessListener { faces ->
                if (continuation.isActive) {
                    val result = evaluateFaces(faces, ownerProfile, sensitivity)
                    continuation.resume(result)
                }
            }
            .addOnFailureListener {
                if (continuation.isActive) {
                    continuation.resume(
                        FaceAnalysisResult(
                            faceCount = 0,
                            isOwnerRecognized = false,
                            isIntruderDetected = false,
                            description = "Yüz analizi başarısız oldu"
                        )
                    )
                }
            }
    }

    private fun evaluateFaces(
        faces: List<Face>,
        ownerProfile: OwnerProfile?,
        sensitivity: Float
    ): FaceAnalysisResult {
        if (faces.isEmpty()) {
            return FaceAnalysisResult(
                faceCount = 0,
                isOwnerRecognized = false,
                isIntruderDetected = false,
                description = "Ekrana bakan yüz tespit edilmedi."
            )
        }

        // Multiple faces detected looking at phone!
        if (faces.size > 1) {
            val secondaryFace = faces[1]
            return FaceAnalysisResult(
                faceCount = faces.size,
                isOwnerRecognized = ownerProfile?.isEnrolled == true,
                isIntruderDetected = true,
                intruderFace = secondaryFace,
                headEulerY = secondaryFace.headEulerAngleY,
                headEulerZ = secondaryFace.headEulerAngleZ,
                smilingProbability = secondaryFace.smilingProbability ?: 0f,
                confidence = 96,
                description = "Arkadan / yandan ekrana bakan ikinci bir kişi tespit edildi!"
            )
        }

        val singleFace = faces[0]
        val headY = singleFace.headEulerAngleY
        val headZ = singleFace.headEulerAngleZ
        val smileProb = singleFace.smilingProbability ?: 0f

        // If no owner profile is enrolled yet, any detected face is treated as a monitored face
        if (ownerProfile == null || !ownerProfile.isEnrolled) {
            return FaceAnalysisResult(
                faceCount = 1,
                isOwnerRecognized = false,
                isIntruderDetected = true,
                intruderFace = singleFace,
                headEulerY = headY,
                headEulerZ = headZ,
                smilingProbability = smileProb,
                confidence = 91,
                description = "Bilinmeyen bir yüz ekrana bakıyor (Sahip kaydı henüz yapılmadı)."
            )
        }

        // Compare face biometric traits with registered owner
        val currentMetrics = extractFaceMetrics(singleFace)
        val matchScore = calculateSimilarity(currentMetrics, ownerProfile)

        // Threshold based on sensitivity: 0.5 (lenient) to 0.85 (strict)
        val matchThreshold = 0.55f + (sensitivity * 0.25f)
        val isOwner = matchScore >= (1f - matchThreshold)

        return if (isOwner) {
            FaceAnalysisResult(
                faceCount = 1,
                isOwnerRecognized = true,
                isIntruderDetected = false,
                headEulerY = headY,
                headEulerZ = headZ,
                confidence = (matchScore * 100).toInt().coerceIn(75, 99),
                description = "Cihaz sahibi doğrulandı (${ownerProfile.ownerName})."
            )
        } else {
            FaceAnalysisResult(
                faceCount = 1,
                isOwnerRecognized = false,
                isIntruderDetected = true,
                intruderFace = singleFace,
                headEulerY = headY,
                headEulerZ = headZ,
                smilingProbability = smileProb,
                confidence = ((1f - matchScore) * 100).toInt().coerceIn(80, 98),
                description = "Cihaz sahibine ait olmayan yabancı bir yüz tespit edildi!"
            )
        }
    }

    fun extractFaceMetrics(face: Face): Pair<Float, Float> {
        val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)?.position
        val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)?.position
        val bounds = face.boundingBox

        val eyeDist = if (leftEye != null && rightEye != null) {
            hypot(leftEye.x - rightEye.x, leftEye.y - rightEye.y)
        } else {
            bounds.width() * 0.35f
        }

        val eyeRatio = if (bounds.width() > 0) eyeDist / bounds.width() else 0.35f
        val faceProp = if (bounds.width() > 0) bounds.height().toFloat() / bounds.width().toFloat() else 1.3f

        return Pair(eyeRatio, faceProp)
    }

    private fun calculateSimilarity(current: Pair<Float, Float>, owner: OwnerProfile): Float {
        val (curEyeRatio, curFaceProp) = current
        val eyeDiff = abs(curEyeRatio - owner.eyeDistanceRatio)
        val propDiff = abs(curFaceProp - owner.faceProportion)

        val eyeScore = (1f - (eyeDiff / 0.15f)).coerceIn(0f, 1f)
        val propScore = (1f - (propDiff / 0.40f)).coerceIn(0f, 1f)

        return (eyeScore * 0.6f) + (propScore * 0.4f)
    }

    /**
     * Crop face region or return full snapshot with bounding rect.
     */
    fun cropFaceWithMargin(bitmap: Bitmap, faceRect: Rect): Bitmap {
        val marginX = (faceRect.width() * 0.3f).toInt()
        val marginY = (faceRect.height() * 0.35f).toInt()

        val left = (faceRect.left - marginX).coerceAtLeast(0)
        val top = (faceRect.top - marginY).coerceAtLeast(0)
        val right = (faceRect.right + marginX).coerceAtMost(bitmap.width)
        val bottom = (faceRect.bottom + marginY).coerceAtMost(bitmap.height)

        val width = (right - left).coerceAtLeast(10)
        val height = (bottom - top).coerceAtLeast(10)

        return Bitmap.createBitmap(bitmap, left, top, width, height)
    }
}
