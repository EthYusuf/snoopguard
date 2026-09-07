package com.example.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import com.example.data.db.SnooperDao
import com.example.data.model.OwnerProfile
import com.example.data.model.SnooperLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SnooperRepository(
    private val context: Context,
    private val snooperDao: SnooperDao
) {
    private val prefs = context.getSharedPreferences("snoopguard_prefs", Context.MODE_PRIVATE)
    private val imagesDir: File
        get() {
            val dir = File(context.filesDir, "snooper_snapshots")
            if (!dir.exists()) dir.mkdirs()
            return dir
        }

    val allLogs: Flow<List<SnooperLog>> = snooperDao.getAllLogs()
    val logCount: Flow<Int> = snooperDao.getLogCount()

    // --- User Consent / Disclaimer Preferences ---
    fun isDisclaimerAccepted(): Boolean {
        return prefs.getBoolean("disclaimer_accepted", false)
    }

    fun setDisclaimerAccepted(accepted: Boolean) {
        prefs.edit().putBoolean("disclaimer_accepted", accepted).apply()
    }

    // --- Owner Profile Persistence ---
    fun getOwnerProfile(): OwnerProfile {
        val isEnrolled = prefs.getBoolean("owner_enrolled", false)
        val name = prefs.getString("owner_name", "Cihaz Sahibi") ?: "Cihaz Sahibi"
        val date = prefs.getLong("owner_enrolled_date", 0L)
        val photoPath = prefs.getString("owner_photo_path", null)
        val eyeRatio = prefs.getFloat("owner_eye_ratio", 0.35f)
        val faceProp = prefs.getFloat("owner_face_prop", 1.3f)
        val landmarks = prefs.getInt("owner_landmarks", 0)

        return OwnerProfile(
            isEnrolled = isEnrolled,
            ownerName = name,
            enrolledDate = date,
            photoPath = photoPath,
            eyeDistanceRatio = eyeRatio,
            faceProportion = faceProp,
            enrolledLandmarksCount = landmarks
        )
    }

    fun saveOwnerProfile(profile: OwnerProfile) {
        prefs.edit()
            .putBoolean("owner_enrolled", profile.isEnrolled)
            .putString("owner_name", profile.ownerName)
            .putLong("owner_enrolled_date", profile.enrolledDate)
            .putString("owner_photo_path", profile.photoPath)
            .putFloat("owner_eye_ratio", profile.eyeDistanceRatio)
            .putFloat("owner_face_prop", profile.faceProportion)
            .putInt("owner_landmarks", profile.enrolledLandmarksCount)
            .apply()
    }

    fun clearOwnerProfile() {
        val currentPhoto = prefs.getString("owner_photo_path", null)
        if (currentPhoto != null) {
            val file = File(currentPhoto)
            if (file.exists()) file.delete()
        }
        prefs.edit()
            .remove("owner_enrolled")
            .remove("owner_name")
            .remove("owner_enrolled_date")
            .remove("owner_photo_path")
            .remove("owner_eye_ratio")
            .remove("owner_face_prop")
            .remove("owner_landmarks")
            .apply()
    }

    // --- Sensitivity & Settings ---
    fun getSensitivity(): Float = prefs.getFloat("guard_sensitivity", 0.75f)
    fun setSensitivity(value: Float) = prefs.edit().putFloat("guard_sensitivity", value).apply()

    fun isVibrationAlertEnabled(): Boolean = prefs.getBoolean("vibrate_alert", true)
    fun setVibrationAlert(enabled: Boolean) = prefs.edit().putBoolean("vibrate_alert", enabled).apply()

    // --- Saving Captured Snapshot ---
    suspend fun saveCapturedBitmap(
        bitmap: Bitmap,
        tag: String,
        confidence: Int,
        headEulerY: Float = 0f,
        headEulerZ: Float = 0f,
        note: String = "Ekrana doğrudan bakarken yerel AI tarafından tespit edildi"
    ): SnooperLog = withContext(Dispatchers.IO) {
        val fileName = "snoop_${System.currentTimeMillis()}.jpg"
        val targetFile = File(imagesDir, fileName)

        // Draw local security timestamp watermark on bitmap
        val watermarked = addWatermarkToBitmap(bitmap)

        FileOutputStream(targetFile).use { out ->
            watermarked.compress(Bitmap.CompressFormat.JPEG, 90, out)
        }

        val log = SnooperLog(
            photoPath = targetFile.absolutePath,
            timestamp = System.currentTimeMillis(),
            tag = tag,
            confidence = confidence,
            faceCount = 1,
            headEulerY = headEulerY,
            headEulerZ = headEulerZ,
            note = note
        )

        val id = snooperDao.insertLog(log)
        log.copy(id = id)
    }

    // Save registered owner reference photo
    suspend fun saveOwnerBitmap(bitmap: Bitmap): String = withContext(Dispatchers.IO) {
        val file = File(imagesDir, "owner_profile_${System.currentTimeMillis()}.jpg")
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)
        }
        file.absolutePath
    }

    // Create a realistic test simulation snapshot if running in emulator or camera denied
    suspend fun createSimulatedCapture(tag: String = "Şüpheli Bakış (Simülasyon)"): SnooperLog = withContext(Dispatchers.IO) {
        val bmp = Bitmap.createBitmap(480, 640, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)
        canvas.drawColor(Color.rgb(24, 28, 38))

        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(78, 128, 238)
            style = Paint.Style.STROKE
            strokeWidth = 4f
        }

        // Draw simulated silhouette
        canvas.drawCircle(240f, 220f, 90f, paint)
        canvas.drawOval(RectF(100f, 340f, 380f, 600f), paint)

        // Face grid
        paint.color = Color.argb(120, 16, 185, 129)
        paint.strokeWidth = 2f
        canvas.drawLine(240f, 130f, 240f, 310f, paint)
        canvas.drawLine(150f, 220f, 330f, 220f, paint)

        // Eyes
        paint.style = Paint.Style.FILL
        paint.color = Color.rgb(239, 68, 68)
        canvas.drawCircle(200f, 210f, 10f, paint)
        canvas.drawCircle(280f, 210f, 10f, paint)

        // Text
        paint.color = Color.WHITE
        paint.textSize = 28f
        paint.textAlign = Paint.Align.CENTER
        canvas.drawText("YABANCI YÜZ TESPİTİ", 240f, 420f, paint)

        paint.textSize = 20f
        paint.color = Color.rgb(180, 190, 210)
        canvas.drawText("Yerel AI Gözcü Tarafından Kaydedildi", 240f, 460f, paint)

        saveCapturedBitmap(
            bitmap = bmp,
            tag = tag,
            confidence = (88..98).random(),
            headEulerY = (-15..20).random().toFloat(),
            headEulerZ = 2f,
            note = "Cihaz sahibinden farklı bir yüz ekranı incelerken yakalandı."
        )
    }

    private fun addWatermarkToBitmap(original: Bitmap): Bitmap {
        val mutable = original.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutable)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.argb(180, 0, 0, 0)
            style = Paint.Style.FILL
        }

        // Bottom banner background
        val bannerHeight = 44f
        canvas.drawRect(0f, mutable.height - bannerHeight, mutable.width.toFloat(), mutable.height.toFloat(), paint)

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.rgb(239, 68, 68) // Red dot
            style = Paint.Style.FILL
        }
        canvas.drawCircle(18f, mutable.height - (bannerHeight / 2), 6f, textPaint)

        textPaint.color = Color.WHITE
        textPaint.textSize = 18f
        val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val dateStr = "GÖZCÜ YEREL KAYIT | " + sdf.format(Date())
        canvas.drawText(dateStr, 34f, mutable.height - 15f, textPaint)

        return mutable
    }

    // --- Deletion ---
    suspend fun deleteLog(log: SnooperLog) = withContext(Dispatchers.IO) {
        val file = File(log.photoPath)
        if (file.exists()) file.delete()
        snooperDao.deleteLog(log)
    }

    suspend fun clearAllLogs() = withContext(Dispatchers.IO) {
        val files = imagesDir.listFiles()
        files?.forEach { it.delete() }
        snooperDao.clearAllLogs()
    }
}
