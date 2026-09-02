package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.SavedScreen
import com.example.ui.screens.StylesScreen
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberMagenta
import com.example.ui.theme.DarkTextMuted
import com.example.ui.theme.PromptForgeTheme
import com.example.ui.viewmodel.MainNavigationTab
import com.example.ui.viewmodel.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun PromptForgeApp(
    viewModel: MainViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val savedPrompts by viewModel.allSavedPrompts.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.toastMessage) {
        val msg = uiState.toastMessage
        if (msg != null) {
            delay(3000)
            viewModel.dismissToast()
        }
    }

    PromptForgeTheme(darkTheme = uiState.darkTheme) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .testTag("prompt_forge_main_scaffold"),
            bottomBar = {
                AppBottomNavigationBar(
                    currentTab = uiState.currentTab,
                    savedCount = savedPrompts.size,
                    onTabSelected = { viewModel.selectTab(it) }
                )
            },
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (uiState.currentTab) {
                    MainNavigationTab.HOME -> HomeScreen(uiState = uiState, viewModel = viewModel)
                    MainNavigationTab.CREATE -> HomeScreen(uiState = uiState, viewModel = viewModel)
                    MainNavigationTab.STYLES -> StylesScreen(uiState = uiState, viewModel = viewModel)
                    MainNavigationTab.SAVED -> SavedScreen(uiState = uiState, viewModel = viewModel)
                    MainNavigationTab.MORE -> MoreScreen(uiState = uiState, viewModel = viewModel)
                }

                // Cyber Toast overlay
                AnimatedVisibility(
                    visible = uiState.toastMessage != null,
                    enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    uiState.toastMessage?.let { msg ->
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .border(
                                    1.dp,
                                    Brush.horizontalGradient(listOf(CyberCyan, CyberMagenta)),
                                    RoundedCornerShape(24.dp)
                                ),
                            color = Color(0xEE0F172A),
                            tonalElevation = 8.dp
                        ) {
                            Text(
                                text = msg,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AppBottomNavigationBar(
    currentTab: MainNavigationTab,
    savedCount: Int,
    onTabSelected: (MainNavigationTab) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(24.dp)),
        color = Color(0xF2111318), // #111318/95%
        tonalElevation = 8.dp
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            MainNavigationTab.values().forEach { tab ->
                val isSelected = currentTab == tab
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onTabSelected(tab) },
                    icon = {
                        if (tab == MainNavigationTab.SAVED && savedCount > 0) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = CyberCyan,
                                        contentColor = Color(0xFF08090C)
                                    ) {
                                        Text(text = savedCount.toString(), fontWeight = FontWeight.Black, fontSize = 10.sp)
                                    }
                                }
                            ) {
                                NavigationIcon(tab = tab, isSelected = isSelected)
                            }
                        } else {
                            NavigationIcon(tab = tab, isSelected = isSelected)
                        }
                    },
                    label = {
                        Text(
                            text = tab.title.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.Medium,
                                fontSize = 10.sp,
                                letterSpacing = 0.8.sp
                            )
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = CyberCyan,
                        selectedTextColor = CyberCyan,
                        unselectedIconColor = DarkTextMuted,
                        unselectedTextColor = DarkTextMuted,
                        indicatorColor = CyberCyan.copy(alpha = 0.12f)
                    ),
                    modifier = Modifier.testTag("nav_tab_${tab.name.lowercase()}")
                )
            }
        }
    }
}

@Composable
private fun NavigationIcon(tab: MainNavigationTab, isSelected: Boolean) {
    when (tab) {
        MainNavigationTab.HOME -> Icon(Icons.Default.Home, contentDescription = "Home")
        MainNavigationTab.CREATE -> Icon(Icons.Default.PhotoCamera, contentDescription = "Create")
        MainNavigationTab.STYLES -> Icon(Icons.Default.AutoAwesome, contentDescription = "Styles")
        MainNavigationTab.SAVED -> Icon(Icons.Default.Bookmark, contentDescription = "Saved")
        MainNavigationTab.MORE -> Icon(Icons.Default.Menu, contentDescription = "More")
    }
}
