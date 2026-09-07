package com.example.data.model

/**
 * Represents the registered owner face profile used for local comparison.
 */
data class OwnerProfile(
    val isEnrolled: Boolean = false,
    val ownerName: String = "Cihaz Sahibi",
    val enrolledDate: Long = 0L,
    val photoPath: String? = null,
    val eyeDistanceRatio: Float = 0f,
    val faceProportion: Float = 0f,
    val enrolledLandmarksCount: Int = 0
)
