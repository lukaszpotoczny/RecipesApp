package com.example.recipesapp.data.repo

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.recipesapp.data.local.RecipeDao
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.repo.DataRepository
import kotlinx.coroutines.flow.Flow

class DataRepositoryImpl(private val recipeDao: RecipeDao) : DataRepository {

    override suspend fun upsertRecipe(recipe: Recipe) {
        recipeDao.upsert(recipe)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        recipeDao.delete(recipe)
    }

    override suspend fun getRecipe(id: String): Recipe? {
        return recipeDao.getRecipe(id)
    }

    override fun getRecipes(): Flow<List<Recipe>> {
        return recipeDao.getRecipes()
    }

    override fun getFavoriteRecipes(): Flow<List<Recipe>> {
        return recipeDao.getFavoriteRecipes()
    }

}