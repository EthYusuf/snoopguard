package com.example.ui.screens

import android.Manifest
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.SnoopGuardUiState
import com.example.ui.theme.AlertRed
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.SecurityIndigo
import com.example.ui.theme.WarningAmber
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SettingsScreen(
    state: SnoopGuardUiState,
    onSensitivityChange: (Float) -> Unit,
    onVibrateToggle: (Boolean) -> Unit,
    onResetOwnerFace: () -> Unit,
    onClearAllLogs: () -> Unit,
    onOpenDisclaimer: () -> Unit,
    onRevokeDisclaimer: () -> Unit,
    onOpenPrivacyGuide: () -> Unit
) {
    val scrollState = rememberScrollState()
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    var showResetFaceConfirm by remember { mutableStateOf(false) }
    var showRevokeConfirm by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Privacy Guarantee Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = EmeraldSafe.copy(alpha = 0.12f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(EmeraldSafe.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = EmeraldSafe,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "100% Yerel Veri İşleme Güvencesi",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Hiçbir fotoğraf veya yüz verisi internete veya sunuculara aktarılmaz. Her şey telefonun özel alanında saklanır.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                            lineHeight = 16.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onOpenPrivacyGuide,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Gözcü Hangi Sorunu Çözer? (Detaylı Rehber)", fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // SECTION: Camera & Permissions
        SettingsSectionHeader("KAMERA & İZİNLER")

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(SecurityIndigo.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = SecurityIndigo,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Ön Kamera Erişimi",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (cameraPermissionState.status.isGranted) "İzin Verildi (Opsiyonel)" else "İzin Verilmedi (Opsiyonel)",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (cameraPermissionState.status.isGranted) EmeraldSafe else WarningAmber
                            )
                        }
                    }

                    if (!cameraPermissionState.status.isGranted) {
                        Button(
                            onClick = { cameraPermissionState.launchPermissionRequest() },
                            colors = ButtonDefaults.buttonColors(containerColor = SecurityIndigo),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("İzin Ver", fontSize = 12.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // SECTION: Detection & Alert Sensitivity
        SettingsSectionHeader("ALGILAMA & BİLDİRİM AYARLARI")

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = SecurityIndigo,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Yüz Eşleşme Hassasiyeti",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    Text(
                        text = when {
                            state.sensitivity < 0.6f -> "Düşük"
                            state.sensitivity < 0.8f -> "Dengeli"
                            else -> "Yüksek"
                        },
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = SecurityIndigo
                    )
                }

                Slider(
                    value = state.sensitivity,
                    onValueChange = onSensitivityChange,
                    valueRange = 0.4f..0.95f,
                    colors = SliderDefaults.colors(
                        thumbColor = SecurityIndigo,
                        activeTrackColor = SecurityIndigo
                    ),
                    modifier = Modifier.testTag("sensitivity_slider")
                )

                Text(
                    text = "Daha yüksek hassasiyet, sahibinden hafif farklı yüz açılarını daha sık yabancı olarak kaydeder.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = null,
                            tint = SecurityIndigo,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Titreşimli Uyarı",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Gözetleme algılandığında gizli hafif titreşim ver",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }
                    }

                    Switch(
                        checked = state.vibrateOnAlert,
                        onCheckedChange = onVibrateToggle,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = SecurityIndigo
                        ),
                        modifier = Modifier.testTag("vibrate_switch")
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // SECTION: Owner & Legal
        SettingsSectionHeader("SORUMLULUK REDDİ & HUKUKİ ONAY")

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Gavel,
                            contentDescription = null,
                            tint = SecurityIndigo,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Kullanıcı Onayı & Sorumluluk Şartları",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = if (state.disclaimerAccepted) "Onaylandı • Tam Rıza Verildi" else "Onay Bekleniyor",
                                style = MaterialTheme.typography.bodySmall,
                                color = if (state.disclaimerAccepted) EmeraldSafe else WarningAmber
                            )
                        }
                    }

                    OutlinedButton(
                        onClick = onOpenDisclaimer,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("İncele", fontSize = 12.sp)
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { showRevokeConfirm = true },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Onayı Geri Çek", fontSize = 12.sp)
                    }

                    if (state.ownerProfile.isEnrolled) {
                        OutlinedButton(
                            onClick = { showResetFaceConfirm = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Sahip Yüzünü Sıfırla", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }

    if (showResetFaceConfirm) {
        AlertDialog(
            onDismissRequest = { showResetFaceConfirm = false },
            title = { Text("Sahip Profilini Sıfırla?") },
            text = { Text("Kayıtlı sahip yüz biyometrisi ve referans fotoğrafı cihazınızdan silinecektir.") },
            confirmButton = {
                Button(
                    onClick = {
                        onResetOwnerFace()
                        showResetFaceConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AlertRed)
                ) {
                    Text("Sıfırla")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showResetFaceConfirm = false }) {
                    Text("İptal")
                }
            }
        )
    }

    if (showRevokeConfirm) {
        AlertDialog(
            onDismissRequest = { showRevokeConfirm = false },
            title = { Text("Kullanıcı Onayını Geri Çek?") },
            text = { Text("Kullanıcı onayını geri çektiğinizde Gözcü Kalkanı durdurulur ve uygulamayı kullanabilmek için yeniden onay vermeniz gerekir.") },
            confirmButton = {
                Button(
                    onClick = {
                        onRevokeDisclaimer()
                        showRevokeConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AlertRed)
                ) {
                    Text("Geri Çek ve Durdur")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { showRevokeConfirm = false }) {
                    Text("İptal")
                }
            }
        )
    }
}

@Composable
private fun SettingsSectionHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, bottom = 8.dp)
    )
}
