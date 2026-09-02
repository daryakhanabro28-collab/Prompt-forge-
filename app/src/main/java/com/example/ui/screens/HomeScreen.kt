package com.example.ui.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.CategoryGroup
import com.example.domain.model.CustomizationOptions
import com.example.domain.model.PromptStyle
import com.example.domain.model.StyleCatalog
import com.example.domain.model.StructuredPrompt
import com.example.ui.components.CreatorFooter
import com.example.ui.components.CustomizationPanel
import com.example.ui.components.EditPromptDialog
import com.example.ui.components.GlassCard
import com.example.ui.components.ImageAnalysisCard
import com.example.ui.components.NeonButton
import com.example.ui.components.PhotoUploadSection
import com.example.ui.components.PromptResultCard
import com.example.ui.theme.CyberAmber
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberGreen
import com.example.ui.theme.CyberMagenta
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    uiState: UiState,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var showCustomization by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // App Header Banner
        AppHeroHeader()

        Spacer(modifier = Modifier.height(16.dp))

        // Live Processing Progress Bar (if generating/analyzing)
        if (uiState.isGenerating || uiState.isAnalyzing) {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("generation_progress_card"),
                isHighlighted = true,
                contentPadding = 14.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (uiState.isAnalyzing) "AI VISION SCANNER ACTIVE" else "SYNTHESIZING PROMPT MATRIX",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = CyberCyan,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "${(uiState.analysisProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = CyberMagenta
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { uiState.analysisProgress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp)),
                    color = CyberCyan,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = uiState.analysisStatusStep,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        // 1. Photo Upload Component
        PhotoUploadSection(
            bitmap = uiState.selectedBitmap,
            onImageSelected = { bmp, uri -> viewModel.setImage(bmp, uri) },
            onRemoveImage = { viewModel.removeImage() },
            onAnalyzePhoto = { viewModel.startAnalysisOnly() },
            onGeneratePrompts = { viewModel.generatePrompts() },
            selectedStyle = uiState.selectedStyle,
            isGenerating = uiState.isGenerating,
            isAnalyzing = uiState.isAnalyzing,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(18.dp))

        // 2. Category Selector Tabs
        StyleCategorySelector(
            selectedCategory = uiState.selectedCategoryGroup,
            onCategorySelected = { category -> viewModel.selectCategoryGroup(category) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 3. Horizontal Style Carousel for chosen category
        StylesHorizontalCarousel(
            category = uiState.selectedCategoryGroup,
            selectedStyle = uiState.selectedStyle,
            onStyleSelected = { style -> viewModel.selectStyle(style) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        // 4. Customization Accordion Toggle Card
        GlassCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showCustomization = !showCustomization },
            contentPadding = 14.dp
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        tint = CyberCyan,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "PROMPT PARAMETERS & TUNING",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                letterSpacing = 0.8.sp
                            )
                        )
                        Text(
                            text = "${uiState.customization.aspectRatio} • ${uiState.customization.quality} • ${uiState.customization.lighting}",
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

        // Expanded Customization Panel
        AnimatedVisibility(visible = showCustomization) {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                CustomizationPanel(
                    options = uiState.customization,
                    onAspectRatioSelected = { viewModel.updateAspectRatio(it) },
                    onQualitySelected = { viewModel.updateQuality(it) },
                    onMoodSelected = { viewModel.updateMood(it) },
                    onLightingSelected = { viewModel.updateLighting(it) },
                    onCameraSelected = { viewModel.updateCamera(it) },
                    onPreserveIdentityToggled = { viewModel.togglePreserveIdentity(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // 5. Image Analysis Card (if available)
        if (uiState.analysisResult != null) {
            Spacer(modifier = Modifier.height(18.dp))
            ImageAnalysisCard(
                analysis = uiState.analysisResult,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // 6. Generated Prompts Showcase (4 distinct variations)
        if (uiState.generatedPrompts.isNotEmpty()) {
            Spacer(modifier = Modifier.height(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "⚡", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "GENERATED AI PROMPTS (${uiState.generatedPrompts.size})",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.onSurface,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            uiState.generatedPrompts.forEachIndexed { index, prompt ->
                PromptResultCard(
                    prompt = prompt,
                    onSave = { viewModel.savePrompt(prompt) },
                    onRegenerate = { viewModel.generatePrompts() },
                    onEdit = { viewModel.startEditingPrompt(prompt) },
                    onShowToast = { viewModel.showToast(it) },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(14.dp))
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // 7. Pro Tips & Guidance Card
        ProTipsCard()

        Spacer(modifier = Modifier.height(10.dp))

        // 8. Prominent Creator Footer
        CreatorFooter(
            modifier = Modifier.fillMaxWidth(),
            showDetailedCredits = true
        )
    }

    // Edit Prompt Dialog
    if (uiState.editingPrompt != null) {
        EditPromptDialog(
            prompt = uiState.editingPrompt,
            onDismiss = { viewModel.dismissEditingPrompt() },
            onSave = { viewModel.saveEditedPrompt(it) }
        )
    }
}

@Composable
private fun AppHeroHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Sleek top badge with glowing online status
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0x331E293B))
                .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(30.dp))
                .padding(horizontal = 14.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(CyberCyan)
            )
            Text(
                text = "NEURAL PROMPT ENGINE",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Black,
                    color = CyberCyan,
                    fontSize = 10.sp,
                    letterSpacing = 2.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Headline: PROMPT FORGE AI
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "PROMPT ",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = Color.White
                )
            )
            Text(
                text = "FORGE",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = CyberCyan
                )
            )
            Text(
                text = " AI",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    letterSpacing = 2.sp,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Turn Any Photo Into Highly Detailed AI Prompts",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF94A3B8),
                fontWeight = FontWeight.Medium
            )
        )
    }
}

@Composable
private fun StyleCategorySelector(
    selectedCategory: CategoryGroup,
    onCategorySelected: (CategoryGroup) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "TARGET CATEGORY",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                color = Color(0xFF94A3B8),
                fontSize = 10.5.sp,
                letterSpacing = 1.5.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(CategoryGroup.values()) { category ->
                val isSelected = category == selectedCategory
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(
                            if (isSelected) Color(0x2622D3EE)
                            else Color(0x331E293B)
                        )
                        .border(
                            1.dp,
                            if (isSelected) CyberCyan.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.06f),
                            RoundedCornerShape(50)
                        )
                        .clickable { onCategorySelected(category) }
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    color = Color.Transparent
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = category.iconEmoji, fontSize = 14.sp)
                        Text(
                            text = category.displayName.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.Black else FontWeight.SemiBold,
                                color = if (isSelected) CyberCyan else Color(0xFF94A3B8),
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun StylesHorizontalCarousel(
    category: CategoryGroup,
    selectedStyle: PromptStyle,
    onStyleSelected: (PromptStyle) -> Unit
) {
    val styles = StyleCatalog.getStylesForCategory(category)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "CHOOSE STYLE PRESET",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Black,
                color = Color(0xFF94A3B8),
                fontSize = 10.5.sp,
                letterSpacing = 1.5.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(styles) { style ->
                val isSelected = style.id == selectedStyle.id
                Surface(
                    modifier = Modifier
                        .width(190.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            if (isSelected) Color(0x2E22D3EE)
                            else Color(0x4D0F172A)
                        )
                        .border(
                            1.2.dp,
                            if (isSelected) CyberCyan else Color.White.copy(alpha = 0.08f),
                            RoundedCornerShape(20.dp)
                        )
                        .clickable { onStyleSelected(style) }
                        .padding(14.dp),
                    color = Color.Transparent
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = style.iconEmoji, fontSize = 22.sp)
                            if (style.isPopular) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(CyberAmber.copy(alpha = 0.15f))
                                        .border(1.dp, CyberAmber.copy(alpha = 0.3f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "HOT",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = CyberAmber,
                                            fontWeight = FontWeight.Black,
                                            fontSize = 9.sp,
                                            letterSpacing = 0.5.sp
                                        )
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = style.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) CyberCyan else Color.White
                            ),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = style.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = Color(0xFF94A3B8),
                                lineHeight = 14.sp
                            ),
                            maxLines = 2
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProTipsCard() {
    GlassCard(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = 14.dp
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Lightbulb,
                contentDescription = null,
                tint = CyberAmber,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "HOW TO USE THESE PROMPTS",
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = CyberAmber,
                    letterSpacing = 1.sp
                )
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "• 1-Tap Copy: Tap any prompt card to copy the engineered prompt text.\n• Midjourney / Flux / SDXL: Paste directly into your AI image generator prompt field.\n• Negative Prompts: Paste negative prompt text to remove artifacts and deformations.",
            style = MaterialTheme.typography.bodySmall.copy(
                color = Color(0xFF94A3B8),
                lineHeight = 17.sp
            )
        )
    }
}
