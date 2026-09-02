package com.example.domain.model

data class PromptStyle(
    val id: String,
    val title: String,
    val category: CategoryGroup,
    val description: String,
    val iconEmoji: String,
    val visualKeywords: List<String>,
    val defaultNegative: String,
    val isPopular: Boolean = false
)

enum class CategoryGroup(val displayName: String, val emoji: String) {
    GAMING("Gaming Styles", "🎮"),
    PHOTOGRAPHY("Photography Studio", "📷"),
    AI_ART("AI Art Studio", "🎨"),
    CINEMATIC("Cinematic Studio", "🎬"),
    SOCIAL_MEDIA("Social Media Creator", "📱"),
    PHOTO_TRANSFORMER("Photo Transformer", "✨"),
    TRENDING("Trending Styles", "🔥");

    val iconEmoji: String get() = emoji
}

data class CustomizationOptions(
    val aspectRatio: String = "16:9",
    val quality: String = "Ultra Detailed",
    val mood: String = "Cinematic",
    val lighting: String = "Volumetric",
    val camera: String = "Medium Shot",
    val preserveIdentity: Boolean = true,
    val customNotes: String = ""
)

data class StructuredPrompt(
    val id: String = java.util.UUID.randomUUID().toString(),
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
    val isFavorite: Boolean = false
)

data class ImageAnalysisInfo(
    val mainSubject: String = "Unknown subject",
    val characterOrObject: String = "Single central figure/focal object",
    val clothing: String = "Modern casual attire",
    val pose: String = "Natural resting posture facing camera",
    val facialExpression: String = "Neutral confident gaze",
    val cameraAngle: String = "Eye-level medium perspective",
    val background: String = "Ambient atmospheric environment",
    val environment: String = "Urban/interior ambient scene",
    val lighting: String = "Balanced soft directional light",
    val colors: List<String> = listOf("#00E5FF", "#D946EF", "#1E293B", "#F8FAFC"),
    val composition: String = "Rule-of-thirds centered framing with shallow depth of field",
    val importantDetails: List<String> = listOf("High fidelity textures", "Natural surface reflections", "Sharp focal details"),
    val accessories: String = "Subtle accessories and accents",
    val mood: String = "Focused and cinematic",
    val imageQuality: String = "High resolution crisp capture"
)
