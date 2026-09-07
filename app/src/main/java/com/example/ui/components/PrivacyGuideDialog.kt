package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsTransit
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.ui.theme.AlertRed
import com.example.ui.theme.CyberDarkSurface
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.SecurityIndigo
import com.example.ui.theme.WarningAmber

@Composable
fun PrivacyGuideDialog(
    onDismiss: () -> Unit
) {
    var selectedScenario by remember { mutableIntStateOf(0) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .clip(RoundedCornerShape(24.dp))
                .border(1.dp, SecurityIndigo.copy(alpha = 0.4f), RoundedCornerShape(24.dp))
                .testTag("privacy_guide_dialog"),
            color = CyberDarkSurface
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Bar
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
                                imageVector = Icons.Default.Shield,
                                contentDescription = null,
                                tint = SecurityIndigo,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Gözcü Hangi Sorunu Çözer?",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = Color.White
                            )
                            Text(
                                text = "Gerçek Hayat Gizlilik Rehberi",
                                style = MaterialTheme.typography.labelSmall,
                                color = EmeraldSafe
                            )
                        }
                    }

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_guide_dialog_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Kapat",
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Hero Graphic Banner
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                ) {
                    Image(
                        painter = painterResource(
                            id = if (selectedScenario == 0) R.drawable.img_shoulder_surfing else R.drawable.img_privacy_hero
                        ),
                        contentDescription = "Gizlilik Tehdit Analizi",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scenario Selector Chips
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    ScenarioChip(
                        title = "Metro & Toplu Taşıma",
                        isSelected = selectedScenario == 0,
                        onClick = { selectedScenario = 0 },
                        modifier = Modifier.weight(1f)
                    )
                    ScenarioChip(
                        title = "Masa & Ofis",
                        isSelected = selectedScenario == 1,
                        onClick = { selectedScenario = 1 },
                        modifier = Modifier.weight(1f)
                    )
                    ScenarioChip(
                        title = "%100 Yerel AI",
                        isSelected = selectedScenario == 2,
                        onClick = { selectedScenario = 2 },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Scenario Content Details
                when (selectedScenario) {
                    0 -> ProblemSolutionCard(
                        icon = Icons.Default.DirectionsTransit,
                        problemTitle = "Sorun: Omuz Üzerinden Gözetleme (Shoulder Surfing)",
                        problemDesc = "Metroda, otobüste veya kafede bankacılık işlemlerinizi yaparken ya da özel mesajlaşmalarınızı sürdürürken, yanınızdaki veya arkanızdaki meraklı gözlerin ekranınızı dikizlemesi.",
                        solutionTitle = "Gözcü Çözümü: Canlı Kalkan & Bakış Açısı Analizi",
                        solutionDesc = "Ön kameranın geniş açısını kullanan yerel AI, ekrana doğrudan odaklanan yabancı yüzleri ve bakış yönünü anlık analiz eder. Sahibinden farklı bir bakış yakaladığında anında uyarı verir ve gözetleyeni sessizce fotoğraflar."
                    )
                    1 -> ProblemSolutionCard(
                        icon = Icons.Default.PhoneAndroid,
                        problemTitle = "Sorun: Masada Bırakılan Telefonun Kurcalanması",
                        problemDesc = "Çalışma masasında, toplantıda veya evde telefonunuzu kısa süre için bıraktığınızda, birisinin ekranı açıp özel bildirimleri veya gelen aramaları okumaya çalışması.",
                        solutionTitle = "Gözcü Çözümü: Tuzak Kilit Ekranı (Decoy Trap)",
                        solutionDesc = "Telefon normal bir kilit ekranı gibi görünür. Meraklı kişi ekrana dokunduğu anda ön kamera flaş patlatmadan sessizce anlık fotoğrafını çeker ve günlüğe saat/tarih damgasıyla kaydeder."
                    )
                    2 -> ProblemSolutionCard(
                        icon = Icons.Default.Lock,
                        problemTitle = "Sorun: Biyometrik Verilerin Buluta Gitme Endişesi",
                        problemDesc = "Piyasadaki birçok güvenlik uygulamasının yüz fotoğraflarını ve biyometri verilerini sunuculara yüklemesi ve kullanıcı gizliliğini tehlikeye atması.",
                        solutionTitle = "Gözcü Çözümü: Sıfır Bulut (%100 On-Device)",
                        solutionDesc = "Gözcü tamamen çevrimdışı çalışır. Fotoğraflar ve yüz analizi sadece cihazınızın güvenli depolama alanında saklanır. Hiçbir 3. tarafa ya da sunucuya veri aktarılmaz."
                    )
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 3 Value Pillars
                ValuePillarRow(
                    icon = Icons.Default.Security,
                    title = "Kullanıcı Onayı & Açık Rıza",
                    subtitle = "Tüm analiz kullanıcı rızası ve yasal sorumluluk reddi şartlarına bağlıdır."
                )

                Spacer(modifier = Modifier.height(10.dp))

                ValuePillarRow(
                    icon = Icons.Default.Visibility,
                    title = "Opsiyonel Kamera Kullanımı",
                    subtitle = "İzin vermeden de tuzak ve simülasyon özellikleriyle güvenle test edebilirsiniz."
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = SecurityIndigo),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("understand_guide_button")
                ) {
                    Text(
                        text = "Anladım, Korumayı Başlat",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun ScenarioChip(
    title: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(if (isSelected) SecurityIndigo else MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() }
            .padding(vertical = 8.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
            maxLines = 1
        )
    }
}

@Composable
private fun ProblemSolutionCard(
    icon: ImageVector,
    problemTitle: String,
    problemDesc: String,
    solutionTitle: String,
    solutionDesc: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Problem Section
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(AlertRed.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = AlertRed,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = problemTitle,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = AlertRed
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = problemDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Solution Section
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(EmeraldSafe.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Shield,
                        contentDescription = null,
                        tint = EmeraldSafe,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = solutionTitle,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = EmeraldSafe
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = solutionDesc,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun ValuePillarRow(
    icon: ImageVector,
    title: String,
    subtitle: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = SecurityIndigo,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                color = Color.White.copy(alpha = 0.7f),
                lineHeight = 14.sp
            )
        }
    }
}
