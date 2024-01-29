package com.example.recipesapp.domain.repo

import com.example.recipesapp.domain.model.Recipe
import kotlinx.coroutines.flow.Flow

interface DataRepository {

    suspend fun upsertRecipe(recipe: Recipe)
    suspend fun deleteRecipe(recipe: Recipe)
    suspend fun getRecipe(id: String): Recipe?
    fun getRecipes(query: String): Flow<List<Recipe>>
    fun getFavoriteRecipes(): Flow<List<Recipe>>
}