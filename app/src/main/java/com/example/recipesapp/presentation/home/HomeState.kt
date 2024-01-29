package com.example.recipesapp.presentation.home

import com.example.recipesapp.domain.model.Recipe

data class HomeState(
    val searchQuery: String = "",
    val recipes: List<Recipe> = emptyList()
)