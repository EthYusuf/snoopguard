package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.SnooperLog
import com.example.ui.GuardMode
import com.example.ui.SnoopGuardUiState
import com.example.ui.components.CameraViewfinder
import com.example.ui.theme.AlertRed
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.SecurityIndigo
import com.example.ui.theme.WarningAmber

@Composable
fun HomeScreen(
    state: SnoopGuardUiState,
    logCount: Int,
    onToggleGuard: (Boolean) -> Unit,
    onSetGuardMode: (GuardMode) -> Unit,
    onCameraPermissionChanged: (Boolean) -> Unit,
    onFrameCaptured: (Bitmap) -> Unit,
    onOpenRegisterOwner: () -> Unit,
    onOpenDecoyTrap: () -> Unit,
    onSimulateTest: () -> Unit,
    onAlertClicked: (SnooperLog) -> Unit,
    onOpenGuide: () -> Unit
) {
    val scrollState = rememberScrollState()

    // Pulse animation when monitoring is active
    val infiniteTransition = rememberInfiniteTransition(label = "shield_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val shieldColor by animateColorAsState(
        targetValue = when {
            !state.isMonitoringActive -> Color.Gray
            state.currentFaceResult?.isIntruderDetected == true -> AlertRed
            state.currentFaceResult?.isOwnerRecognized == true -> EmeraldSafe
            else -> SecurityIndigo
        },
        label = "shield_color"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Real-time Snooper Alert Banner (if recently triggered)
        AnimatedVisibility(visible = state.activeAlert != null) {
            state.activeAlert?.let { alert ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .clickable { onAlertClicked(alert) }
                        .testTag("active_alert_banner"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AlertRed)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "DİKKAT: YABANCI BAKIŞ TESPİT EDİLDİ!",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "${alert.tag} • İncelemek için dokunun",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }
        }

        // Hero Shield Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("hero_shield_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header with Master Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Gözcü Kalkanı",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (state.isMonitoringActive) "Aktif Olarak Koruyor" else "Koruma Kapalı",
                            style = MaterialTheme.typography.labelSmall,
                            color = if (state.isMonitoringActive) EmeraldSafe else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    }

                    Switch(
                        checked = state.isMonitoringActive,
                        onCheckedChange = { onToggleGuard(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = EmeraldSafe
                        ),
                        modifier = Modifier.testTag("guard_master_switch")
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Pulsing Shield Center
                Box(
                    modifier = Modifier
                        .size(130.dp)
                        .scale(if (state.isMonitoringActive) pulseScale else 1f)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    shieldColor.copy(alpha = 0.25f),
                                    Color.Transparent
                                )
                            )
                        )
                        .border(3.dp, shieldColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (state.isMonitoringActive) Icons.Default.Shield else Icons.Default.Security,
                        contentDescription = null,
                        tint = shieldColor,
                        modifier = Modifier.size(60.dp)
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = when {
                        !state.isMonitoringActive -> "Kalkan Beklemede"
                        state.currentFaceResult?.isIntruderDetected == true -> "YETKİSİZ KİŞİ YAKALANDI!"
                        state.currentFaceResult?.isOwnerRecognized == true -> "Cihaz Sahibi Algılandı"
                        else -> "Yerel AI Ekranı İzliyor"
                    },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = shieldColor
                )

                Text(
                    text = if (state.isMonitoringActive) {
                        "Telefonunuza bakan başka biri olduğunda anında yerel fotoğraf kaydı alır."
                    } else {
                        "Korumayı başlatmak için yukarıdaki anahtarı açın."
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    lineHeight = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Camera Viewfinder (Front Camera & Local Detection)
        CameraViewfinder(
            isCameraActive = state.isCameraActive,
            isMonitoringActive = state.isMonitoringActive,
            currentFaceResult = state.currentFaceResult,
            onCameraPermissionChanged = onCameraPermissionChanged,
            onFrameCaptured = onFrameCaptured
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Owner Face Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(
                                if (state.ownerProfile.isEnrolled) EmeraldSafe.copy(alpha = 0.15f)
                                else WarningAmber.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = null,
                            tint = if (state.ownerProfile.isEnrolled) EmeraldSafe else WarningAmber,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = if (state.ownerProfile.isEnrolled) state.ownerProfile.ownerName else "Sahip Yüzü Kayıtlı Değil",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (state.ownerProfile.isEnrolled) "Biyometrik Profil Hazır" else "AI'ın sizi tanıması için kaydedin",
                            style = MaterialTheme.typography.bodySmall,
                            color = if (state.ownerProfile.isEnrolled) EmeraldSafe else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }

                Button(
                    onClick = onOpenRegisterOwner,
                    colors = ButtonDefaults.buttonColors(containerColor = SecurityIndigo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("register_face_trigger_button")
                ) {
                    Text(
                        text = if (state.ownerProfile.isEnrolled) "Güncelle" else "Yüzü Tanıt",
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Banner: Gözcü Hangi Sorunu Çözer?
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .border(1.dp, SecurityIndigo.copy(alpha = 0.35f), RoundedCornerShape(18.dp))
                .clickable { onOpenGuide() }
                .testTag("open_privacy_guide_banner"),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_shoulder_surfing),
                        contentDescription = "Gözcü Rehberi",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Gözcü Hangi Sorunu Çözer?",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(3.dp))
                    Text(
                        text = "Metroda omuz dikizleme ve masada telefon kurcalama çözümleri",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    )
                }

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = "Rehberi Aç",
                    tint = SecurityIndigo,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Guard Mode Selector
        Text(
            text = "KORUMA MODLARI",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 4.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            GuardModeCard(
                title = "Canlı Kalkan",
                subtitle = "Ön kamera ile anlık analiz",
                isSelected = state.guardMode == GuardMode.LIVE_SHIELD,
                onClick = { onSetGuardMode(GuardMode.LIVE_SHIELD) },
                modifier = Modifier.weight(1f)
            )

            GuardModeCard(
                title = "Tuzak Ekranı",
                subtitle = "Gözetleyeni sahte kilit ile yakalar",
                isSelected = state.guardMode == GuardMode.DECOY_TRAP,
                onClick = {
                    onSetGuardMode(GuardMode.DECOY_TRAP)
                    onOpenDecoyTrap()
                },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Stats & Quick Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            QuickStatCard(
                title = "Yakalanan Bakış",
                value = "$logCount",
                icon = Icons.Default.Visibility,
                accentColor = if (logCount > 0) AlertRed else EmeraldSafe,
                modifier = Modifier.weight(1f)
            )

            QuickStatCard(
                title = "Gizlilik Durumu",
                value = "%100 Yerel",
                icon = Icons.Default.Lock,
                accentColor = EmeraldSafe,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Test Simulation Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Simülasyon & Test",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Kamera olmadan da sistemin nasıl yabancı yakaladığını test edin.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                    )
                }

                OutlinedButton(
                    onClick = onSimulateTest,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("simulate_test_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.BugReport,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Test Et", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun GuardModeCard(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .border(
                1.5.dp,
                if (isSelected) SecurityIndigo else Color.Transparent,
                RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) SecurityIndigo.copy(alpha = 0.12f)
            else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                color = if (isSelected) SecurityIndigo else MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
private fun QuickStatCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(accentColor.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = accentColor,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
