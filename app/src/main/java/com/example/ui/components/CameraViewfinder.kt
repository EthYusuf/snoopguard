package com.example.ui.components

import android.Manifest
import android.graphics.Bitmap
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.detection.CameraManager
import com.example.detection.FaceAnalysisResult
import com.example.ui.theme.AlertRed
import com.example.ui.theme.CyberDarkBg
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.SecurityIndigo
import com.example.ui.theme.WarningAmber
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraViewfinder(
    isCameraActive: Boolean,
    isMonitoringActive: Boolean,
    currentFaceResult: FaceAnalysisResult?,
    onCameraPermissionChanged: (Boolean) -> Unit,
    onFrameCaptured: (Bitmap) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()
    var isExpanded by remember { mutableStateOf(true) }
    var previewViewRef by remember { mutableStateOf<PreviewView?>(null) }
    val cameraManager = remember { CameraManager(context) }

    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    LaunchedEffect(cameraPermissionState.status.isGranted) {
        onCameraPermissionChanged(cameraPermissionState.status.isGranted)
    }

    // Scanning line animation
    val infiniteTransition = rememberInfiniteTransition(label = "scanner")
    val scanLineProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "scan_line"
    )

    // Periodic frame analysis loop while monitoring is active and camera is bound
    LaunchedEffect(isMonitoringActive, isCameraActive, cameraPermissionState.status.isGranted) {
        if (isMonitoringActive && isCameraActive && cameraPermissionState.status.isGranted) {
            while (true) {
                delay(1200) // Analyze a frame every 1.2s for optimal battery and smooth detection
                try {
                    val bitmap = cameraManager.captureSnapshot()
                    if (bitmap != null) {
                        onFrameCaptured(bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .border(
                1.dp,
                if (currentFaceResult?.isIntruderDetected == true) AlertRed else SecurityIndigo.copy(alpha = 0.3f),
                RoundedCornerShape(20.dp)
            )
            .testTag("camera_viewfinder_surface"),
        color = CyberDarkBg
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header bar of viewfinder
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    !isMonitoringActive -> Color.Gray
                                    currentFaceResult?.isIntruderDetected == true -> AlertRed
                                    currentFaceResult?.isOwnerRecognized == true -> EmeraldSafe
                                    else -> WarningAmber
                                }
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = when {
                            !isMonitoringActive -> "Gözcü Beklemede"
                            !cameraPermissionState.status.isGranted -> "Kamera Opsiyonel (Kapalı)"
                            currentFaceResult?.isIntruderDetected == true -> "DİKKAT: Yabancı Bakış!"
                            currentFaceResult?.isOwnerRecognized == true -> "Güvenli: Sahip Doğrulandı"
                            else -> "Yerel AI Taramada..."
                        },
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (cameraPermissionState.status.isGranted && isCameraActive) {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    val bmp = cameraManager.captureSnapshot()
                                    if (bmp != null) onFrameCaptured(bmp)
                                }
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoCamera,
                                contentDescription = "Anlık Analiz Yap",
                                tint = SecurityIndigo,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    IconButton(
                        onClick = { isExpanded = !isExpanded },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                            contentDescription = "Genişlet/Daralt",
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            AnimatedVisibility(visible = isExpanded) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(Color(0xFF090D14))
                ) {
                    if (cameraPermissionState.status.isGranted && isCameraActive) {
                        // Live Camera View
                        AndroidView(
                            factory = { ctx ->
                                PreviewView(ctx).apply {
                                    previewViewRef = this
                                    cameraManager.bindCamera(lifecycleOwner, this) { success ->
                                        // Bound status
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxSize()
                        )

                        // Cyber Scanning Overlay
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Draw scanning line
                            val lineY = h * scanLineProgress
                            drawLine(
                                color = if (currentFaceResult?.isIntruderDetected == true) AlertRed.copy(alpha = 0.8f) else SecurityIndigo.copy(alpha = 0.7f),
                                start = Offset(0f, lineY),
                                end = Offset(w, lineY),
                                strokeWidth = 3f
                            )

                            // Corner targeting brackets
                            val cornerLen = 36f
                            val cornerStroke = 4f
                            val strokeColor = if (currentFaceResult?.isIntruderDetected == true) AlertRed else SecurityIndigo

                            // Top-left
                            drawLine(strokeColor, Offset(40f, 30f), Offset(40f + cornerLen, 30f), cornerStroke)
                            drawLine(strokeColor, Offset(40f, 30f), Offset(40f, 30f + cornerLen), cornerStroke)

                            // Top-right
                            drawLine(strokeColor, Offset(w - 40f, 30f), Offset(w - 40f - cornerLen, 30f), cornerStroke)
                            drawLine(strokeColor, Offset(w - 40f, 30f), Offset(w - 40f, 30f + cornerLen), cornerStroke)

                            // Bottom-left
                            drawLine(strokeColor, Offset(40f, h - 30f), Offset(40f + cornerLen, h - 30f), cornerStroke)
                            drawLine(strokeColor, Offset(40f, h - 30f), Offset(40f, h - 30f - cornerLen), cornerStroke)

                            // Bottom-right
                            drawLine(strokeColor, Offset(w - 40f, h - 30f), Offset(w - 40f - cornerLen, h - 30f), cornerStroke)
                            drawLine(strokeColor, Offset(w - 40f, h - 30f), Offset(w - 40f, h - 30f - cornerLen), cornerStroke)
                        }
                    } else {
                        // Camera not active / permission not granted placeholder
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(SecurityIndigo.copy(alpha = 0.12f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (!cameraPermissionState.status.isGranted) Icons.Default.VideocamOff else Icons.Default.CameraAlt,
                                    contentDescription = null,
                                    tint = SecurityIndigo,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = if (!cameraPermissionState.status.isGranted) "Kamera Erişimi Tercihe Bağlıdır" else "Kamera İzleme Duraklatıldı",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (!cameraPermissionState.status.isGranted) {
                                    "Ön kamera ile anlık analiz yapmak için izin verebilir veya alttaki simülasyon butonlarıyla sistemi test edebilirsiniz."
                                } else {
                                    "Gözcü Kalkanını açarak gerçek zamanlı yüz analizini başlatabilirsiniz."
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.65f),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                lineHeight = 16.sp
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            if (!cameraPermissionState.status.isGranted) {
                                Button(
                                    onClick = { cameraPermissionState.launchPermissionRequest() },
                                    colors = ButtonDefaults.buttonColors(containerColor = SecurityIndigo),
                                    shape = RoundedCornerShape(12.dp),
                                    modifier = Modifier.testTag("request_camera_permission_button")
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text("Kamerayı Etkinleştir")
                                }
                            }
                        }
                    }

                    // Bottom info bar inside viewfinder
                    if (cameraPermissionState.status.isGranted && isCameraActive) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(Color.Black.copy(alpha = 0.6f))
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = currentFaceResult?.description ?: "Ön kamera ile yerel yüz taraması yapılıyor...",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (currentFaceResult?.isIntruderDetected == true) AlertRed else Color.White.copy(alpha = 0.85f),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }
    }
}
