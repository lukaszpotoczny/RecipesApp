package com.example.recipesapp.presentation.details

import com.example.recipesapp.domain.model.Recipe


sealed class DetailsEvent {

    data class ChangeFavoriteState(val recipe: Recipe): DetailsEvent()
    object RemoveSideEffect: DetailsEvent()
}