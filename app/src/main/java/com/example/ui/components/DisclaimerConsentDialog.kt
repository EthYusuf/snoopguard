package com.example.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gavel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.SecurityIndigo

@Composable
fun DisclaimerConsentDialog(
    onAccept: () -> Unit,
    onDismiss: () -> Unit,
    canDismissWithoutAccepting: Boolean = false
) {
    var isAgreed by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = {
            if (canDismissWithoutAccepting) onDismiss()
        },
        properties = DialogProperties(
            dismissOnBackPress = canDismissWithoutAccepting,
            dismissOnClickOutside = canDismissWithoutAccepting,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.94f)
                .clip(RoundedCornerShape(24.dp))
                .testTag("disclaimer_dialog"),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Icon
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(SecurityIndigo.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Güvenlik ve Onay",
                        tint = SecurityIndigo,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Kullanıcı Onayı & Sorumluluk Reddi",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Gözcü (SnoopGuard) Kullanım Koşulları",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Feature Highlights
                ConsentHighlightCard(
                    icon = Icons.Default.Lock,
                    title = "%100 Yerel ve Çevrimdışı (On-Device)",
                    description = "Fotoğraflarınız ve yüz biyometri verileriniz hiçbir sunucuya, buluta veya 3. şahsa iletilmez. Tüm veriler yalnızca bu cihazda yerel olarak işlenir ve saklanır."
                )

                Spacer(modifier = Modifier.height(10.dp))

                ConsentHighlightCard(
                    icon = Icons.Default.Visibility,
                    title = "Opsiyonel Kamera ve Yüz Analizi",
                    description = "Kamera erişimi zorunlu olmayıp tamamen tercihinize bağlıdır. İzin verildiğinde ön kamera sadece ekranınıza izinsiz bakan yabancıları tespit etmek için kullanılır."
                )

                Spacer(modifier = Modifier.height(10.dp))

                ConsentHighlightCard(
                    icon = Icons.Default.Gavel,
                    title = "Yasal Sorumluluk Reddi",
                    description = "Uygulama geliştiricisi ve altyapı sağlayıcıları, uygulamanın kullanımından doğabilecek herhangi bir yasal, idari veya kişisel durumdan dolayı hiçbir sorumluluk kabul etmez. Tüm hukuki sorumluluk ve rıza kullanıcıya aittir."
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Agreement Checkbox
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isAgreed,
                        onCheckedChange = { isAgreed = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = EmeraldSafe,
                            checkmarkColor = MaterialTheme.colorScheme.surface
                        ),
                        modifier = Modifier.testTag("consent_checkbox")
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Yukarıdaki koşulları okudum, kendi rızam ve sorumluluğumla kabul ediyorum.",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (canDismissWithoutAccepting) {
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("consent_cancel_button")
                        ) {
                            Text("Kapat")
                        }
                    }

                    Button(
                        onClick = onAccept,
                        enabled = isAgreed,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("consent_accept_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecurityIndigo
                        )
                    ) {
                        Text(
                            text = "Onaylıyorum",
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ConsentHighlightCard(
    icon: ImageVector,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
        )
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(SecurityIndigo.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = SecurityIndigo,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
                    lineHeight = 16.sp
                )
            }
        }
    }
}
