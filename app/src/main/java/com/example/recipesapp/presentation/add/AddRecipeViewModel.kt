package com.example.recipesapp.presentation.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.domain.model.RecipeBody
import com.example.recipesapp.domain.repo.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
) : ViewModel() {

    var sideEffect by mutableStateOf<String?>(null)
        private set

    var recipe by mutableStateOf<RecipeBody?>(null)
        private set

    fun onEvent(event: AddRecipeEvent) {
        when (event) {
            is AddRecipeEvent.PushRecipe -> {
                recipe?.let {
                    viewModelScope.launch {
                        recipeRepository.addRecipe(it)
                            .addOnSuccessListener {
                                recipe = null
                                sideEffect = "Successfully added a new recipe"
                            }
                            .addOnFailureListener {
                                sideEffect = "Error adding recipe"
                            }
                    }
                }
            }

            is AddRecipeEvent.UpdateRecipe -> {
                recipe = event.recipeBody
            }

            is AddRecipeEvent.RemoveSideEffect -> {
                sideEffect = null
            }
        }
    }
}