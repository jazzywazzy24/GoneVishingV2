package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gonevishing.R
import com.example.gonevishing.ui.theme.GvOnSurface
import com.example.gonevishing.ui.theme.GvSurface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.gonevishing.ui.theme.Fredoka
import com.example.gonevishing.ui.theme.GvBackground
import java.time.Duration
import java.time.LocalDateTime

/*
Edits to make:
- SCRAP bottom nav bar
- change "ONLINE" to "IN CALL" or "Waiting for a bite..."
    descriptions and images
    hahahha
 */

private enum class Screen {
    DevMenu,
    LoginScreen,
    HomeScreen,
    SettingsScreen,
    FeatureComingSoonScreen,
    AgentManagementScreen,
    RecentActivityScreen
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRouter() {

    var screen by remember { mutableStateOf(Screen.DevMenu) }
    var showSendToAgentOverlay by remember { mutableStateOf(false) }
    var isProtectionOnline by remember { mutableStateOf(true) } //real logic later
    val recentCalls = remember {
        val now = LocalDateTime.now()
        listOf(
            CallActivity("1", "+1 (415) 555-0123", now.minusHours(1), Duration.ofSeconds(184)),
            CallActivity("2", "+1 (650) 555-0199", now.minusDays(1).minusMinutes(12), Duration.ofSeconds(57)),
            CallActivity("3", "Unknown", now.minusDays(2).minusHours(3), Duration.ofSeconds(420)),
            CallActivity("4", "+44 20 7946 0958", now.minusDays(3), Duration.ofSeconds(133)),
            CallActivity("5", "+1 (212) 555-0177", now.minusDays(6).minusHours(2), Duration.ofSeconds(305)),
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Scaffold(
            // Top bar everywhere except login
            topBar = {
                if (screen != Screen.LoginScreen) {
                    TopAppBar(
                        title = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = "Gone Vishing",
                                    modifier = Modifier.align(Alignment.Center),
                                    color = GvOnSurface,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.SemiBold,
                                    fontFamily = Fredoka
                                )

                                ProtectionStatusPill(
                                    isOnline = isProtectionOnline,
                                    modifier = Modifier.align(Alignment.CenterEnd)
                                )
                            }
                        },
                        actions = {},
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = GvSurface,
                            titleContentColor = GvOnSurface
                        )
                    )
                }
            },

            // Bottom bar only on Home and AgentManagement
            bottomBar = {
                if (screen == Screen.HomeScreen ||
                    screen == Screen.AgentManagementScreen ||
                    screen == Screen.RecentActivityScreen
                    ) {
                    NavigationBar (
                        containerColor = GvBackground,
                        modifier = Modifier.height(110.dp)
                    ){

                        NavigationBarItem(
                            selected = screen == Screen.FeatureComingSoonScreen,
                            onClick = { screen = Screen.FeatureComingSoonScreen },
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.temp_icon),
                                    contentDescription = "Automation management",
                                    modifier = Modifier.size(60.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )

                        NavigationBarItem(
                            selected = screen == Screen.FeatureComingSoonScreen,
                            onClick = { screen = Screen.FeatureComingSoonScreen },
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.transcript_icon),
                                    contentDescription = "View Transcripts",
                                    modifier = Modifier.size(60.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )

                        NavigationBarItem(
                            selected = screen == Screen.HomeScreen,
                            onClick = { screen = Screen.HomeScreen },
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.home_icon),
                                    contentDescription = "Home",
                                    modifier = Modifier.size(60.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )

                        NavigationBarItem(
                            selected = screen == Screen.RecentActivityScreen,
                            onClick = { screen = Screen.RecentActivityScreen },
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.recentactivity_icon),
                                    contentDescription = "Recent Activity",
                                    modifier = Modifier.size(60.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )

                        NavigationBarItem(
                            selected = screen == Screen.SettingsScreen,
                            onClick = { screen = Screen.SettingsScreen },
                            icon = {
                                Image(
                                    painter = painterResource(id = R.drawable.settings_icon),
                                    contentDescription = "Settings",
                                    modifier = Modifier.size(40.dp)
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        ) { innerPadding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (screen) {

                    Screen.DevMenu ->
                        _root_ide_package_.com.example.gonevishing.ui.screens.DevMenuScreen(
                            onOpenLogin = { screen = Screen.LoginScreen },
                            onOpenHome = { screen = Screen.HomeScreen },
                            onOpenSendToAgentOverlay = { showSendToAgentOverlay = true },
                            onOpenSettingsScreen = { screen = Screen.SettingsScreen },
                            onOpenFeatureComingSoonScreen = {
                                screen = Screen.FeatureComingSoonScreen
                            },
                            onOpenAgentManagementScreen = { screen = Screen.AgentManagementScreen },
                            onOpenRecentActivityScreen = { screen = Screen.RecentActivityScreen },
                        )

                    Screen.LoginScreen ->
                        _root_ide_package_.com.example.gonevishing.ui.screens.LoginScreen(
                            onLoginSuccess = { screen = Screen.HomeScreen },
                            onForgotPassword = { screen = Screen.FeatureComingSoonScreen }
                        )

                    Screen.HomeScreen ->
                        HomeScreen(
                            onAgentManagementClick = { screen = Screen.AgentManagementScreen },
                            onRecentActivityClick = { screen = Screen.RecentActivityScreen }
                        )

                    Screen.FeatureComingSoonScreen ->
                        _root_ide_package_.com.example.gonevishing.ui.screens.FeatureComingSoonScreen(
                            onBack = { screen = Screen.LoginScreen }
                        )

                    Screen.SettingsScreen ->
                        SettingsScreen(
                            onSave = { username, email, phone, scamOverlay, notifications, vibration ->
                                screen = Screen.HomeScreen }
                        )

                    Screen.AgentManagementScreen ->
                        AgentManagementScreen (
                            onBack = { screen= Screen.HomeScreen }
                        )

                    Screen.RecentActivityScreen ->
                        RecentActivityScreen (
                            calls = recentCalls,
                            onBack = { screen = Screen.HomeScreen },
                            onViewTranscript = { } //logic later
                        )
                }
            }
        }

        if (showSendToAgentOverlay) {
            _root_ide_package_.com.example.gonevishing.ui.screens.SendToAgentOverlay(
                callerName = "Scam Caller",
                callerNumber = "+1 (818) 123-4567",
                onDismiss = { showSendToAgentOverlay = false },
                onSendToAgent = { showSendToAgentOverlay = false },
            )
        }
    }
}

@Composable
private fun ProtectionStatusPill(
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    val label = if (isOnline) "ONLINE" else "OFFLINE"
    val dotColor = if (isOnline) Color(0xFF2E7D32) else Color(0xFFC62828)
    val textColor = dotColor

    Row(
        modifier = modifier
            .background(
                color = Color.White.copy(alpha = 0.85f),
                shape = RoundedCornerShape(999.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(dotColor, shape = RoundedCornerShape(999.dp))
        )
        Spacer(Modifier.width(6.dp))
        Text(
            text = label,
            color = textColor,
            style = MaterialTheme.typography.labelSmall
        )
    }
}