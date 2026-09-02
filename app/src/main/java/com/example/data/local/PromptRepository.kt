package com.example.data.local

import kotlinx.coroutines.flow.Flow

class PromptRepository(private val promptDao: PromptDao) {
    val allPrompts: Flow<List<PromptEntity>> = promptDao.getAllPrompts()
    val favoritePrompts: Flow<List<PromptEntity>> = promptDao.getFavoritePrompts()

    fun searchPrompts(query: String): Flow<List<PromptEntity>> = promptDao.searchPrompts(query)

    fun getPromptsByCategory(category: String): Flow<List<PromptEntity>> = promptDao.getPromptsByCategory(category)

    suspend fun insert(prompt: PromptEntity): Long = promptDao.insertPrompt(prompt)

    suspend fun update(prompt: PromptEntity) = promptDao.updatePrompt(prompt)

    suspend fun setFavorite(id: Long, isFavorite: Boolean) = promptDao.updateFavoriteStatus(id, isFavorite)

    suspend fun deleteById(id: Long) = promptDao.deletePromptById(id)

    suspend fun clearAll() = promptDao.deleteAllPrompts()

    suspend fun clearHistoryOnly() = promptDao.deleteHistoryOnly()
}
