package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PromptDao {
    @Query("SELECT * FROM saved_prompts ORDER BY timestamp DESC")
    fun getAllPrompts(): Flow<List<PromptEntity>>

    @Query("SELECT * FROM saved_prompts WHERE isFavorite = 1 ORDER BY timestamp DESC")
    fun getFavoritePrompts(): Flow<List<PromptEntity>>

    @Query("SELECT * FROM saved_prompts WHERE category = :category ORDER BY timestamp DESC")
    fun getPromptsByCategory(category: String): Flow<List<PromptEntity>>

    @Query("SELECT * FROM saved_prompts WHERE title LIKE '%' || :query || '%' OR fullPrompt LIKE '%' || :query || '%' OR styleName LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchPrompts(query: String): Flow<List<PromptEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPrompt(prompt: PromptEntity): Long

    @Update
    suspend fun updatePrompt(prompt: PromptEntity)

    @Query("UPDATE saved_prompts SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    @Query("DELETE FROM saved_prompts WHERE id = :id")
    suspend fun deletePromptById(id: Long)

    @Query("DELETE FROM saved_prompts")
    suspend fun deleteAllPrompts()

    @Query("DELETE FROM saved_prompts WHERE isFavorite = 0")
    suspend fun deleteHistoryOnly()
}
