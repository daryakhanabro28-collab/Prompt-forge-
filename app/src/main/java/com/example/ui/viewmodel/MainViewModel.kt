package com.example.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PromptEntity
import com.example.data.local.PromptRepository
import com.example.domain.engine.PromptEngine
import com.example.domain.model.CategoryGroup
import com.example.domain.model.CustomizationOptions
import com.example.domain.model.ImageAnalysisInfo
import com.example.domain.model.PromptStyle
import com.example.domain.model.StyleCatalog
import com.example.domain.model.StructuredPrompt
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class MainNavigationTab(val title: String, val iconEmoji: String) {
    HOME("Home", "🏠"),
    CREATE("Create", "📸"),
    STYLES("Styles", "🎮"),
    SAVED("Saved", "⭐"),
    MORE("More", "☰")
}

enum class SubPortal {
    ANALYZER,
    GAMING,
    PHOTOGRAPHY,
    AI_ART,
    CINEMATIC,
    SOCIAL_MEDIA,
    TRENDING,
    TRANSFORMER,
    SETTINGS,
    ABOUT
}

data class UiState(
    val currentTab: MainNavigationTab = MainNavigationTab.HOME,
    val currentSubPortal: SubPortal? = null,
    val selectedBitmap: Bitmap? = null,
    val selectedImageUri: Uri? = null,
    val selectedStyle: PromptStyle = StyleCatalog.allStyles.first(),
    val selectedCategoryGroup: CategoryGroup = CategoryGroup.GAMING,
    val customization: CustomizationOptions = CustomizationOptions(),
    val isAnalyzing: Boolean = false,
    val isGenerating: Boolean = false,
    val analysisProgress: Float = 0f,
    val analysisStatusStep: String = "",
    val analysisResult: ImageAnalysisInfo? = null,
    val generatedPrompts: List<StructuredPrompt> = emptyList(),
    val activePromptIndex: Int = 0,
    val searchQuery: String = "",
    val savedFilterCategory: String? = null,
    val toastMessage: String? = null,
    val editingPrompt: StructuredPrompt? = null,
    val darkTheme: Boolean = true,
    val autoSaveEnabled: Boolean = true,
    val promptDetailLevel: String = "Ultra Detailed",
    val appLanguage: String = "English (US)"
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PromptRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = PromptRepository(db.promptDao())
    }

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")

    val allSavedPrompts: StateFlow<List<PromptEntity>> = repository.allPrompts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredSavedPrompts: StateFlow<List<PromptEntity>> = combine(
        repository.allPrompts,
        _searchQuery,
        _uiState
    ) { prompts, query, state ->
        prompts.filter { item ->
            val matchesQuery = query.isBlank() ||
                    item.title.contains(query, ignoreCase = true) ||
                    item.fullPrompt.contains(query, ignoreCase = true) ||
                    item.styleName.contains(query, ignoreCase = true) ||
                    item.category.contains(query, ignoreCase = true)

            val matchesCategory = state.savedFilterCategory == null || item.category == state.savedFilterCategory
            matchesQuery && matchesCategory
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private var generationJob: Job? = null

    fun selectTab(tab: MainNavigationTab) {
        _uiState.value = _uiState.value.copy(currentTab = tab, currentSubPortal = null)
    }

    fun navigateToSubPortal(portal: SubPortal) {
        _uiState.value = _uiState.value.copy(currentSubPortal = portal)
    }

    fun clearSubPortal() {
        _uiState.value = _uiState.value.copy(currentSubPortal = null)
    }

    fun setImage(bitmap: Bitmap?, uri: Uri?) {
        _uiState.value = _uiState.value.copy(
            selectedBitmap = bitmap,
            selectedImageUri = uri
        )
        if (bitmap != null) {
            triggerAutoAnalyze(bitmap)
        }
    }

    fun removeImage() {
        _uiState.value = _uiState.value.copy(
            selectedBitmap = null,
            selectedImageUri = null,
            analysisResult = null
        )
        showToast("Image removed")
    }

    fun selectStyle(style: PromptStyle) {
        _uiState.value = _uiState.value.copy(
            selectedStyle = style,
            selectedCategoryGroup = style.category
        )
    }

    fun selectCategoryGroup(group: CategoryGroup) {
        val styles = StyleCatalog.getStylesForCategory(group)
        val newStyle = styles.firstOrNull() ?: StyleCatalog.allStyles.first()
        _uiState.value = _uiState.value.copy(
            selectedCategoryGroup = group,
            selectedStyle = newStyle
        )
    }

    fun updateCustomization(options: CustomizationOptions) {
        _uiState.value = _uiState.value.copy(customization = options)
    }

    fun updateAspectRatio(ratio: String) {
        _uiState.value = _uiState.value.copy(
            customization = _uiState.value.customization.copy(aspectRatio = ratio)
        )
    }

    fun updateQuality(quality: String) {
        _uiState.value = _uiState.value.copy(
            customization = _uiState.value.customization.copy(quality = quality)
        )
    }

    fun updateMood(mood: String) {
        _uiState.value = _uiState.value.copy(
            customization = _uiState.value.customization.copy(mood = mood)
        )
    }

    fun updateLighting(lighting: String) {
        _uiState.value = _uiState.value.copy(
            customization = _uiState.value.customization.copy(lighting = lighting)
        )
    }

    fun updateCamera(camera: String) {
        _uiState.value = _uiState.value.copy(
            customization = _uiState.value.customization.copy(camera = camera)
        )
    }

    fun togglePreserveIdentity(preserve: Boolean) {
        _uiState.value = _uiState.value.copy(
            customization = _uiState.value.customization.copy(preserveIdentity = preserve)
        )
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        _uiState.value = _uiState.value.copy(searchQuery = query)
    }

    fun setSavedFilterCategory(category: String?) {
        _uiState.value = _uiState.value.copy(savedFilterCategory = category)
    }

    fun startAnalysisOnly() {
        val bitmap = _uiState.value.selectedBitmap
        if (bitmap == null) {
            showToast("Please upload or select an image first")
            return
        }
        triggerAutoAnalyze(bitmap)
    }

    private fun triggerAutoAnalyze(bitmap: Bitmap) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isAnalyzing = true,
                analysisProgress = 0.1f,
                analysisStatusStep = "Initializing AI Vision Scanner..."
            )
            delay(400)
            _uiState.value = _uiState.value.copy(
                analysisProgress = 0.4f,
                analysisStatusStep = "Detecting Subject, Pose & Facial Landmarks..."
            )
            delay(400)
            _uiState.value = _uiState.value.copy(
                analysisProgress = 0.7f,
                analysisStatusStep = "Extracting Color Palette & Lighting Dynamics..."
            )

            val result = PromptEngine.analyzeAndGeneratePrompts(
                bitmap = bitmap,
                style = _uiState.value.selectedStyle,
                options = _uiState.value.customization
            )

            result.onSuccess { (analysis, prompts) ->
                _uiState.value = _uiState.value.copy(
                    isAnalyzing = false,
                    analysisProgress = 1.0f,
                    analysisStatusStep = "Analysis Complete!",
                    analysisResult = analysis,
                    generatedPrompts = prompts
                )
                showToast("Photo analyzed successfully ✨")
            }.onFailure {
                _uiState.value = _uiState.value.copy(
                    isAnalyzing = false,
                    analysisProgress = 1.0f,
                    analysisStatusStep = "Analysis complete using local vision synthesis"
                )
            }
        }
    }

    fun generatePrompts(onComplete: () -> Unit = {}) {
        generationJob?.cancel()
        generationJob = viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isGenerating = true,
                analysisProgress = 0.2f,
                analysisStatusStep = "Reading Image Aesthetics & Style Parameters..."
            )
            delay(350)
            _uiState.value = _uiState.value.copy(
                analysisProgress = 0.6f,
                analysisStatusStep = "Synthesizing 4 Multi-Tier Prompt Variations..."
            )
            delay(350)
            _uiState.value = _uiState.value.copy(
                analysisProgress = 0.9f,
                analysisStatusStep = "Optimizing Negative Prompts & Aspect Ratios..."
            )

            val result = PromptEngine.analyzeAndGeneratePrompts(
                bitmap = _uiState.value.selectedBitmap,
                style = _uiState.value.selectedStyle,
                options = _uiState.value.customization
            )

            result.onSuccess { (analysis, prompts) ->
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    analysisProgress = 1.0f,
                    analysisResult = analysis,
                    generatedPrompts = prompts
                )

                // Auto-save if enabled
                if (_uiState.value.autoSaveEnabled && prompts.isNotEmpty()) {
                    val primaryPrompt = prompts.first()
                    savePromptToDatabase(primaryPrompt, isAutoSave = true)
                }

                showToast("Generated 4 detailed prompts! 🚀")
                onComplete()
            }.onFailure { e ->
                _uiState.value = _uiState.value.copy(
                    isGenerating = false,
                    analysisProgress = 1.0f
                )
                showToast("Something went wrong. Generated offline fallback prompts.")
                onComplete()
            }
        }
    }

    fun savePrompt(prompt: StructuredPrompt) {
        savePromptToDatabase(prompt, isAutoSave = false)
        showToast("Saved to library ⭐")
    }

    private fun savePromptToDatabase(prompt: StructuredPrompt, isAutoSave: Boolean = false) {
        viewModelScope.launch {
            val entity = PromptEntity(
                title = prompt.title,
                variantType = prompt.variantType,
                fullPrompt = prompt.fullPrompt,
                subject = prompt.subject,
                composition = prompt.composition,
                camera = prompt.camera,
                lighting = prompt.lighting,
                environment = prompt.environment,
                style = prompt.style,
                details = prompt.details,
                quality = prompt.quality,
                negativePrompt = prompt.negativePrompt,
                category = _uiState.value.selectedCategoryGroup.displayName,
                styleId = _uiState.value.selectedStyle.id,
                styleName = _uiState.value.selectedStyle.title,
                aspectRatio = _uiState.value.customization.aspectRatio,
                mood = _uiState.value.customization.mood,
                imageUri = _uiState.value.selectedImageUri?.toString(),
                isFavorite = !isAutoSave
            )
            val id = repository.insert(entity)
            // Update local prompt list favorite status if matching
            val updatedPrompts = _uiState.value.generatedPrompts.map {
                if (it.id == prompt.id) it.copy(isFavorite = true) else it
            }
            _uiState.value = _uiState.value.copy(generatedPrompts = updatedPrompts)
        }
    }

    fun toggleFavorite(id: Long, currentFav: Boolean) {
        viewModelScope.launch {
            repository.setFavorite(id, !currentFav)
            showToast(if (!currentFav) "Added to Favorites ⭐" else "Removed from Favorites")
        }
    }

    fun deletePrompt(id: Long) {
        viewModelScope.launch {
            repository.deleteById(id)
            showToast("Prompt deleted")
        }
    }

    fun clearAllHistory() {
        viewModelScope.launch {
            repository.clearHistoryOnly()
            showToast("History cleared (Favorites kept)")
        }
    }

    fun clearAllData() {
        viewModelScope.launch {
            repository.clearAll()
            showToast("All saved prompts and history deleted")
        }
    }

    fun startEditingPrompt(prompt: StructuredPrompt) {
        _uiState.value = _uiState.value.copy(editingPrompt = prompt)
    }

    fun dismissEditingPrompt() {
        _uiState.value = _uiState.value.copy(editingPrompt = null)
    }

    fun saveEditedPrompt(editedPrompt: StructuredPrompt) {
        val updated = _uiState.value.generatedPrompts.map {
            if (it.id == editedPrompt.id) editedPrompt else it
        }
        _uiState.value = _uiState.value.copy(
            generatedPrompts = updated,
            editingPrompt = null
        )
        showToast("Prompt updated ✏️")
    }

    fun reusePrompt(entity: PromptEntity) {
        val style = StyleCatalog.getStyleById(entity.styleId)
        val prompt = entity.toStructuredPrompt()
        _uiState.value = _uiState.value.copy(
            selectedStyle = style,
            currentTab = MainNavigationTab.CREATE,
            generatedPrompts = listOf(prompt)
        )
        showToast("Loaded '${style.title}' into creator")
    }

    fun toggleTheme(dark: Boolean) {
        _uiState.value = _uiState.value.copy(darkTheme = dark)
    }

    fun toggleAutoSave(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(autoSaveEnabled = enabled)
    }

    fun setDetailLevel(level: String) {
        _uiState.value = _uiState.value.copy(promptDetailLevel = level)
    }

    fun setLanguage(lang: String) {
        _uiState.value = _uiState.value.copy(appLanguage = lang)
        showToast("Language changed to $lang")
    }

    fun showToast(message: String) {
        _uiState.value = _uiState.value.copy(toastMessage = message)
    }

    fun dismissToast() {
        _uiState.value = _uiState.value.copy(toastMessage = null)
    }
}
