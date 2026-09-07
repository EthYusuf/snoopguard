package com.example.ui

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.db.AppDatabase
import com.example.data.model.OwnerProfile
import com.example.data.model.SnooperLog
import com.example.data.repository.SnooperRepository
import com.example.detection.FaceAnalysisEngine
import com.example.detection.FaceAnalysisResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class GuardMode(val title: String, val description: String) {
    LIVE_SHIELD("Canlı Kalkan", "Uygulama açıkken ekrana bakanları anlık analiz eder"),
    DECOY_TRAP("Tuzak Ekranı", "Sahte kilit ekranı ile gözetleyen kişiyi gizlice yakalar"),
    SENSITIVE_STEALTH("Gizli Gözcü", "Minimum pil tüketimi ile şüpheli bakışları kaydeder")
}

data class SnoopGuardUiState(
    val disclaimerAccepted: Boolean = false,
    val isCameraPermissionGranted: Boolean = false,
    val isCameraActive: Boolean = false,
    val isMonitoringActive: Boolean = false,
    val guardMode: GuardMode = GuardMode.LIVE_SHIELD,
    val ownerProfile: OwnerProfile = OwnerProfile(),
    val currentFaceResult: FaceAnalysisResult? = null,
    val activeAlert: SnooperLog? = null,
    val isAnalyzingFrame: Boolean = false,
    val sensitivity: Float = 0.75f,
    val vibrateOnAlert: Boolean = true,
    val showRegisterOwnerDialog: Boolean = false,
    val showDecoyTrapScreen: Boolean = false,
    val showDisclaimerDialog: Boolean = false,
    val isSimulating: Boolean = false
)

class SnoopGuardViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: SnooperRepository
    private val faceEngine = FaceAnalysisEngine()

    private val _uiState = MutableStateFlow(SnoopGuardUiState())
    val uiState: StateFlow<SnoopGuardUiState> = _uiState.asStateFlow()

    val logs: StateFlow<List<SnooperLog>>

    init {
        val db = AppDatabase.getInstance(application)
        repository = SnooperRepository(application, db.snooperDao())

        logs = repository.allLogs.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        // Load persisted state
        val isConsentGiven = repository.isDisclaimerAccepted()
        val owner = repository.getOwnerProfile()
        val sens = repository.getSensitivity()
        val vib = repository.isVibrationAlertEnabled()

        _uiState.value = _uiState.value.copy(
            disclaimerAccepted = isConsentGiven,
            showDisclaimerDialog = !isConsentGiven,
            ownerProfile = owner,
            sensitivity = sens,
            vibrateOnAlert = vib
        )
    }

    // --- User Consent ---
    fun acceptDisclaimer() {
        repository.setDisclaimerAccepted(true)
        _uiState.value = _uiState.value.copy(
            disclaimerAccepted = true,
            showDisclaimerDialog = false
        )
    }

    fun openDisclaimer() {
        _uiState.value = _uiState.value.copy(showDisclaimerDialog = true)
    }

    fun dismissDisclaimer() {
        _uiState.value = _uiState.value.copy(showDisclaimerDialog = false)
    }

    fun revokeDisclaimer() {
        repository.setDisclaimerAccepted(false)
        _uiState.value = _uiState.value.copy(
            disclaimerAccepted = false,
            isMonitoringActive = false,
            isCameraActive = false,
            showDisclaimerDialog = true
        )
    }

    // --- Camera & Shield Management ---
    fun setCameraPermission(granted: Boolean) {
        _uiState.value = _uiState.value.copy(
            isCameraPermissionGranted = granted,
            isCameraActive = granted && _uiState.value.isMonitoringActive
        )
    }

    fun toggleCamera(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isCameraActive = enabled)
    }

    fun toggleGuard(enabled: Boolean) {
        if (!_uiState.value.disclaimerAccepted) {
            _uiState.value = _uiState.value.copy(showDisclaimerDialog = true)
            return
        }

        _uiState.value = _uiState.value.copy(
            isMonitoringActive = enabled,
            isCameraActive = enabled && _uiState.value.isCameraPermissionGranted
        )
    }

    fun setGuardMode(mode: GuardMode) {
        _uiState.value = _uiState.value.copy(guardMode = mode)
    }

    // --- Owner Face Registration ---
    fun setShowRegisterOwnerDialog(show: Boolean) {
        _uiState.value = _uiState.value.copy(showRegisterOwnerDialog = show)
    }

    fun registerOwnerFace(bitmap: Bitmap, ownerName: String) {
        viewModelScope.launch {
            val result = faceEngine.analyzeBitmap(
                bitmap = bitmap,
                ownerProfile = null,
                sensitivity = _uiState.value.sensitivity
            )

            val photoPath = repository.saveOwnerBitmap(bitmap)
            val faceMetrics = if (result.intruderFace != null) {
                faceEngine.extractFaceMetrics(result.intruderFace)
            } else {
                Pair(0.35f, 1.3f)
            }

            val profile = OwnerProfile(
                isEnrolled = true,
                ownerName = ownerName.ifBlank { "Cihaz Sahibi" },
                enrolledDate = System.currentTimeMillis(),
                photoPath = photoPath,
                eyeDistanceRatio = faceMetrics.first,
                faceProportion = faceMetrics.second,
                enrolledLandmarksCount = 4
            )

            repository.saveOwnerProfile(profile)
            _uiState.value = _uiState.value.copy(
                ownerProfile = profile,
                showRegisterOwnerDialog = false
            )
        }
    }

    fun resetOwnerFace() {
        repository.clearOwnerProfile()
        _uiState.value = _uiState.value.copy(
            ownerProfile = OwnerProfile(isEnrolled = false)
        )
    }

    // --- Real-time Face Frame Processing ---
    fun onCameraFrameCaptured(bitmap: Bitmap) {
        if (_uiState.value.isAnalyzingFrame) return

        viewModelScope.launch(Dispatchers.Default) {
            _uiState.value = _uiState.value.copy(isAnalyzingFrame = true)
            try {
                val currentOwner = _uiState.value.ownerProfile
                val result = faceEngine.analyzeBitmap(
                    bitmap = bitmap,
                    ownerProfile = if (currentOwner.isEnrolled) currentOwner else null,
                    sensitivity = _uiState.value.sensitivity
                )

                _uiState.value = _uiState.value.copy(currentFaceResult = result)

                // If an unknown snooper or intruder is spotted!
                if (result.isIntruderDetected) {
                    val tag = if (result.faceCount > 1) {
                        "Arkadan Bakan Biri"
                    } else if (!currentOwner.isEnrolled) {
                        "Bilinmeyen Kişi (Ekrana Bakış)"
                    } else {
                        "Yetkisiz Yüz / Gözetleme"
                    }

                    // Save snapshot locally to private app storage & Room DB
                    val log = repository.saveCapturedBitmap(
                        bitmap = bitmap,
                        tag = tag,
                        confidence = result.confidence,
                        headEulerY = result.headEulerY,
                        headEulerZ = result.headEulerZ,
                        note = result.description
                    )

                    triggerAlert(log)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _uiState.value = _uiState.value.copy(isAnalyzingFrame = false)
            }
        }
    }

    // --- Decoy Trap Mode ---
    fun openDecoyTrap() {
        _uiState.value = _uiState.value.copy(showDecoyTrapScreen = true)
    }

    fun closeDecoyTrap() {
        _uiState.value = _uiState.value.copy(showDecoyTrapScreen = false)
    }

    fun onDecoyTrapTriggered(bitmap: Bitmap?) {
        viewModelScope.launch {
            if (bitmap != null) {
                val log = repository.saveCapturedBitmap(
                    bitmap = bitmap,
                    tag = "Tuzak Ekranı Yakalaması",
                    confidence = 95,
                    note = "Tuzak kilit ekranına dokunulduğunda gizlice yakalandı"
                )
                triggerAlert(log)
            } else {
                val log = repository.createSimulatedCapture("Tuzak Ekranı Yakalaması")
                triggerAlert(log)
            }
        }
    }

    // --- Test Simulation (for offline / emulator testing) ---
    fun simulateSnooperIncident() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isSimulating = true)
            val log = repository.createSimulatedCapture(
                tag = listOf("Şüpheli Bakış", "Arkadan Bakan Kişi", "Yetkisiz İnceleme").random()
            )
            triggerAlert(log)
            _uiState.value = _uiState.value.copy(isSimulating = false)
        }
    }

    private fun triggerAlert(log: SnooperLog) {
        _uiState.value = _uiState.value.copy(activeAlert = log)
        if (_uiState.value.vibrateOnAlert) {
            vibrateDevice()
        }
    }

    fun dismissAlert() {
        _uiState.value = _uiState.value.copy(activeAlert = null)
    }

    // --- Log Management ---
    fun deleteLog(log: SnooperLog) {
        viewModelScope.launch {
            repository.deleteLog(log)
        }
    }

    fun clearAllLogs() {
        viewModelScope.launch {
            repository.clearAllLogs()
        }
    }

    // --- Settings ---
    fun setSensitivity(value: Float) {
        repository.setSensitivity(value)
        _uiState.value = _uiState.value.copy(sensitivity = value)
    }

    fun setVibrateAlert(enabled: Boolean) {
        repository.setVibrationAlert(enabled)
        _uiState.value = _uiState.value.copy(vibrateOnAlert = enabled)
    }

    private fun vibrateDevice() {
        try {
            val context = getApplication<Application>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createOneShot(250, VibrationEffect.DEFAULT_AMPLITUDE)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                @Suppress("DEPRECATION")
                vibrator?.vibrate(250)
            }
        } catch (_: Exception) {}
    }
}
