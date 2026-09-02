package com.example.domain.model

object StyleCatalog {

    val allStyles: List<PromptStyle> = listOf(
        // === GAMING STYLES ===
        // Minecraft styles
        PromptStyle(
            id = "mc_blocky_3d",
            title = "Minecraft Blocky 3D World",
            category = CategoryGroup.GAMING,
            description = "Iconic voxel cube geometry with rich shaders and RTX raytraced block lighting",
            iconEmoji = "🟩",
            visualKeywords = listOf("voxel geometry", "textured pixel cubes", "raytraced voxel lighting", "chunky volumetric blocks", "crisp cube foliage"),
            defaultNegative = "smooth curves, non-cubic photorealism, round spheres, blurry textures, extra limbs",
            isPopular = true
        ),
        PromptStyle(
            id = "mc_character",
            title = "Minecraft Character Skin",
            category = CategoryGroup.GAMING,
            description = "Stylized 3D voxel avatar with custom textured pixel armour, cape, and enchanted gear",
            iconEmoji = "🧑‍🌾",
            visualKeywords = listOf("custom voxel character skin", "pixel-art armor", "enchanted glowing weapons", "blocky proportion limbs", "isometric avatar"),
            defaultNegative = "realistic human skin, curved round head, photorealistic face, anatomically detailed fingers"
        ),
        PromptStyle(
            id = "mc_house_build",
            title = "Minecraft Grand Architecture & Build",
            category = CategoryGroup.GAMING,
            description = "Detailed voxel mansion, castle or modern house with oak planks, stone bricks and glass panes",
            iconEmoji = "🏰",
            visualKeywords = listOf("stone brick fortress", "spruce wood timber framing", "interior lantern lighting", "stained glass windows", "block terraforming"),
            defaultNegative = "real concrete, curved architecture, circular columns, untextured solids"
        ),
        PromptStyle(
            id = "mc_cinematic_scene",
            title = "Minecraft Cinematic Shaders",
            category = CategoryGroup.GAMING,
            description = "Breathtaking cinematic render with god rays, water reflections, and volumetric morning fog",
            iconEmoji = "🌅",
            visualKeywords = listOf("BSL shaders aesthetic", "volumetric sun rays", "screen space reflections on water", "dense block canopy", "warm atmospheric bloom"),
            defaultNegative = "flat lighting, 2D sprites, low contrast, washed out tones"
        ),
        PromptStyle(
            id = "mc_survival",
            title = "Minecraft Survival Adventure",
            category = CategoryGroup.GAMING,
            description = "Epic wilderness survival camp, glowing campfire, torchlit caverns, and danger lurking",
            iconEmoji = "🏕️",
            visualKeywords = listOf("campfire smoke particles", "torchlit oak outpost", "deep cave entrance", "redstone machinery", "night sky constellation"),
            defaultNegative = "modern cars, smooth plastics, photoreal faces"
        ),
        PromptStyle(
            id = "mc_village",
            title = "Minecraft Bustling Village",
            category = CategoryGroup.GAMING,
            description = "Cozy procedural village with cobblestone paths, farm crops, windmills, and iron golem watch",
            iconEmoji = "🌾",
            visualKeywords = listOf("cobblestone pathways", "wheat crop terraces", "wooden village houses", "iron golem guardian", "bell tower"),
            defaultNegative = "modern skyscrapers, asphalt roads"
        ),
        PromptStyle(
            id = "mc_fantasy_world",
            title = "Minecraft Fantasy Realm",
            category = CategoryGroup.GAMING,
            description = "Floating block islands, mystical glowing netherite/amethyst crystals, and enchanted biomes",
            iconEmoji = "🔮",
            visualKeywords = listOf("floating sky islands", "glowing amethyst geode", "bioluminescent mushroom biome", "mythical voxel dragon", "ender pearl particles"),
            defaultNegative = "flat terrain, mundane real world architecture"
        ),

        // PUBG-Inspired
        PromptStyle(
            id = "pubg_tactical",
            title = "Tactical Battle Royale Aesthetic",
            category = CategoryGroup.GAMING,
            description = "Gritty, high-stakes tactical shooter style with military ballistic gear and weathered terrain",
            iconEmoji = "🪖",
            visualKeywords = listOf("military tactical vest", "ballistic helmet with visor", "weathered combat fatigues", "abandoned Eastern European industrial landscape", "dusty air particulate"),
            defaultNegative = "cartoonish colors, neon fantasy magic, low poly, distorted weaponry",
            isPopular = true
        ),
        PromptStyle(
            id = "pubg_soldier_scene",
            title = "Cinematic Combat Soldier Scene",
            category = CategoryGroup.GAMING,
            description = "Tense squad ambush in foggy forest or dilapidated warehouse with muzzle flare reflections",
            iconEmoji = "🎖️",
            visualKeywords = listOf("tactical weapon sling", "smoke grenade plume", "overcast moody sky", "sweat and mud skin detail", "dynamic tactical crouch"),
            defaultNegative = "cartoon graphics, childish proportions, bright rainbow colors"
        ),
        PromptStyle(
            id = "pubg_airdrop",
            title = "Battle Royale Airdrop Environment",
            category = CategoryGroup.GAMING,
            description = "Iconic red cargo crate emitting thick crimson smoke signal across sprawling open hills",
            iconEmoji = "📦",
            visualKeywords = listOf("red beacon smoke canister", "open steppe grassland", "distant coastal radar dish", "buggy vehicle tire tracks", "golden hour combat lighting"),
            defaultNegative = "low texture resolution, fake lighting, flat ground"
        ),
        PromptStyle(
            id = "pubg_character_portrait",
            title = "Tactical Operator Portrait",
            category = CategoryGroup.GAMING,
            description = "High detail close-up of a hardened battle royale survivor with aviator sunglasses and headset",
            iconEmoji = "👤",
            visualKeywords = listOf("intense focused gaze", "tactical comms headset", "scratched combat goggles", "fabric weave on combat jacket", "gritty film grain"),
            defaultNegative = "airbrushed plastic skin, smooth face, extra fingers, blurry eyes"
        ),

        // Free Fire-Inspired
        PromptStyle(
            id = "ff_neon_action",
            title = "High-Energy Battle Royale Aesthetic",
            category = CategoryGroup.GAMING,
            description = "Vibrant, hyper-stylish combat action featuring neon accents, streetwear armor, and explosive energy",
            iconEmoji = "⚡",
            visualKeywords = listOf("futuristic cyber streetwear", "glowing neon tribal decals", "stylish high-top sneakers", "graffiti energy bursts", "electric particle aura"),
            defaultNegative = "dull muted colors, drab monochrome, low fidelity",
            isPopular = true
        ),
        PromptStyle(
            id = "ff_character_poster",
            title = "Neon Gaming Character Poster",
            category = CategoryGroup.GAMING,
            description = "Iconic hero splash art with signature weapon, futuristic visor, and electric lighting",
            iconEmoji = "🔥",
            visualKeywords = listOf("stylized dynamic hero pose", "cyberpunk combat katana", "glowing chromatic jacket", "neon purple rim lighting", "holographic HUD elements"),
            defaultNegative = "pale washed colors, deformed hands, bad anatomy"
        ),
        PromptStyle(
            id = "ff_action_scene",
            title = "High-Octane Battle Arena Action",
            category = CategoryGroup.GAMING,
            description = "Sliding under fire with dual futuristic pistols amid neon billboards and sports cars",
            iconEmoji = "💥",
            visualKeywords = listOf("dynamic motion blur streaks", "speed trail particles", "cyber sports car background", "sparking bullet impacts", "electric blue speed lines"),
            defaultNegative = "static boring pose, blurry foreground, low resolution"
        ),

        // Additional Gaming Styles
        PromptStyle(
            id = "game_gta",
            title = "GTA-Inspired Open World Action",
            category = CategoryGroup.GAMING,
            description = "Sun-drenched coastal city, luxury hypercars, palm trees, and stylish urban crime drama aesthetic",
            iconEmoji = "🌴",
            visualKeywords = listOf("sun-soaked California golden hour", "sleek exotic supercar", "palm tree silhouettes", "retro sunglasses", "warm high-contrast saturation"),
            defaultNegative = "medieval fantasy, anime cartoon, low resolution textures"
        ),
        PromptStyle(
            id = "game_fortnite",
            title = "Fortnite-Inspired Stylized Battle",
            category = CategoryGroup.GAMING,
            description = "Colorful stylized 3D toon-shaded aesthetic with playful hero cosmetics and lush vibrant terrain",
            iconEmoji = "🦄",
            visualKeywords = listOf("stylized PBR toon shading", "rich saturated pastel palette", "chunky expressive hero silhouette", "floating battle bus sky", "playful energy glow"),
            defaultNegative = "grim photorealism, gritty war gore, washed out muddy tones"
        ),
        PromptStyle(
            id = "game_roblox",
            title = "Roblox-Inspired Block World",
            category = CategoryGroup.GAMING,
            description = "Charming smooth blocky avatar with rounded studs, fun accessories, and cheerful obby playground",
            iconEmoji = "🧱",
            visualKeywords = listOf("smooth bevel block avatar", "classic cylinder head and limbs", "fun collectible wings and hats", "vibrant obstacle course neon paths", "clean glossy plastic material"),
            defaultNegative = "hyper-realistic anatomy, scary horror details, complex muscle structure"
        ),
        PromptStyle(
            id = "game_cod",
            title = "Call of Duty-Inspired Military Spec-Ops",
            category = CategoryGroup.GAMING,
            description = "Photorealistic modern warfare night-vision operation with infrared lasers and tactical breach",
            iconEmoji = "🎯",
            visualKeywords = listOf("quad-tube panoramic night vision goggles", "green phosphorescent night-vision hue", "infrared PEQ-15 laser beam", "tactical multicam gear", "wet tarmac reflections"),
            defaultNegative = "bright cartoon colors, whimsical elements, fake lighting"
        ),
        PromptStyle(
            id = "game_valorant",
            title = "Valorant-Inspired Cel-Shaded Agent",
            category = CategoryGroup.GAMING,
            description = "Sleek tactical shooter agent design with bold graphic silhouette, elemental magic, and crisp cel-shading",
            iconEmoji = "🗡️",
            visualKeywords = listOf("stylized tactical cel-shading", "dynamic wind/fire elemental effects", "sharp graphic character concept art", "minimalist futuristic techwear", "high-key directional rim light"),
            defaultNegative = "photorealistic noise, muddy gradients, bad line art"
        ),
        PromptStyle(
            id = "game_cyberpunk",
            title = "Cyberpunk 2077-Inspired Night City",
            category = CategoryGroup.GAMING,
            description = "Dense neon-drenched metropolis with cybernetic implants, holographic ads, and rainy asphalt",
            iconEmoji = "🦾",
            visualKeywords = listOf("subdermal chrome implants", "volumetric neon smog", "holographic corporate advertisements", "wet rain puddles with neon reflections", "high tech low life aesthetic"),
            defaultNegative = "natural serene countryside, clean pastoral scene, dull lighting"
        ),
        PromptStyle(
            id = "game_rpg_fantasy",
            title = "Epic Fantasy RPG Hero",
            category = CategoryGroup.GAMING,
            description = "Elden Ring & Witcher-inspired dark fantasy knight with ornate engraved plate armor and rune blade",
            iconEmoji = "⚔️",
            visualKeywords = listOf("ornate gothic plate armor", "weathered chainmail and leather", "glowing arcane runes on blade", "foggy ancient ruins", "dramatic atmospheric ember sparks"),
            defaultNegative = "modern clothing, plastic toys, sci-fi guns"
        ),
        PromptStyle(
            id = "game_racing",
            title = "Next-Gen Sim Racing",
            category = CategoryGroup.GAMING,
            description = "Forza & Gran Turismo hyper-fidelity track racing with carbon fiber details and heat haze",
            iconEmoji = "🏎️",
            visualKeywords = listOf("carbon fiber aero kit", "glowing brake caliper heat", "asphalt rubber track marks", "dynamic motion blurred backdrop", "crisp sun flare off windshield"),
            defaultNegative = "blurry car models, low polygon wheels, bad reflections"
        ),
        PromptStyle(
            id = "game_zombie",
            title = "Zombie Survival Apocalypse",
            category = CategoryGroup.GAMING,
            description = "Overgrown post-apocalyptic cityscape with reinforced makeshift armor and dramatic survival tension",
            iconEmoji = "🧟",
            visualKeywords = listOf("ivy covered skyscrapers", "makeshift scrap armor", "flashlight beam cutting through dense fog", "distressed survival backpack", "moody desaturated tones"),
            defaultNegative = "clean spotless clothes, sunny cheerful beach, bright colors"
        ),

        // === PHOTOGRAPHY STYLES ===
        PromptStyle(
            id = "photo_dslr",
            title = "DSLR Master Photography",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Shot on Sony Alpha A1 / Canon EOS R5 with 85mm f/1.2 lens, pristine bokeh and lifelike skin pores",
            iconEmoji = "📷",
            visualKeywords = listOf("85mm f/1.2 prime lens", "creamy shallow depth of field", "ultra-sharp subject focus", "lifelike skin texture with micro pores", "natural color grading"),
            defaultNegative = "airbrushed plastic skin, oversharpened halo, 3D render look, CGI gloss",
            isPopular = true
        ),
        PromptStyle(
            id = "photo_smartphone",
            title = "Smartphone Flagship Portrait",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Crisp computational portrait mode with vibrant HDR dynamic range and natural edge separation",
            iconEmoji = "📱",
            visualKeywords = listOf("computational HDR dynamic range", "sharp edge cutout bokeh", "vibrant lifelike skin tones", "clean modern ambient light", "crisp detail balance"),
            defaultNegative = "blurry edges, lens distortion, noise artifacts"
        ),
        PromptStyle(
            id = "photo_cinematic",
            title = "Cinematic Movie Still",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Shot on Arri Alexa with anamorphic lenses, subtle teal & orange grade, 2.39:1 aspect ratio framing",
            iconEmoji = "🎬",
            visualKeywords = listOf("Arri Alexa LF camera", "Cooke Anamorphic prime lens", "subtle horizontal blue lens flare", "cinematic teal and amber color grade", "shallow focal plane"),
            defaultNegative = "home video look, flat amateur lighting, cheap digital sharpness",
            isPopular = true
        ),
        PromptStyle(
            id = "photo_film_35mm",
            title = "Vintage 35mm Film Grain",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Kodak Portra 400 analog aesthetic with authentic organic grain, warm nostalgic tones, and gentle halation",
            iconEmoji = "🎞️",
            visualKeywords = listOf("Kodak Portra 400 film stock", "organic silver halide film grain", "warm nostalgic skin tones", "gentle red highlight halation", "authentic analog texture"),
            defaultNegative = "digital noise, sterile sharpness, hyper-clean 3D render"
        ),
        PromptStyle(
            id = "photo_golden_hour",
            title = "Golden Hour Magic",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Warm low-angle setting sun casting long soft shadows and radiant golden rim highlights",
            iconEmoji = "🌅",
            visualKeywords = listOf("low-angle golden hour sunlight", "warm honey backlighting", "radiant hair rim light", "soft ethereal lens flare", "dreamy atmospheric haze"),
            defaultNegative = "cold blue shadows, harsh midday glare, flat lighting"
        ),
        PromptStyle(
            id = "photo_night_urban",
            title = "Night Urban Street Photography",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "High-contrast city street at midnight illuminated by neon signs, car taillights, and wet pavement",
            iconEmoji = "🌃",
            visualKeywords = listOf("wet asphalt neon light spill", "bokeh of distant streetlamps and headlights", "rich deep blacks with vibrant color pops", "cinematic street mood", "subtle atmospheric fog"),
            defaultNegative = "underexposed black crush, noisy sensor grain, blurry motion"
        ),
        PromptStyle(
            id = "photo_rainy_cinematic",
            title = "Rainy Cinematic Mood",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Atmospheric downpour with glistening raindrops on glass, wet hair, and reflective city ground",
            iconEmoji = "🌧️",
            visualKeywords = listOf("glistening raindrops suspended in air", "wet skin and hair texture", "puddle reflections of streetlights", "heavy moody clouds", "water droplets on camera lens"),
            defaultNegative = "dry flat environment, sunny sky, oversaturated cartoony colors"
        ),
        PromptStyle(
            id = "photo_moody",
            title = "Moody & Atmospheric Art",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Intimate low-key lighting with deep expressive shadows, emotional intensity, and soft mist",
            iconEmoji = "🌫️",
            visualKeywords = listOf("low-key chiaroscuro lighting", "dramatic side shadows", "emotional introspective expression", "subtle volumetric smoke", "matte dark tones"),
            defaultNegative = "overly bright, flat high-key studio light, goofy smiles"
        ),
        PromptStyle(
            id = "photo_luxury",
            title = "High-End Luxury Editorial",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Vogue and GQ magazine cover aesthetic featuring haute couture, architectural minimalism, and flawless polish",
            iconEmoji = "✨",
            visualKeywords = listOf("haute couture designer styling", "architectural brutalist backdrop", "flawless beauty editorial lighting", "striking high fashion pose", "prestigious magazine aesthetic"),
            defaultNegative = "casual messy background, bad posture, cheap clothing"
        ),
        PromptStyle(
            id = "photo_urban",
            title = "Urban Street Culture",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Candid metropolitan energy with graffiti murals, modern streetwear, subway stations, and rooftop views",
            iconEmoji = "🏙️",
            visualKeywords = listOf("modern techwear styling", "graffiti concrete textured wall", "dramatic metropolitan perspective", "natural candid energy", "sharp urban geometry"),
            defaultNegative = "countryside farm, traditional medieval themes"
        ),
        PromptStyle(
            id = "photo_nature",
            title = "Wilderness & Nature Odyssey",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "National Geographic style untouched wilderness, emerald pine forests, alpine lakes, and dramatic skies",
            iconEmoji = "🌲",
            visualKeywords = listOf("National Geographic photography style", "pristine alpine lake reflection", "dense mossy pine forest", "dramatic mountain ridge backdrop", "crisp outdoor natural lighting"),
            defaultNegative = "man-made buildings, plastic trash, neon lights"
        ),
        PromptStyle(
            id = "photo_automotive",
            title = "Automotive Commercial Studio",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Ultra-polished car magazine shoot with giant overhead softbox light reflections and metallic sparkle",
            iconEmoji = "🏎️",
            visualKeywords = listOf("infinite curved studio backdrop", "sculpted continuous strip light reflections", "flawless metallic flake paint", "crisp carbon fiber weave", "ultra wide low angle perspective"),
            defaultNegative = "scratched paint, dusty windshield, distorted geometry"
        ),
        PromptStyle(
            id = "photo_portrait_pro",
            title = "Professional Studio Headshot",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Clean three-point Profoto studio lighting, soft neutral gradient backdrop, and authentic executive warmth",
            iconEmoji = "📸",
            visualKeywords = listOf("three-point beauty dish lighting", "soft gradient studio backdrop", "warm confident eye contact", "crisp hair edge definition", "true-to-life skin tones"),
            defaultNegative = "harsh shadows under eyes, blown out highlights, cartoon render"
        ),
        PromptStyle(
            id = "photo_portrait_dramatic",
            title = "Dramatic Fine-Art Portrait",
            category = CategoryGroup.PHOTOGRAPHY,
            description = "Rembrandt lighting triangle on cheek, dark textured canvas background, and powerful emotional presence",
            iconEmoji = "🎭",
            visualKeywords = listOf("Rembrandt light triangle", "dark hand-painted canvas texture", "piercing expressive eyes", "rich shadow falloff", "museum quality fine-art composition"),
            defaultNegative = "flat cheerful lighting, washed out colors, low contrast"
        ),

        // === AI ART STYLES ===
        PromptStyle(
            id = "art_3d_render",
            title = "3D Octane / Unreal Render",
            category = CategoryGroup.AI_ART,
            description = "Unreal Engine 5 Lumen raytracing with subsurface scattering, hyper-detailed textures, and studio lighting",
            iconEmoji = "🧊",
            visualKeywords = listOf("Octane render 8K", "Unreal Engine 5 Lumen lighting", "subsurface scattering on skin", "physically based rendering textures", "subtle volumetric dispersion"),
            defaultNegative = "flat 2D vector, blurry low-res render, bad polygons",
            isPopular = true
        ),
        PromptStyle(
            id = "art_pixar_3d",
            title = "3D Family Animation Aesthetic",
            category = CategoryGroup.AI_ART,
            description = "Whimsical animated movie hero with expressive oversized eyes, soft velvet lighting, and charming character",
            iconEmoji = "🧸",
            visualKeywords = listOf("stylized 3D character animation", "large expressive emotive eyes", "soft clay-like subsurface scattering", "fluffy hair dynamics", "warm whimsical color palette"),
            defaultNegative = "uncanny valley horror, realistic gory details, gritty rough textures",
            isPopular = true
        ),
        PromptStyle(
            id = "art_anime",
            title = "Masterwork Anime Aesthetic",
            category = CategoryGroup.AI_ART,
            description = "Makoto Shinkai & Kyoto Animation inspired with radiant sky, shimmering light particles, and delicate line work",
            iconEmoji = "🌸",
            visualKeywords = listOf("high quality anime key visual", "delicate clean line art", "Makoto Shinkai vibrant cloudscape", "shimmering lens bokeh particles", "emotive anime eyes with intricate reflections"),
            defaultNegative = "western comic art, photoreal human skin, muddy colors, bad line weight",
            isPopular = true
        ),
        PromptStyle(
            id = "art_comic_book",
            title = "Vintage Comic Book & Pop Art",
            category = CategoryGroup.AI_ART,
            description = "Bold ink hatching, Ben-Day halftone dots, dynamic action speed lines, and vibrant primary colors",
            iconEmoji = "💥",
            visualKeywords = listOf("heavy black ink line art", "Ben-Day halftone print pattern", "dynamic graphic novel panel composition", "punchy primary color contrast", "stylized crosshatching"),
            defaultNegative = "smooth 3D gradient, photorealistic skin, blurry edges"
        ),
        PromptStyle(
            id = "art_digital_concept",
            title = "Digital Concept Art & Splash Art",
            category = CategoryGroup.AI_ART,
            description = "Artstation trending digital masterpiece with expressive brush strokes and epic cinematic scale",
            iconEmoji = "🎨",
            visualKeywords = listOf("trending on Artstation", "dynamic digital painting brushwork", "epic atmospheric scale", "rich color harmony", "dramatic focal lighting"),
            defaultNegative = "unfinished sketch, amateur doodles, flat coloring"
        ),
        PromptStyle(
            id = "art_oil_painting",
            title = "Impressionist Oil on Canvas",
            category = CategoryGroup.AI_ART,
            description = "Thick impasto palette knife strokes, rich linseed oil glaze, and textured linen canvas weave",
            iconEmoji = "🖌️",
            visualKeywords = listOf("heavy impasto oil paint texture", "visible palette knife strokes", "rich luminous glazing", "textured linen canvas backdrop", "classical master color theory"),
            defaultNegative = "smooth digital vector, plastic render, sharp vector lines"
        ),
        PromptStyle(
            id = "art_watercolor",
            title = "Ethereal Watercolor & Wet-on-Wet",
            category = CategoryGroup.AI_ART,
            description = "Delicate color bleeds, organic pigment granulations, fluid water pooling, and textured rough paper",
            iconEmoji = "💧",
            visualKeywords = listOf("spontaneous watercolor wash", "pigment blooms and granulations", "fluid wet-on-wet color blending", "rough cold-press watercolor paper", "graceful translucent layers"),
            defaultNegative = "hard solid digital lines, heavy black outlines, thick opaque paint"
        ),
        PromptStyle(
            id = "art_pencil_sketch",
            title = "Fine Graphite Pencil Drawing",
            category = CategoryGroup.AI_ART,
            description = "Masterful crosshatch shading, sharp 2B graphite details, soft blending stump tones, and vintage parchment",
            iconEmoji = "✏️",
            visualKeywords = listOf("detailed graphite pencil drawing", "fine crosshatch shading", "precise anatomical contours", "smudged charcoal shadows", "creamy vintage sketchbook paper"),
            defaultNegative = "color saturation, neon colors, digital painting"
        ),
        PromptStyle(
            id = "art_clay_art",
            title = "Handcrafted Claymation & Plasticine",
            category = CategoryGroup.AI_ART,
            description = "Aardman & Laika stop-motion aesthetic with tactile thumbprint imperfections and studio lighting",
            iconEmoji = "🏺",
            visualKeywords = listOf("tactile handmade plasticine clay", "subtle miniature thumbprint textures", "stop-motion miniature set lighting", "matte clay finish", "charming miniature scale"),
            defaultNegative = "smooth digital CGI, photoreal human skin, razor sharp metal"
        ),
        PromptStyle(
            id = "art_low_poly",
            title = "Low Poly Geometric Art",
            category = CategoryGroup.AI_ART,
            description = "Clean faceted polygon facets with vibrant flat gradients and isometric diorama presentation",
            iconEmoji = "📐",
            visualKeywords = listOf("faceted low-poly 3D geometry", "crisp triangle meshes", "vibrant gradient lighting across poly faces", "isometric floating island diorama", "minimalist geometric charm"),
            defaultNegative = "smooth curved surfaces, high-density polygons, detailed textures"
        ),
        PromptStyle(
            id = "art_dark_fantasy",
            title = "Dark Gothic Fantasy",
            category = CategoryGroup.AI_ART,
            description = "Eerie towering cathedrals, obsidian spikes, eldritch glowing runes, and brooding dark mythology",
            iconEmoji = "🦇",
            visualKeywords = listOf("gothic cathedral spires", "eldritch glowing runes", "brooding atmospheric fog", "dark crimson and obsidian palette", "haunting ethereal presence"),
            defaultNegative = "cheerful daylight, cute cartoon colors, modern tech"
        ),

        // === SOCIAL MEDIA CREATOR STYLES ===
        PromptStyle(
            id = "social_yt_thumbnail",
            title = "Viral YouTube Thumbnail",
            category = CategoryGroup.SOCIAL_MEDIA,
            description = "Ultra high-contrast face expression with glowing neon outline, explosive background, and maximum click-through appeal",
            iconEmoji = "🔴",
            visualKeywords = listOf("expressive hyper-enthusiastic facial expression", "bold neon subject rim outline", "explosive high-contrast backdrop", "saturated vibrant colors", "clean uncluttered composition for small screens"),
            defaultNegative = "dull low-contrast colors, sleepy expression, tiny distant subject",
            isPopular = true
        ),
        PromptStyle(
            id = "social_gaming_pfp",
            title = "Pro Gaming Avatar / PFP",
            category = CategoryGroup.SOCIAL_MEDIA,
            description = "Centered stylized avatar framed by circular glowing neon energy ring with dark cyber gradient",
            iconEmoji = "👤",
            visualKeywords = listOf("centered circular avatar framing", "glowing neon halo ring", "high-detail cybernetic mask or headset", "dark sleek gradient backdrop", "iconic memorable silhouette"),
            defaultNegative = "off-center composition, busy cluttered background, unreadable at small size"
        ),
        PromptStyle(
            id = "social_tiktok_pfp",
            title = "TikTok & Reels Creator PFP",
            category = CategoryGroup.SOCIAL_MEDIA,
            description = "Hyper-aesthetic Gen-Z aesthetic with dreamy pastel chromatic aberration, soft ring light, and trendy streetwear",
            iconEmoji = "📱",
            visualKeywords = listOf("trendy streetwear aesthetic", "soft beauty ring light in eyes", "subtle chromatic aberration glow", "aesthetic pastel dreamscape background", "clean vibrant punch"),
            defaultNegative = "gloomy drab lighting, messy uncurated room, pixelated compression"
        ),
        PromptStyle(
            id = "social_esports_poster",
            title = "Esports Championship Poster",
            category = CategoryGroup.SOCIAL_MEDIA,
            description = "Dynamic diagonal tournament banner with player silhouette, laser beams, glass shards, and metallic typography spacing",
            iconEmoji = "🏆",
            visualKeywords = listOf("diagonal dynamic action composition", "shattered holographic glass shards", "intense stage spotlight beams", "metallic carbon fiber textures", "championship victory atmosphere"),
            defaultNegative = "static calm scene, lack of energy, amateur layout"
        ),
        PromptStyle(
            id = "social_streamer_banner",
            title = "Twitch / Streamer Hero Banner",
            category = CategoryGroup.SOCIAL_MEDIA,
            description = "Ultra-wide 16:9 banner with neon gaming room setup, dual curved monitors, and purple LED strip illumination",
            iconEmoji = "🟣",
            visualKeywords = listOf("neon purple and cyan stream room lighting", "acoustic wall hex panels", "glowing dual curved monitors", "sleek streamer microphone with pop filter", "spacious composition for text overlay"),
            defaultNegative = "cramped layout, bad aspect ratio, harsh white flash"
        ),

        // === PHOTO TRANSFORMER STYLES ===
        PromptStyle(
            id = "trans_minecraft",
            title = "Real Photo → Minecraft World",
            category = CategoryGroup.PHOTO_TRANSFORMER,
            description = "Convert real-life subject and scene into authentic textured 3D voxel cubes preserving exact pose and clothes",
            iconEmoji = "🟩",
            visualKeywords = listOf("exact pose converted to voxel geometry", "clothes translated to pixel-art textures", "face stylized as expressive voxel avatar", "surrounding environment transformed into Minecraft biome blocks", "RTX shader raytraced lighting"),
            defaultNegative = "photorealistic human skin, smooth curved non-block geometry"
        ),
        PromptStyle(
            id = "trans_gaming_char",
            title = "Real Photo → Gaming Character",
            category = CategoryGroup.PHOTO_TRANSFORMER,
            description = "Reimagine uploaded photo as an AAA video game hero with tactical sci-fi gear while keeping true face and expression",
            iconEmoji = "🎮",
            visualKeywords = listOf("faithful face identity preservation", "upgraded tactical sci-fi combat armor", "cybernetic glowing accents", "AAA game engine high-poly model", "dramatic cinematic hero lighting"),
            defaultNegative = "changing face identity, generic face replacement, low polygon count"
        ),
        PromptStyle(
            id = "trans_3d_char",
            title = "Real Photo → 3D Character",
            category = CategoryGroup.PHOTO_TRANSFORMER,
            description = "Transform uploaded subject into a charming 3D animated film character with velvety lighting and stylized features",
            iconEmoji = "🧸",
            visualKeywords = listOf("recognizable facial features in 3D animated style", "smooth velvety skin with subsurface scattering", "stylized hair strands", "expressive warm eyes", "cinematic studio lighting"),
            defaultNegative = "unrecognizable face, photoreal horror, flat 2D"
        ),
        PromptStyle(
            id = "trans_anime_char",
            title = "Real Photo → Anime Character",
            category = CategoryGroup.PHOTO_TRANSFORMER,
            description = "Convert photo into a high-tier Japanese anime protagonist preserving hair color, clothes, and gesture",
            iconEmoji = "🌸",
            visualKeywords = listOf("exact hairstyle and clothing translated to crisp anime lines", "vibrant anime eyes with subject's eye color", "delicate clean cel-shading", "dynamic anime lighting sparks", "painterly atmospheric backdrop"),
            defaultNegative = "western cartoon style, distorted proportions, messy line art"
        ),
        PromptStyle(
            id = "trans_superhero",
            title = "Real Photo → Superhero Icon",
            category = CategoryGroup.PHOTO_TRANSFORMER,
            description = "Transform person into a powerful comic hero with custom textured vibranium suit, billowing cape, and energy aura",
            iconEmoji = "⚡",
            visualKeywords = listOf("heroic athletic posture", "custom embossed superhero armor with intricate emblem", "crackling cosmic energy around fists", "cinematic stormy sky with lightning highlights", "faithful facial recognition"),
            defaultNegative = "weak posture, generic stock face, low resolution suit details"
        ),
        PromptStyle(
            id = "trans_movie_poster",
            title = "Real Photo → Blockbuster Movie Poster",
            category = CategoryGroup.PHOTO_TRANSFORMER,
            description = "Turn photo into the lead star of a Hollywood cinematic blockbuster poster with intense lighting and epic background",
            iconEmoji = "🎬",
            visualKeywords = listOf("Hollywood movie star key art", "dramatic teal and orange split lighting", "rain soaked atmosphere with sparks", "epic high-stakes city disaster in background", "hyper-detailed skin and costume texture"),
            defaultNegative = "casual snapshot look, flat phone photo lighting"
        )
    )

    fun getStylesForCategory(category: CategoryGroup): List<PromptStyle> {
        return if (category == CategoryGroup.TRENDING) {
            allStyles.filter { it.isPopular }
        } else {
            allStyles.filter { it.category == category }
        }
    }

    fun getStyleById(id: String): PromptStyle {
        return allStyles.firstOrNull { it.id == id } ?: allStyles.first()
    }
}
