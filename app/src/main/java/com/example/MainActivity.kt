package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.SnooperLog
import com.example.ui.SnoopGuardViewModel
import com.example.ui.components.DecoyTrapScreen
import com.example.ui.components.DisclaimerConsentDialog
import com.example.ui.components.PrivacyGuideDialog
import com.example.ui.components.RegisterOwnerDialog
import com.example.ui.components.SnooperDetailDialog
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.LogsGalleryScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.AlertRed
import com.example.ui.theme.EmeraldSafe
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SecurityIndigo

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                SnoopGuardApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnoopGuardApp(viewModel: SnoopGuardViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val logs by viewModel.logs.collectAsStateWithLifecycle()

    var selectedTab by remember { mutableIntStateOf(0) }
    var inspectingLog by remember { mutableStateOf<SnooperLog?>(null) }
    var showPrivacyGuide by remember { mutableStateOf(false) }

    // If Decoy Trap mode is fullscreen active
    if (state.showDecoyTrapScreen) {
        DecoyTrapScreen(
            onExitTrap = { viewModel.closeDecoyTrap() },
            onTrapTriggered = { bmp -> viewModel.onDecoyTrapTriggered(bmp) }
        )
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "GÖZCÜ",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Black,
                                letterSpacing = 2.sp
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (state.isMonitoringActive) EmeraldSafe.copy(alpha = 0.15f)
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "Güvenlik Durumu",
                            tint = if (state.isMonitoringActive) EmeraldSafe else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                actions = {
                    // 100% Local On-Device indicator pill
                    Surface(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(CircleShape),
                        color = EmeraldSafe.copy(alpha = 0.12f)
                    ) {
                        androidx.compose.foundation.layout.Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lock,
                                contentDescription = null,
                                tint = EmeraldSafe,
                                modifier = Modifier.size(12.dp)
                            )
                            androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = "Yerel AI",
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = EmeraldSafe,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = "Kalkan"
                        )
                    },
                    label = { Text("Kalkan") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SecurityIndigo,
                        selectedTextColor = SecurityIndigo,
                        indicatorColor = SecurityIndigo.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier.testTag("nav_shield")
                )

                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = {
                        BadgedBox(
                            badge = {
                                if (logs.isNotEmpty()) {
                                    Badge(
                                        containerColor = AlertRed,
                                        contentColor = Color.White
                                    ) {
                                        Text("${logs.size}")
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.PhotoLibrary,
                                contentDescription = "Günlük"
                            )
                        }
                    },
                    label = { Text("Günlük") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SecurityIndigo,
                        selectedTextColor = SecurityIndigo,
                        indicatorColor = SecurityIndigo.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier.testTag("nav_logs")
                )

                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Ayarlar"
                        )
                    },
                    label = { Text("Ayarlar") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = SecurityIndigo,
                        selectedTextColor = SecurityIndigo,
                        indicatorColor = SecurityIndigo.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier.testTag("nav_settings")
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (selectedTab) {
                0 -> HomeScreen(
                    state = state,
                    logCount = logs.size,
                    onToggleGuard = { viewModel.toggleGuard(it) },
                    onSetGuardMode = { viewModel.setGuardMode(it) },
                    onCameraPermissionChanged = { viewModel.setCameraPermission(it) },
                    onFrameCaptured = { viewModel.onCameraFrameCaptured(it) },
                    onOpenRegisterOwner = { viewModel.setShowRegisterOwnerDialog(true) },
                    onOpenDecoyTrap = { viewModel.openDecoyTrap() },
                    onSimulateTest = { viewModel.simulateSnooperIncident() },
                    onAlertClicked = { inspectingLog = it },
                    onOpenGuide = { showPrivacyGuide = true }
                )
                1 -> LogsGalleryScreen(
                    logs = logs,
                    onLogClick = { inspectingLog = it },
                    onDeleteLog = { viewModel.deleteLog(it) },
                    onClearAll = { viewModel.clearAllLogs() }
                )
                2 -> SettingsScreen(
                    state = state,
                    onSensitivityChange = { viewModel.setSensitivity(it) },
                    onVibrateToggle = { viewModel.setVibrateAlert(it) },
                    onResetOwnerFace = { viewModel.resetOwnerFace() },
                    onClearAllLogs = { viewModel.clearAllLogs() },
                    onOpenDisclaimer = { viewModel.openDisclaimer() },
                    onRevokeDisclaimer = { viewModel.revokeDisclaimer() },
                    onOpenPrivacyGuide = { showPrivacyGuide = true }
                )
            }
        }
    }

    // Modal: Privacy & Problem Solution Guide
    if (showPrivacyGuide) {
        PrivacyGuideDialog(
            onDismiss = { showPrivacyGuide = false }
        )
    }

    // Modal: Legal Disclaimer & Explicit User Consent
    if (state.showDisclaimerDialog) {
        DisclaimerConsentDialog(
            onAccept = { viewModel.acceptDisclaimer() },
            onDismiss = { viewModel.dismissDisclaimer() },
            canDismissWithoutAccepting = state.disclaimerAccepted
        )
    }

    // Modal: Register Owner Face
    if (state.showRegisterOwnerDialog) {
        RegisterOwnerDialog(
            onDismiss = { viewModel.setShowRegisterOwnerDialog(false) },
            onEnrolled = { bmp, name -> viewModel.registerOwnerFace(bmp, name) }
        )
    }

    // Modal: Snooper Detail View
    inspectingLog?.let { log ->
        SnooperDetailDialog(
            log = log,
            onDismiss = { inspectingLog = null },
            onDelete = {
                viewModel.deleteLog(it)
                inspectingLog = null
            }
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Hello $name!", modifier = modifier)
}

