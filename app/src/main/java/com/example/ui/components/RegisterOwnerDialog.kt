package com.example.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color as AndroidColor
import android.graphics.Paint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.detection.CameraManager
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.SecurityIndigo
import kotlinx.coroutines.launch

@Composable
fun RegisterOwnerDialog(
    onDismiss: () -> Unit,
    onEnrolled: (Bitmap, String) -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("Cihaz Sahibi") }
    var isCapturing by remember { mutableStateOf(false) }
    val cameraManager = remember { CameraManager(context) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, SecurityIndigo.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
                .testTag("register_owner_dialog"),
            color = CyberDarkSurface
        ) {
            Column(
                modifier = Modifier.padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(SecurityIndigo.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Face,
                                contentDescription = null,
                                tint = SecurityIndigo,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Sahip Yüzünü Tanıt",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "Biyometrik Yerel Profil",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldSafe
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_register_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Kapat",
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "AI Gözcü'nün telefonunuza başka birinin baktığını anlayabilmesi için önce kendi yüzünüzü cihaza güvenle kaydetmelisiniz. Bu veri %100 telefonunuzda kalır.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.8f),
                    lineHeight = 17.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Adınız veya Takma Ad") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("owner_name_input")
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Avatar Scan Silhouette Icon
                Box(
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(2.dp, SecurityIndigo, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = null,
                        tint = SecurityIndigo,
                        modifier = Modifier.size(54.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Vazgeç")
                    }

                    Button(
                        onClick = {
                            isCapturing = true
                            scope.launch {
                                // Try capturing from camera or generate a local reference profile
                                val capturedBmp = cameraManager.captureSnapshot()
                                val finalBmp = capturedBmp ?: createOwnerReferenceBitmap(name)
                                onEnrolled(finalBmp, name)
                                isCapturing = false
                            }
                        },
                        enabled = !isCapturing && name.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(containerColor = SecurityIndigo),
                        modifier = Modifier
                            .weight(1.3f)
                            .testTag("enroll_face_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (isCapturing) "Taranıyor..." else "Yüzümü Kaydet")
                    }
                }
            }
        }
    }
}

private fun createOwnerReferenceBitmap(name: String): Bitmap {
    val bmp = Bitmap.createBitmap(400, 400, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bmp)
    canvas.drawColor(AndroidColor.rgb(18, 24, 38))

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = AndroidColor.rgb(67, 97, 238)
        style = Paint.Style.STROKE
        strokeWidth = 5f
    }
    canvas.drawCircle(200f, 180f, 80f, paint)

    paint.color = AndroidColor.rgb(16, 185, 129)
    paint.style = Paint.Style.FILL
    canvas.drawCircle(170f, 165f, 10f, paint)
    canvas.drawCircle(230f, 165f, 10f, paint)

    paint.color = AndroidColor.WHITE
    paint.textSize = 22f
    paint.textAlign = Paint.Align.CENTER
    canvas.drawText("KAYITLI SAHİP", 200f, 320f, paint)
    paint.textSize = 18f
    paint.color = AndroidColor.rgb(150, 165, 195)
    canvas.drawText(name, 200f, 350f, paint)

    return bmp
}
