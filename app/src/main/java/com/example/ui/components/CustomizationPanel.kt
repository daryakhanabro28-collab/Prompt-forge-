package com.example.ui.components

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.CustomizationOptions
import com.example.ui.theme.CyberCyan
import com.example.ui.theme.CyberMagenta

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomizationPanel(
    options: CustomizationOptions,
    onAspectRatioSelected: (String) -> Unit,
    onQualitySelected: (String) -> Unit,
    onMoodSelected: (String) -> Unit,
    onLightingSelected: (String) -> Unit,
    onCameraSelected: (String) -> Unit,
    onPreserveIdentityToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val aspectRatios = listOf("1:1", "4:5", "16:9", "9:16", "3:4")
    val qualities = listOf("Standard", "High", "Ultra Detailed")
    val moods = listOf("Cinematic", "Dark", "Bright", "Epic", "Peaceful", "Dramatic", "Energetic")
    val lightings = listOf("Studio", "Natural", "Golden Hour", "Neon", "Dramatic", "Volumetric")
    val cameras = listOf("Close-up", "Medium Shot", "Full Body", "Wide Shot", "Low Angle", "High Angle", "Eye Level")

    GlassCard(
        modifier = modifier,
        contentPadding = 16.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "PROMPT CUSTOMIZATION CONTROLS",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.8.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Aspect Ratio
        CustomizationGroup(
            title = "ASPECT RATIO",
            items = aspectRatios,
            selectedItem = options.aspectRatio,
            onSelect = onAspectRatioSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Quality
        CustomizationGroup(
            title = "RENDER QUALITY",
            items = qualities,
            selectedItem = options.quality,
            onSelect = onQualitySelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mood
        CustomizationGroup(
            title = "MOOD & ATMOSPHERE",
            items = moods,
            selectedItem = options.mood,
            onSelect = onMoodSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Lighting
        CustomizationGroup(
            title = "LIGHTING ENGINE",
            items = lightings,
            selectedItem = options.lighting,
            onSelect = onLightingSelected
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Camera
        CustomizationGroup(
            title = "CAMERA PERSPECTIVE",
            items = cameras,
            selectedItem = options.camera,
            onSelect = onCameraSelected
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Preserve Identity Switch
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Preserve Subject Identity",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Text(
                    text = "Retain facial features and pose in transformations",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Switch(
                checked = options.preserveIdentity,
                onCheckedChange = onPreserveIdentityToggled,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.Black,
                    checkedTrackColor = CyberCyan
                )
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CustomizationGroup(
    title: String,
    items: List<String>,
    selectedItem: String,
    onSelect: (String) -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items.forEach { item ->
                val isSelected = item.equals(selectedItem, ignoreCase = true)
                Surface(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (isSelected) CyberCyan.copy(alpha = 0.2f)
                            else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                        )
                        .border(
                            1.dp,
                            if (isSelected) CyberCyan else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                            RoundedCornerShape(8.dp)
                        )
                        .clickable { onSelect(item) }
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    color = Color.Transparent
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) CyberCyan else MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}
