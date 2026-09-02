package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.StructuredPrompt

@Entity(tableName = "saved_prompts")
data class PromptEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val variantType: String,
    val fullPrompt: String,
    val subject: String,
    val composition: String,
    val camera: String,
    val lighting: String,
    val environment: String,
    val style: String,
    val details: String,
    val quality: String,
    val negativePrompt: String,
    val category: String,
    val styleId: String,
    val styleName: String,
    val aspectRatio: String,
    val mood: String,
    val imageUri: String? = null,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
) {
    fun toStructuredPrompt(): StructuredPrompt {
        return StructuredPrompt(
            id = id.toString(),
            title = title,
            variantType = variantType,
            fullPrompt = fullPrompt,
            subject = subject,
            composition = composition,
            camera = camera,
            lighting = lighting,
            environment = environment,
            style = style,
            details = details,
            quality = quality,
            negativePrompt = negativePrompt,
            isFavorite = isFavorite
        )
    }
}
