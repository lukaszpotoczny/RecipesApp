package com.example.recipesapp.domain.repo

import com.example.recipesapp.domain.model.Recipe
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface RecipeRepository {

    fun getRecipes(): Task<QuerySnapshot>
    suspend fun updateRecipes(newList: List<Recipe>)
}