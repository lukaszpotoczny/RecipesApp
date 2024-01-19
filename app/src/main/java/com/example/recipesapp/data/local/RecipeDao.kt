package com.example.recipesapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.recipesapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("SELECT * FROM Recipe")
    fun getRecipes(): Flow<List<Recipe>>

    @Query("SELECT * FROM Recipe")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM Recipe WHERE id=:id")
    suspend fun getRecipe(id: String): Recipe?

    @Insert
    fun insert(recipes: List<Recipe>)

    @Query("DELETE FROM Recipe WHERE id IN (:ids)")
    fun deleteRecipes(ids: List<String>)

    @Update
    fun update(recipes: List<Recipe>)

    @Transaction
    suspend fun commit(deleted: List<Recipe>, updated: List<Recipe>, added: List<Recipe>) {
        if (deleted.isNotEmpty()) deleteRecipes(deleted.map { it.id })
        if (updated.isNotEmpty()) update(updated)
        if (added.isNotEmpty()) insert(added)
    }
}