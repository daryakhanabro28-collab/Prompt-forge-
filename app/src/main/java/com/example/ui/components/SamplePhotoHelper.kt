package com.example.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader

data class SamplePhoto(
    val id: String,
    val title: String,
    val description: String,
    val iconEmoji: String,
    val primaryColor: Int,
    val secondaryColor: Int
)

object SamplePhotoProvider {
    val samples = listOf(
        SamplePhoto(
            id = "cyber_operator",
            title = "Cyber Operator",
            description = "Futuristic techwear tactical portrait",
            iconEmoji = "🥷",
            primaryColor = Color.parseColor("#00E5FF"),
            secondaryColor = Color.parseColor("#7C4DFF")
        ),
        SamplePhoto(
            id = "mountain_vista",
            title = "Alpine Peak",
            description = "Golden hour mountain landscape",
            iconEmoji = "🏔️",
            primaryColor = Color.parseColor("#FF9100"),
            secondaryColor = Color.parseColor("#D500F9")
        ),
        SamplePhoto(
            id = "hypercar",
            title = "Cyber Hypercar",
            description = "Aerodynamic supercar under neon lights",
            iconEmoji = "🏎️",
            primaryColor = Color.parseColor("#00E676"),
            secondaryColor = Color.parseColor("#00B0FF")
        ),
        SamplePhoto(
            id = "studio_portrait",
            title = "Studio Headshot",
            description = "Dramatic three-point executive portrait",
            iconEmoji = "👤",
            primaryColor = Color.parseColor("#FF6D00"),
            secondaryColor = Color.parseColor("#263238")
        ),
        SamplePhoto(
            id = "anime_character",
            title = "Anime Hero",
            description = "Vibrant fantasy warrior avatar",
            iconEmoji = "⚔️",
            primaryColor = Color.parseColor("#F50057"),
            secondaryColor = Color.parseColor("#651FFF")
        )
    )

    fun createSampleBitmap(sample: SamplePhoto, width: Int = 600, height: Int = 600): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        // Gradient Background
        val shader = LinearGradient(
            0f, 0f, width.toFloat(), height.toFloat(),
            sample.primaryColor, sample.secondaryColor,
            Shader.TileMode.CLAMP
        )
        paint.shader = shader
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        // Geometric aesthetic overlay
        paint.shader = null
        paint.color = Color.parseColor("#20000000")
        canvas.drawCircle(width * 0.5f, height * 0.45f, width * 0.35f, paint)

        paint.color = Color.parseColor("#35FFFFFF")
        canvas.drawCircle(width * 0.5f, height * 0.45f, width * 0.28f, paint)

        // Inner shape
        val path = Path()
        when (sample.id) {
            "cyber_operator", "studio_portrait", "anime_character" -> {
                // Head and shoulders silhouette
                paint.color = Color.parseColor("#E0000000")
                // Head
                canvas.drawCircle(width * 0.5f, height * 0.35f, width * 0.14f, paint)
                // Body/shoulders
                val bodyRect = RectF(width * 0.25f, height * 0.52f, width * 0.75f, height * 0.95f)
                canvas.drawRoundRect(bodyRect, 40f, 40f, paint)
                // Neon glow visor or eye line
                paint.color = Color.parseColor("#00E5FF")
                paint.strokeWidth = 8f
                paint.style = Paint.Style.STROKE
                canvas.drawLine(width * 0.42f, height * 0.35f, width * 0.58f, height * 0.35f, paint)
            }
            "mountain_vista" -> {
                // Mountain peaks
                paint.color = Color.parseColor("#D0000000")
                paint.style = Paint.Style.FILL
                path.moveTo(width * 0.1f, height * 0.85f)
                path.lineTo(width * 0.5f, height * 0.3f)
                path.lineTo(width * 0.9f, height * 0.85f)
                path.close()
                canvas.drawPath(path, paint)

                paint.color = Color.parseColor("#90FFFFFF")
                val sunRect = RectF(width * 0.65f, height * 0.15f, width * 0.85f, height * 0.35f)
                canvas.drawOval(sunRect, paint)
            }
            "hypercar" -> {
                // Supercar silhouette
                paint.color = Color.parseColor("#D0000000")
                paint.style = Paint.Style.FILL
                val carRect = RectF(width * 0.15f, height * 0.45f, width * 0.85f, height * 0.7f)
                canvas.drawRoundRect(carRect, 24f, 24f, paint)
                // Headlights
                paint.color = Color.parseColor("#00E5FF")
                canvas.drawCircle(width * 0.25f, height * 0.58f, 16f, paint)
                canvas.drawCircle(width * 0.75f, height * 0.58f, 16f, paint)
            }
        }

        // Title text in bottom badge
        paint.reset()
        paint.isAntiAlias = true
        paint.color = Color.parseColor("#CC000000")
        val badgeRect = RectF(width * 0.08f, height * 0.82f, width * 0.92f, height * 0.95f)
        canvas.drawRoundRect(badgeRect, 20f, 20f, paint)

        paint.color = Color.WHITE
        paint.textSize = 28f
        paint.textAlign = Paint.Align.CENTER
        paint.isFakeBoldText = true
        canvas.drawText("PROMPT FORGE AI • ${sample.title.uppercase()}", width * 0.5f, height * 0.90f, paint)

        return bitmap
    }
}
