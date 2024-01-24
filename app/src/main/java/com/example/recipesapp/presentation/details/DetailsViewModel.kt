package com.example.recipesapp.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.repo.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.ChangeFavoriteState -> {
                viewModelScope.launch {
                    dataRepository.upsertRecipe(event.recipe.copy(isFavorite = !event.recipe.isFavorite))
                    sideEffect =
                        if (!event.recipe.isFavorite) "Recipe added to favorite list"
                        else "Recipe removed from favorite list"
                }
            }

            is DetailsEvent.RemoveSideEffect -> sideEffect = null
        }
    }
}