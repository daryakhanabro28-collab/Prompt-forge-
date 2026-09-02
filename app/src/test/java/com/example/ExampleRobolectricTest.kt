package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.AppDatabase
import com.example.data.local.PromptEntity
import com.example.domain.engine.PromptEngine
import com.example.domain.model.CategoryGroup
import com.example.domain.model.CustomizationOptions
import com.example.domain.model.StyleCatalog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

  @Test
  fun readStringFromContext() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Prompt Forge AI", appName)
  }

  @Test
  fun styleCatalogContainsAllRequestedCategories() {
    val styles = StyleCatalog.allStyles
    assertTrue("Styles catalog should contain at least 20 presets", styles.size >= 20)

    val gamingStyles = StyleCatalog.getStylesForCategory(CategoryGroup.GAMING)
    assertTrue("Gaming styles should include Minecraft, PUBG, Free Fire variants", gamingStyles.isNotEmpty())
    assertTrue(gamingStyles.any { it.id.startsWith("mc_") })
    assertTrue(gamingStyles.any { it.id.startsWith("pubg_") })
    assertTrue(gamingStyles.any { it.id.startsWith("ff_") })

    val photoStyles = StyleCatalog.getStylesForCategory(CategoryGroup.PHOTOGRAPHY)
    assertTrue("Photography styles should include DSLR, 35mm film, golden hour", photoStyles.isNotEmpty())
  }

  @Test
  fun promptEngineGeneratesStructuredPrompts() = runBlocking {
    val style = StyleCatalog.getStyleById("mc_blocky_3d")
    val options = CustomizationOptions(aspectRatio = "16:9", quality = "Ultra Detailed", lighting = "Volumetric")

    val result = PromptEngine.analyzeAndGeneratePrompts(bitmap = null, style = style, options = options)
    assertTrue(result.isSuccess)

    val (analysis, prompts) = result.getOrThrow()
    assertNotNull(analysis)
    assertEquals(4, prompts.size)

    val prompt1 = prompts.first()
    assertNotNull(prompt1.fullPrompt)
    assertTrue(prompt1.fullPrompt.contains("--ar 16:9"))
    assertTrue(prompt1.negativePrompt.isNotBlank())
  }

  @Test
  fun databaseOperationsWork() = runBlocking {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val db = AppDatabase.getDatabase(context)
    val dao = db.promptDao()

    val testPrompt = PromptEntity(
      title = "Test Minecraft Prompt",
      variantType = "Cinematic",
      fullPrompt = "Detailed voxel castle in mountain landscape, RTX shaders --ar 16:9",
      subject = "Voxel Castle",
      composition = "Rule of thirds",
      camera = "Wide shot",
      lighting = "Golden Hour",
      environment = "Mountain Pine Biome",
      style = "Minecraft Blocky 3D World",
      details = "Textured cobblestone, lantern illumination",
      quality = "Masterpiece 8K",
      negativePrompt = "blurry, round curves",
      category = "Gaming Styles",
      styleId = "mc_blocky_3d",
      styleName = "Minecraft Blocky 3D World",
      aspectRatio = "16:9",
      mood = "Epic",
      isFavorite = true
    )

    val id = dao.insertPrompt(testPrompt)
    assertTrue(id > 0)

    val allPrompts = dao.getAllPrompts().first()
    assertTrue(allPrompts.any { it.id == id })

    dao.updateFavoriteStatus(id, false)
    val updatedList = dao.getAllPrompts().first()
    val updated = updatedList.first { it.id == id }
    assertFalse(updated.isFavorite)

    dao.deletePromptById(id)
  }
}
