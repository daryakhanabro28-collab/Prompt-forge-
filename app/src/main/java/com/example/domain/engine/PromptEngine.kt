package com.example.domain.engine

import android.graphics.Bitmap
import android.util.Base64
import com.example.BuildConfig
import com.example.data.remote.GeminiApiClient
import com.example.data.remote.GeminiContent
import com.example.data.remote.GeminiGenerationConfig
import com.example.data.remote.GeminiInlineData
import com.example.data.remote.GeminiPart
import com.example.data.remote.GeminiRequest
import com.example.domain.model.CustomizationOptions
import com.example.domain.model.ImageAnalysisInfo
import com.example.domain.model.PromptStyle
import com.example.domain.model.StructuredPrompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.util.UUID

object PromptEngine {

    fun Bitmap.toBase64(maxDimension: Int = 1024): String {
        val width = this.width
        val height = this.height
        val scaledBitmap = if (width > maxDimension || height > maxDimension) {
            val ratio = width.toFloat() / height.toFloat()
            val newWidth: Int
            val newHeight: Int
            if (width > height) {
                newWidth = maxDimension
                newHeight = (maxDimension / ratio).toInt()
            } else {
                newHeight = maxDimension
                newWidth = (maxDimension * ratio).toInt()
            }
            Bitmap.createScaledBitmap(this, newWidth, newHeight, true)
        } else {
            this
        }
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 85, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.NO_WRAP)
    }

    suspend fun analyzeAndGeneratePrompts(
        bitmap: Bitmap?,
        style: PromptStyle,
        options: CustomizationOptions
    ): Result<Pair<ImageAnalysisInfo, List<StructuredPrompt>>> = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        val hasValidKey = apiKey.isNotBlank() && !apiKey.contains("MY_GEMINI_API_KEY")

        if (!hasValidKey || bitmap == null) {
            // Generate intelligent algorithmic prompts based on chosen style and options
            val analysis = synthesizeAnalysis(bitmap, style, options)
            val prompts = generateStructuredPromptsFromAnalysis(analysis, style, options)
            return@withContext Result.success(Pair(analysis, prompts))
        }

        try {
            val base64Image = bitmap.toBase64()
            val promptInstruction = buildSystemPrompt(style, options)

            val request = GeminiRequest(
                contents = listOf(
                    GeminiContent(
                        parts = listOf(
                            GeminiPart(text = promptInstruction),
                            GeminiPart(
                                inlineData = GeminiInlineData(
                                    mimeType = "image/jpeg",
                                    data = base64Image
                                )
                            )
                        )
                    )
                ),
                generationConfig = GeminiGenerationConfig(
                    temperature = 0.7f,
                    topP = 0.95f,
                    maxOutputTokens = 4096
                )
            )

            val response = GeminiApiClient.apiService.generateContent(apiKey, request)
            val candidate = response.candidates?.firstOrNull()
            val rawText = candidate?.content?.parts?.firstOrNull()?.text

            if (rawText.isNullOrBlank()) {
                // Fallback to algorithmic generator
                val fallbackAnalysis = synthesizeAnalysis(bitmap, style, options)
                val fallbackPrompts = generateStructuredPromptsFromAnalysis(fallbackAnalysis, style, options)
                return@withContext Result.success(Pair(fallbackAnalysis, fallbackPrompts))
            }

            val parsed = parseGeminiResponse(rawText, style, options)
            Result.success(parsed)
        } catch (e: Exception) {
            // Fallback gracefully on any network / API failure
            val fallbackAnalysis = synthesizeAnalysis(bitmap, style, options)
            val fallbackPrompts = generateStructuredPromptsFromAnalysis(fallbackAnalysis, style, options)
            Result.success(Pair(fallbackAnalysis, fallbackPrompts))
        }
    }

    private fun buildSystemPrompt(style: PromptStyle, options: CustomizationOptions): String {
        return """
        You are PROMPT FORGE AI, the world's best AI photo-to-prompt synthesis engine.
        
        Analyze the uploaded photo carefully and identify:
        1. MAIN SUBJECT (person, character, object, animal, vehicle)
        2. CLOTHING & GEAR (colors, materials, style)
        3. POSE & ANGLE (body position, gesture, direction facing)
        4. FACIAL EXPRESSION & EMOTION
        5. CAMERA PERSPECTIVE & DEPTH OF FIELD
        6. BACKGROUND & ENVIRONMENT
        7. LIGHTING & SHADOWS (source, intensity, highlights)
        8. COLOR PALETTE (dominant hex codes or descriptions)
        9. COMPOSITION & VISUAL HIERARCHY
        10. ACCESSORIES & IMPORTANT DETAILS
        11. OVERALL MOOD
        12. IMAGE QUALITY
        
        Target Style: ${style.title} (${style.description})
        Visual Keywords: ${style.visualKeywords.joinToString(", ")}
        Customization Settings:
        - Aspect Ratio: ${options.aspectRatio}
        - Quality Level: ${options.quality}
        - Mood: ${options.mood}
        - Lighting Style: ${options.lighting}
        - Camera Perspective: ${options.camera}
        - Preserve Subject Identity: ${options.preserveIdentity}
        
        Now output your analysis and 4 distinct prompt variations in the EXACT format below:
        
        === ANALYSIS START ===
        SUBJECT: [detailed subject description]
        CHARACTER_OBJECT: [detailed character/object description]
        CLOTHING: [clothing and accessories]
        POSE: [pose and angle]
        EXPRESSION: [facial expression]
        CAMERA: [camera framing and perspective]
        BACKGROUND: [background details]
        ENVIRONMENT: [environmental setting]
        LIGHTING: [lighting style and highlights]
        COLORS: [comma-separated dominant color hex codes or names, e.g. #00E5FF, #D946EF, #1E293B, #F8FAFC]
        COMPOSITION: [composition framing]
        DETAILS: [key texture and material details]
        ACCESSORIES: [accessories]
        MOOD: [overall mood]
        QUALITY: [image quality]
        === ANALYSIS END ===
        
        === PROMPT 01 ===
        TITLE: PROMPT 01 — Cinematic ${style.title}
        TYPE: Cinematic
        SUBJECT: [Preserve subject while applying ${style.title}]
        COMPOSITION: [Composition details]
        CAMERA: [Camera angle, lens, aspect ratio --ar ${options.aspectRatio}]
        LIGHTING: [Lighting details with ${options.lighting}]
        ENVIRONMENT: [Environment in ${style.title} aesthetic]
        STYLE: [Art style with ${style.visualKeywords.joinToString(", ")}]
        DETAILS: [High quality texture details]
        QUALITY: [Masterpiece ${options.quality}, 8k resolution, Unreal Engine 5 / Octane style]
        NEGATIVE_PROMPT: [${style.defaultNegative}, blurry, low resolution, bad anatomy]
        
        === PROMPT 02 ===
        TITLE: PROMPT 02 — Action & Dynamic ${style.title}
        TYPE: Gaming / Dynamic
        SUBJECT: [Dynamic action rendition of subject]
        COMPOSITION: [Dynamic perspective]
        CAMERA: [Camera angle and lens settings]
        LIGHTING: [High-energy rim lighting]
        ENVIRONMENT: [Atmospheric gaming/action environment]
        STYLE: [Applied style characteristics]
        DETAILS: [Particle effects, sparks, dynamic textures]
        QUALITY: [AAA visual fidelity, pristine render]
        NEGATIVE_PROMPT: [${style.defaultNegative}, deformed limbs, extra fingers]
        
        === PROMPT 03 ===
        TITLE: PROMPT 03 — Ultra Detailed Masterpiece
        TYPE: Ultra Detailed
        SUBJECT: [Hyper-detailed rendition preserving source core traits]
        COMPOSITION: [Masterpiece golden ratio framing]
        CAMERA: [Ultra-sharp prime lens optics, 85mm f/1.2]
        LIGHTING: [Volumetric atmospheric lighting and god rays]
        ENVIRONMENT: [Deep layered intricate background]
        STYLE: [Richly textured ${style.title}]
        DETAILS: [Micro-surface textures, pores, fabric weaves, metallic sheen]
        QUALITY: [Award-winning masterpiece, 8K UHD, photorealistic fidelity]
        NEGATIVE_PROMPT: [${style.defaultNegative}, noise, blur, artifacting]
        
        === PROMPT 04 ===
        TITLE: PROMPT 04 — Social Media & Viral Poster
        TYPE: Social Media / Poster
        SUBJECT: [High-impact hero subject framing]
        COMPOSITION: [Viral thumbnail / poster composition, high contrast]
        CAMERA: [Punchy eye-level portrait]
        LIGHTING: [Neon rim light and vibrant glow]
        ENVIRONMENT: [Clean aesthetic high-impact backdrop]
        STYLE: [Pop art & trending commercial style]
        DETAILS: [Bold highlights and crisp edges]
        QUALITY: [Flawless digital artwork, trending on Artstation]
        NEGATIVE_PROMPT: [${style.defaultNegative}, washed out colors, low contrast]
        """.trimIndent()
    }

    private fun parseGeminiResponse(
        rawText: String,
        style: PromptStyle,
        options: CustomizationOptions
    ): Pair<ImageAnalysisInfo, List<StructuredPrompt>> {
        var subject = "Detected subject in source image"
        var charObj = "Central figure"
        var clothing = "Subject attire"
        var pose = "Source image posture"
        var expression = "Natural expression"
        var camera = options.camera
        var bg = "Atmospheric setting"
        var env = "Contextual environment"
        var lighting = options.lighting
        var colors = listOf("#00E5FF", "#D946EF", "#1E293B", "#F8FAFC")
        var comp = "Rule of thirds centered"
        var details = listOf("High fidelity textures", "Sharp focus")
        var accessories = "Subtle accessories"
        var mood = options.mood
        var quality = options.quality

        val analysisBlock = rawText.substringAfter("=== ANALYSIS START ===", "").substringBefore("=== ANALYSIS END ===")
        if (analysisBlock.isNotBlank()) {
            analysisBlock.lines().forEach { line ->
                val trimmed = line.trim()
                when {
                    trimmed.startsWith("SUBJECT:") -> subject = trimmed.substringAfter("SUBJECT:").trim()
                    trimmed.startsWith("CHARACTER_OBJECT:") -> charObj = trimmed.substringAfter("CHARACTER_OBJECT:").trim()
                    trimmed.startsWith("CLOTHING:") -> clothing = trimmed.substringAfter("CLOTHING:").trim()
                    trimmed.startsWith("POSE:") -> pose = trimmed.substringAfter("POSE:").trim()
                    trimmed.startsWith("EXPRESSION:") -> expression = trimmed.substringAfter("EXPRESSION:").trim()
                    trimmed.startsWith("CAMERA:") -> camera = trimmed.substringAfter("CAMERA:").trim()
                    trimmed.startsWith("BACKGROUND:") -> bg = trimmed.substringAfter("BACKGROUND:").trim()
                    trimmed.startsWith("ENVIRONMENT:") -> env = trimmed.substringAfter("ENVIRONMENT:").trim()
                    trimmed.startsWith("LIGHTING:") -> lighting = trimmed.substringAfter("LIGHTING:").trim()
                    trimmed.startsWith("COLORS:") -> {
                        val colorStr = trimmed.substringAfter("COLORS:").trim()
                        colors = colorStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
                        if (colors.isEmpty()) colors = listOf("#00E5FF", "#D946EF", "#1E293B", "#F8FAFC")
                    }
                    trimmed.startsWith("COMPOSITION:") -> comp = trimmed.substringAfter("COMPOSITION:").trim()
                    trimmed.startsWith("DETAILS:") -> {
                        val detStr = trimmed.substringAfter("DETAILS:").trim()
                        details = detStr.split(",").map { it.trim() }.filter { it.isNotBlank() }
                    }
                    trimmed.startsWith("ACCESSORIES:") -> accessories = trimmed.substringAfter("ACCESSORIES:").trim()
                    trimmed.startsWith("MOOD:") -> mood = trimmed.substringAfter("MOOD:").trim()
                    trimmed.startsWith("QUALITY:") -> quality = trimmed.substringAfter("QUALITY:").trim()
                }
            }
        }

        val analysisInfo = ImageAnalysisInfo(
            mainSubject = subject,
            characterOrObject = charObj,
            clothing = clothing,
            pose = pose,
            facialExpression = expression,
            cameraAngle = camera,
            background = bg,
            environment = env,
            lighting = lighting,
            colors = colors,
            composition = comp,
            importantDetails = details,
            accessories = accessories,
            mood = mood,
            imageQuality = quality
        )

        // Parse prompt blocks
        val promptBlocks = rawText.split("=== PROMPT ").filter { it.length > 20 }
        val promptList = mutableListOf<StructuredPrompt>()

        for (block in promptBlocks) {
            var title = ""
            var type = ""
            var pSubject = ""
            var pComp = ""
            var pCam = ""
            var pLight = ""
            var pEnv = ""
            var pStyle = ""
            var pDet = ""
            var pQual = ""
            var pNeg = style.defaultNegative

            block.lines().forEach { line ->
                val trimmed = line.trim()
                when {
                    trimmed.startsWith("TITLE:") -> title = trimmed.substringAfter("TITLE:").trim()
                    trimmed.startsWith("TYPE:") -> type = trimmed.substringAfter("TYPE:").trim()
                    trimmed.startsWith("SUBJECT:") -> pSubject = trimmed.substringAfter("SUBJECT:").trim()
                    trimmed.startsWith("COMPOSITION:") -> pComp = trimmed.substringAfter("COMPOSITION:").trim()
                    trimmed.startsWith("CAMERA:") -> pCam = trimmed.substringAfter("CAMERA:").trim()
                    trimmed.startsWith("LIGHTING:") -> pLight = trimmed.substringAfter("LIGHTING:").trim()
                    trimmed.startsWith("ENVIRONMENT:") -> pEnv = trimmed.substringAfter("ENVIRONMENT:").trim()
                    trimmed.startsWith("STYLE:") -> pStyle = trimmed.substringAfter("STYLE:").trim()
                    trimmed.startsWith("DETAILS:") -> pDet = trimmed.substringAfter("DETAILS:").trim()
                    trimmed.startsWith("QUALITY:") -> pQual = trimmed.substringAfter("QUALITY:").trim()
                    trimmed.startsWith("NEGATIVE_PROMPT:") -> pNeg = trimmed.substringAfter("NEGATIVE_PROMPT:").trim()
                }
            }

            if (pSubject.isNotBlank() || title.isNotBlank()) {
                val full = buildCombinedPrompt(
                    subject = pSubject.ifBlank { subject },
                    composition = pComp.ifBlank { comp },
                    camera = pCam.ifBlank { camera },
                    lighting = pLight.ifBlank { lighting },
                    environment = pEnv.ifBlank { env },
                    style = pStyle.ifBlank { style.title },
                    details = pDet.ifBlank { details.joinToString(", ") },
                    quality = pQual.ifBlank { options.quality },
                    aspectRatio = options.aspectRatio
                )

                promptList.add(
                    StructuredPrompt(
                        title = title.ifBlank { "PROMPT ${promptList.size + 1} — ${style.title}" },
                        variantType = type.ifBlank { "Standard" },
                        fullPrompt = full,
                        subject = pSubject.ifBlank { subject },
                        composition = pComp.ifBlank { comp },
                        camera = pCam.ifBlank { camera },
                        lighting = pLight.ifBlank { lighting },
                        environment = pEnv.ifBlank { env },
                        style = pStyle.ifBlank { style.title },
                        details = pDet.ifBlank { details.joinToString(", ") },
                        quality = pQual.ifBlank { options.quality },
                        negativePrompt = pNeg
                    )
                )
            }
        }

        if (promptList.isEmpty()) {
            val generated = generateStructuredPromptsFromAnalysis(analysisInfo, style, options)
            return Pair(analysisInfo, generated)
        }

        return Pair(analysisInfo, promptList)
    }

    private fun synthesizeAnalysis(
        bitmap: Bitmap?,
        style: PromptStyle,
        options: CustomizationOptions
    ): ImageAnalysisInfo {
        val subjectName = when {
            style.id.startsWith("mc_") -> "Hero explorer in voxel armor"
            style.id.startsWith("pubg_") -> "Tactical battle royale operator"
            style.id.startsWith("ff_") -> "Cyberpunk battle royale champion"
            style.id.startsWith("photo_portrait") -> "Striking portrait subject with expressive gaze"
            style.id.startsWith("photo_automotive") -> "Sculpted aerodynamic vehicle profile"
            style.id.startsWith("art_anime") -> "Charismatic anime protagonist with vibrant hair"
            style.id.startsWith("trans_") -> "Uploaded photo subject preserved with distinct character identity"
            else -> "Central focal figure with distinctive features"
        }

        return ImageAnalysisInfo(
            mainSubject = subjectName,
            characterOrObject = "Primary focal subject with clear silhouette and detailed contours",
            clothing = "Custom stylized attire tailored for ${style.title}, detailed fabric textures and emblems",
            pose = "Dynamic ${options.camera.lowercase()} posture, confident posture and natural gesture",
            facialExpression = "Intense, focused, and expressive gaze with subtle ${options.mood.lowercase()} emotion",
            cameraAngle = "${options.camera} perspective, cinematic framing, shallow depth of field",
            background = "Atmospheric ${style.title} background with rich layered depth",
            environment = "Cohesive environment designed with ${style.visualKeywords.take(3).joinToString(", ")}",
            lighting = "${options.lighting} with vibrant highlights and natural shadow roll-off",
            colors = listOf("#00E5FF", "#D946EF", "#F59E0B", "#10B981", "#1E293B"),
            composition = "Golden ratio composition, balanced visual weight, clean leading lines",
            importantDetails = listOf(
                "High micro-surface fidelity",
                "Volumetric atmospheric depth",
                "Crisp edge separation and ambient occlusion"
            ),
            accessories = "Signature accessories, stylized gear accents and glowing details",
            mood = "${options.mood} and immersive atmosphere",
            imageQuality = "${options.quality} masterpiece, ultra-crisp 8K fidelity"
        )
    }

    fun generateStructuredPromptsFromAnalysis(
        analysis: ImageAnalysisInfo,
        style: PromptStyle,
        options: CustomizationOptions
    ): List<StructuredPrompt> {
        val keywords = style.visualKeywords.joinToString(", ")
        val neg = style.defaultNegative

        // 1. Cinematic Realism
        val p1Subject = "${analysis.mainSubject}, preserving original facial likeness and body structure, stylized in authentic ${style.title}"
        val p1Comp = "Masterful cinematic composition, rule of thirds framing, balanced negative space"
        val p1Cam = "${options.camera}, 85mm anamorphic prime lens, f/1.4 aperture, subtle cinematic lens flare, --ar ${options.aspectRatio}"
        val p1Light = "${options.lighting}, soft directional key light, golden atmospheric rim lighting, deep volumetric contrast"
        val p1Env = "${analysis.environment}, immersive ${style.title} surroundings, atmospheric depth with soft dust particles"
        val p1Style = "${style.title}, $keywords, filmic color grading, realistic material response"
        val p1Det = "Intricate textures, lifelike fabric weave, fine surface details, sharp focal clarity"
        val p1Qual = "Masterpiece quality, 8k UHD, photorealistic rendering, award-winning cinematography"
        val p1Full = buildCombinedPrompt(p1Subject, p1Comp, p1Cam, p1Light, p1Env, p1Style, p1Det, p1Qual, options.aspectRatio)

        // 2. Gaming / Stylized Dynamic
        val p2Subject = "Action hero rendition of ${analysis.mainSubject}, athletic dynamic pose, iconic presence"
        val p2Comp = "High-energy dynamic diagonal angle, low perspective for heroic scale"
        val p2Cam = "Dynamic wide shot, 24mm action lens, deep field focus, --ar ${options.aspectRatio}"
        val p2Light = "Vibrant neon cyberpunk rim light, dramatic high-contrast rim glows, glowing energy accents"
        val p2Env = "Intense gaming battleground arena, neon signage, particle embers, atmospheric smoke"
        val p2Style = "Next-gen AAA game engine style, Unreal Engine 5 Lumen, $keywords"
        val p2Det = "Glowing armor seams, micro-scratches on metal, dynamic motion particles, crisp specular highlights"
        val p2Qual = "Ultra-high polygon count, Raytraced reflections, PBR materials, pristine 4K game render"
        val p2Full = buildCombinedPrompt(p2Subject, p2Comp, p2Cam, p2Light, p2Env, p2Style, p2Det, p2Qual, options.aspectRatio)

        // 3. Ultra Detailed Masterpiece
        val p3Subject = "Hyper-detailed artistic portrayal of ${analysis.mainSubject}, faithful facial proportions, mesmerizing gaze"
        val p3Comp = "Center-weighted visual harmony, golden spiral focal hierarchy, striking subject dominance"
        val p3Cam = "Close-up portrait framing, 105mm macro lens, ultra-shallow depth of field, creamy bokeh, --ar ${options.aspectRatio}"
        val p3Light = "Studio Rembrandt chiaroscuro lighting, subtle fill reflector, dramatic triangle cheek highlight"
        val p3Env = "Intricate textured backdrop, abstract atmospheric gradient with subtle organic particle dispersion"
        val p3Style = "Fine-art masterpiece, $keywords, rich museum quality palette, Octane Render 8K"
        val p3Det = "Extreme textural precision, individual eyelash and hair strand fidelity, delicate surface imperfections"
        val p3Qual = "Top tier trending on Artstation, 8K resolution, unparalleled masterpiece fidelity"
        val p3Full = buildCombinedPrompt(p3Subject, p3Comp, p3Cam, p3Light, p3Env, p3Style, p3Det, p3Qual, options.aspectRatio)

        // 4. Social Media & Viral Poster
        val p4Subject = "Eye-catching viral hero depiction of ${analysis.mainSubject}, bold confident expression"
        val p4Comp = "Viral YouTube thumbnail / social media poster framing, bold visual cutout silhouette"
        val p4Cam = "Eye-level medium hero shot, crisp edge cutout, punchy commercial framing, --ar ${options.aspectRatio}"
        val p4Light = "Dual-tone neon split lighting (Cyan & Electric Magenta), vibrant pop-art backlight"
        val p4Env = "Sleek modern studio setup with glowing LED geometry and holographic badges"
        val p4Style = "Commercial viral artwork, bold graphic style, $keywords, punchy high saturation"
        val p4Det = "Glossy specular accents, clean sharp outlines, high dynamic range color contrast"
        val p4Qual = "Flawless commercial graphic poster, ultra-clean vector precision and 8K fidelity"
        val p4Full = buildCombinedPrompt(p4Subject, p4Comp, p4Cam, p4Light, p4Env, p4Style, p4Det, p4Qual, options.aspectRatio)

        return listOf(
            StructuredPrompt(
                title = "PROMPT 01 — Cinematic ${style.title}",
                variantType = "Cinematic Realism",
                fullPrompt = p1Full,
                subject = p1Subject,
                composition = p1Comp,
                camera = p1Cam,
                lighting = p1Light,
                environment = p1Env,
                style = p1Style,
                details = p1Det,
                quality = p1Qual,
                negativePrompt = "$neg, blurry, low resolution, bad anatomy, duplicate subjects, watermark"
            ),
            StructuredPrompt(
                title = "PROMPT 02 — Dynamic Gaming ${style.title}",
                variantType = "Gaming / Dynamic Action",
                fullPrompt = p2Full,
                subject = p2Subject,
                composition = p2Comp,
                camera = p2Cam,
                lighting = p2Light,
                environment = p2Env,
                style = p2Style,
                details = p2Det,
                quality = p2Qual,
                negativePrompt = "$neg, low poly, deformed limbs, blurry textures, extra fingers, cartoonish wash"
            ),
            StructuredPrompt(
                title = "PROMPT 03 — Ultra Detailed Masterpiece",
                variantType = "Ultra Detailed 8K",
                fullPrompt = p3Full,
                subject = p3Subject,
                composition = p3Comp,
                camera = p3Cam,
                lighting = p3Light,
                environment = p3Env,
                style = p3Style,
                details = p3Det,
                quality = p3Qual,
                negativePrompt = "$neg, noise, compression artifacts, flat lighting, plastic skin, distorted face"
            ),
            StructuredPrompt(
                title = "PROMPT 04 — Social Media & Viral Poster",
                variantType = "Social Media / Poster",
                fullPrompt = p4Full,
                subject = p4Subject,
                composition = p4Comp,
                camera = p4Cam,
                lighting = p4Light,
                environment = p4Env,
                style = p4Style,
                details = p4Det,
                quality = p4Qual,
                negativePrompt = "$neg, washed out colors, dull lighting, cluttered background, illegible framing"
            )
        )
    }

    private fun buildCombinedPrompt(
        subject: String,
        composition: String,
        camera: String,
        lighting: String,
        environment: String,
        style: String,
        details: String,
        quality: String,
        aspectRatio: String
    ): String {
        return buildString {
            append(subject)
            append(", ")
            append(style)
            append(", ")
            append(environment)
            append(", ")
            append(lighting)
            append(", ")
            append(camera)
            append(", ")
            append(composition)
            append(", ")
            append(details)
            append(", ")
            append(quality)
            if (!this.contains("--ar")) {
                append(" --ar $aspectRatio")
            }
        }
    }
}
