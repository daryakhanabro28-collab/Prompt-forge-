package com.example.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Transform
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.CategoryGroup
import com.example.domain.model.PromptStyle
import com.example.domain.model.StyleCatalog
import com.example.ui.components.CreatorFooter
import com.example.ui.components.GlassCard
import com.example.ui.components.NeonButton
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberMagenta
import com.example.ui.viewmodel.MainNavigationTab
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.SubPortal
import com.example.ui.viewmodel.UiState

@Composable
fun MoreScreen(
    uiState: UiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    AnimatedContent(
        targetState = uiState.currentSubPortal,
        label = "more_portal_transition"
    ) { subPortal ->
        if (subPortal == null) {
            MoreHubMenu(viewModel = viewModel, modifier = modifier)
        } else {
            when (subPortal) {
                SubPortal.ABOUT -> AboutPortal(viewModel = viewModel, modifier = modifier)
                SubPortal.SETTINGS -> SettingsPortal(uiState = uiState, viewModel = viewModel, modifier = modifier)
                SubPortal.ANALYZER -> DedicatedPortal(
                    title = "AI VISION ANALYZER",
                    category = CategoryGroup.TRENDING,
                    description = "Extract deep structural, lighting & color telemetry from your uploaded photos.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.GAMING -> DedicatedPortal(
                    title = "GAMING PROMPT STUDIO",
                    category = CategoryGroup.GAMING,
                    description = "Specialized prompt generator for Minecraft, PUBG, Free Fire, GTA & RPG universes.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.PHOTOGRAPHY -> DedicatedPortal(
                    title = "PHOTOGRAPHY STUDIO",
                    category = CategoryGroup.PHOTOGRAPHY,
                    description = "Master DSLR, 35mm film grain, golden hour, and cinematic lighting aesthetics.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.AI_ART -> DedicatedPortal(
                    title = "AI ART & 3D STUDIO",
                    category = CategoryGroup.AI_ART,
                    description = "Octane 3D renders, Pixar-style characters, Anime key visuals & traditional media.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.TRANSFORMER -> DedicatedPortal(
                    title = "PHOTO TRANSFORMER",
                    category = CategoryGroup.PHOTO_TRANSFORMER,
                    description = "Reimagine your photos as gaming champions, superheroes, and 3D animated figures.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.SOCIAL_MEDIA -> DedicatedPortal(
                    title = "SOCIAL MEDIA & POSTER STUDIO",
                    category = CategoryGroup.SOCIAL_MEDIA,
                    description = "Viral YouTube thumbnails, gaming avatars, TikTok PFPs & championship banners.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.CINEMATIC -> DedicatedPortal(
                    title = "CINEMATIC FILM STUDIO",
                    category = CategoryGroup.PHOTOGRAPHY,
                    description = "Anamorphic wide-angle lenses, Arri Alexa film stock, and Hollywood grading.",
                    viewModel = viewModel,
                    modifier = modifier
                )
                SubPortal.TRENDING -> DedicatedPortal(
                    title = "TRENDING PROMPT ARCHETYPES",
                    category = CategoryGroup.TRENDING,
                    description = "Most popular prompt styles used by AI digital creators worldwide.",
                    viewModel = viewModel,
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
private fun MoreHubMenu(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "SPECIALIZED STUDIOS",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = "Dedicated prompt synthesis portals & app configuration",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        // Portals Grid
        HubPortalItem(
            emoji = "🔍",
            title = "AI Vision Analyzer",
            subtitle = "Extract color palettes, lighting & pose telemetry",
            onClick = { viewModel.navigateToSubPortal(SubPortal.ANALYZER) }
        )
        HubPortalItem(
            emoji = "🎮",
            title = "Gaming Prompt Studio",
            subtitle = "Minecraft, PUBG, Free Fire, GTA & AAA Games",
            onClick = { viewModel.navigateToSubPortal(SubPortal.GAMING) }
        )
        HubPortalItem(
            emoji = "📸",
            title = "Photography Studio",
            subtitle = "DSLR, 35mm film, Golden hour, Cinematic lighting",
            onClick = { viewModel.navigateToSubPortal(SubPortal.PHOTOGRAPHY) }
        )
        HubPortalItem(
            emoji = "🎨",
            title = "AI Art & 3D Render Studio",
            subtitle = "Unreal Engine 5, Anime, Pixar-like, Watercolor",
            onClick = { viewModel.navigateToSubPortal(SubPortal.AI_ART) }
        )
        HubPortalItem(
            emoji = "🔄",
            title = "Photo Transformer",
            subtitle = "Turn portraits into Superheroes, Anime & 3D avatars",
            onClick = { viewModel.navigateToSubPortal(SubPortal.TRANSFORMER) }
        )
        HubPortalItem(
            emoji = "📱",
            title = "Social Media & Poster Creator",
            subtitle = "Viral YouTube thumbnails, Esports posters, PFPs",
            onClick = { viewModel.navigateToSubPortal(SubPortal.SOCIAL_MEDIA) }
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "PREFERENCES & ABOUT",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 1.sp
            )
        )

        HubPortalItem(
            emoji = "⚙️",
            title = "App Settings",
            subtitle = "Dark mode, auto-save & detail level",
            onClick = { viewModel.navigateToSubPortal(SubPortal.SETTINGS) }
        )

        HubPortalItem(
            emoji = "👑",
            title = "About Prompt Forge AI",
            subtitle = "Created by Daryakhan Abro • Version & Features",
            onClick = { viewModel.navigateToSubPortal(SubPortal.ABOUT) }
        )

        CreatorFooter(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun HubPortalItem(
    emoji: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        contentPadding = 14.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = emoji, fontSize = 24.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 11.5.sp
                        )
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DedicatedPortal(
    title: String,
    category: CategoryGroup,
    description: String,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val styles = StyleCatalog.getStylesForCategory(category)

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { viewModel.clearSubPortal() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = CyberCyan
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }

        items(styles) { style ->
            GlassCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.selectStyle(style)
                    viewModel.selectTab(MainNavigationTab.HOME)
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = style.iconEmoji, fontSize = 28.sp)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = style.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            )
                            Text(
                                text = style.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )
                        }
                    }

                    FilledTonalButton(
                        onClick = {
                            viewModel.selectStyle(style)
                            viewModel.selectTab(MainNavigationTab.HOME)
                        },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = CyberCyan.copy(alpha = 0.15f),
                            contentColor = CyberCyan
                        )
                    ) {
                        Text("Apply", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        item {
            CreatorFooter(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun SettingsPortal(
    uiState: UiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { viewModel.clearSubPortal() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = CyberCyan
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "SETTINGS & PREFERENCES",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        // Auto Save Switch
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Auto-Save Generated Prompts",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Text(
                        text = "Automatically log prompt generations to your local Room database",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
                Switch(
                    checked = uiState.autoSaveEnabled,
                    onCheckedChange = { viewModel.toggleAutoSave(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Black,
                        checkedTrackColor = CyberCyan
                    )
                )
            }
        }

        // Detail Level Selection
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "DEFAULT PROMPT DETAIL LEVEL",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.8.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            listOf("Standard", "High", "Ultra Detailed").forEach { level ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { viewModel.setDetailLevel(level) }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = uiState.promptDetailLevel == level,
                        onClick = { viewModel.setDetailLevel(level) },
                        colors = RadioButtonDefaults.colors(selectedColor = CyberCyan)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = level,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (uiState.promptDetailLevel == level) FontWeight.Bold else FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }

        // Storage Management
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "DATABASE & STORAGE MANAGEMENT",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    letterSpacing = 0.8.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = { viewModel.clearAllData() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.DeleteSweep, contentDescription = null, tint = Color(0xFFFF5252))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Clear All Local Data & History", color = Color(0xFFFF5252))
            }
        }

        CreatorFooter(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun AboutPortal(viewModel: MainViewModel, modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = { viewModel.clearSubPortal() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = CyberCyan
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "ABOUT PROMPT FORGE AI",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 1.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        // Creator Hero Card
        GlassCard(
            modifier = Modifier.fillMaxWidth(),
            isHighlighted = true,
            contentPadding = 20.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Brush.horizontalGradient(listOf(CyberCyan, CyberMagenta))),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👑", fontSize = 36.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "PROMPT FORGE AI",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.5.sp,
                        brush = Brush.horizontalGradient(listOf(CyberCyan, CyberMagenta))
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(1.dp, CyberCyan.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "CREATED BY DARYAKHAN ABRO",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = CyberCyan,
                            letterSpacing = 1.2.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "PROMPT FORGE AI is the world's most advanced mobile AI photo-to-prompt synthesizer. Upload any photo, and the neural engine dissects the subject, gear, posture, optics, lighting dynamics, and color harmonies to forge production-ready prompts for Midjourney v6, Stable Diffusion XL, Flux, and DALL-E 3.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Features Checklist
        GlassCard(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "ENGINE CAPABILITIES",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = CyberCyan,
                    letterSpacing = 1.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            AboutFeatureRow("🚀 35+ Master Styles", "Minecraft, PUBG, Free Fire, GTA, DSLR, 35mm Film, Octane 3D & Anime")
            AboutFeatureRow("👁️ Deep Vision Telemetry", "Identifies Subject, Clothing, Pose, Expression, Lighting, Composition & Palette")
            AboutFeatureRow("⚡ 4-Tier Prompt Synthesis", "Generates Cinematic, Gaming, Ultra-Detailed & Viral Social Media variations")
            AboutFeatureRow("⭐ Offline Room Database", "Full local bookmarking, history tracking, search & prompt editing")
            AboutFeatureRow("📋 1-Tap Copy & Share", "Instant copy to clipboard with ready-to-paste Midjourney / SDXL parameters")
        }

        CreatorFooter(modifier = Modifier.fillMaxWidth(), showDetailedCredits = true)
    }
}

@Composable
private fun AboutFeatureRow(title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.Top
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.5.sp
                )
            )
        }
    }
}
