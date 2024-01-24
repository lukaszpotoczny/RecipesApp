package com.example.recipesapp.presentation.add

import com.example.recipesapp.domain.model.RecipeBody


sealed class AddRecipeEvent {

    data class UpdateRecipe(val recipeBody: RecipeBody): AddRecipeEvent()
    data object PushRecipe: AddRecipeEvent()
    data object RemoveSideEffect: AddRecipeEvent()
}