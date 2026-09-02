package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.domain.model.ImageAnalysisInfo
import com.example.ui.theme.CyberCyan

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ImageAnalysisCard(
    analysis: ImageAnalysisInfo,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        isHighlighted = true,
        contentPadding = 16.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Analytics,
                contentDescription = null,
                tint = CyberCyan,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "AI VISION ANALYSIS TELEMETRY",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 0.8.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Grid of vision breakdown telemetry
        AnalysisTelemetryRow("Main Subject", "🎯", analysis.mainSubject)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Character / Object", "👤", analysis.characterOrObject)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Clothing & Gear", "👔", analysis.clothing)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Pose & Gesture", "🧍", analysis.pose)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Facial Expression", "😊", analysis.facialExpression)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Camera Angle & Optics", "📷", analysis.cameraAngle)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Background & Scene", "🏞️", analysis.background)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Lighting & Shadows", "💡", analysis.lighting)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Composition Framing", "📐", analysis.composition)
        Spacer(modifier = Modifier.height(8.dp))
        AnalysisTelemetryRow("Overall Mood & Vibe", "🎭", analysis.mood)

        Spacer(modifier = Modifier.height(12.dp))

        // Extracted Color Palette
        Text(
            text = "EXTRACTED COLOR PALETTE",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                letterSpacing = 0.8.sp
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            analysis.colors.forEach { hexColor ->
                val parsedColor = try {
                    Color(android.graphics.Color.parseColor(hexColor))
                } catch (e: Exception) {
                    CyberCyan
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(parsedColor)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = hexColor,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontFamily = FontFamily.Monospace,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun AnalysisTelemetryRow(label: String, emoji: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = emoji, fontSize = 14.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = CyberCyan
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
