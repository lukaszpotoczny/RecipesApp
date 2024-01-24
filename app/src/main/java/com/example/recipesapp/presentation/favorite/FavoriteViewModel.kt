package com.example.recipesapp.presentation.favorite

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.repo.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    private val _state = mutableStateOf(FavoriteState(emptyList()))
    val state: State<FavoriteState> = _state

    init {
        getRecipes()
    }

    private fun getRecipes() {
        dataRepository.getFavoriteRecipes().onEach { recipes ->
            _state.value = state.value.copy(recipes = recipes.sortedBy { it.title })
        }.launchIn(viewModelScope)
    }
}