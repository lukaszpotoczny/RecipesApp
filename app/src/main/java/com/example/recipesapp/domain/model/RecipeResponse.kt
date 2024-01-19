package com.example.recipesapp.domain.model

data class RecipeResponse(
    val title: String? = null,
    val summary: List<Summary>? = null,
    val description: List<Description>? = null,
    val author: String? = null
)