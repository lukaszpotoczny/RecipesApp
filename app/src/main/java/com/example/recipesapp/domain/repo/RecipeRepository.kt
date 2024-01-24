package com.example.recipesapp.domain.repo

import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.RecipeBody
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot

interface RecipeRepository {

    fun getRecipes(): Task<QuerySnapshot>
    fun addRecipe(recipe: RecipeBody): Task<DocumentReference>
    suspend fun updateRecipes(newList: List<Recipe>)
}