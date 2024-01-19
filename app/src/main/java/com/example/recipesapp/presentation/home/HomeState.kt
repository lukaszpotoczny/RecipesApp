package com.example.recipesapp.presentation.home

import com.example.recipesapp.domain.model.Recipe

data class HomeState(val recipes: List<Recipe> = emptyList())