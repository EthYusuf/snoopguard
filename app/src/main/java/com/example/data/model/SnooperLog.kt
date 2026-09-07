package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a snooper / unauthorized glance incident captured locally on the device.
 */
@Entity(tableName = "snooper_logs")
data class SnooperLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val photoPath: String,
    val timestamp: Long = System.currentTimeMillis(),
    val tag: String, // e.g. "Bilinmeyen Kişi", "Şüpheli Bakış", "Tuzak Ekranı Yakalaması"
    val confidence: Int, // e.g. 94%
    val faceCount: Int = 1,
    val headEulerY: Float = 0f, // horizontal gaze angle
    val headEulerZ: Float = 0f, // tilt angle
    val smilingProbability: Float = 0f,
    val note: String = "Ekrana doğrudan bakarken yerel AI tarafından tespit edildi"
)
