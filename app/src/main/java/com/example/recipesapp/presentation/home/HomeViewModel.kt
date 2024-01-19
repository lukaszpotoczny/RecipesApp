package com.example.recipesapp.presentation.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipesapp.presentation.main.TAG
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.RecipeResponse
import com.example.recipesapp.domain.repo.DataRepository
import com.example.recipesapp.domain.repo.RecipeRepository
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository,
    private val dataRepository: DataRepository
): ViewModel(){

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        getRecipes()
        fetchRecipes()
    }

    private fun getRecipes(){
        dataRepository.getRecipes().onEach {
            _state.value = state.value.copy(recipes = it)
        }.launchIn(viewModelScope)
    }

    private fun fetchRecipes(){
        recipeRepository.getRecipes()
            .addOnSuccessListener { result ->
                val recipes = mutableListOf<Recipe>()
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val item = document.toObject<RecipeResponse>()
                    recipes.add(item.toRecipe(document.id))
                }
                updateRecipes(recipes)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    private fun updateRecipes(newList: List<Recipe>) {
        viewModelScope.launch {
            recipeRepository.updateRecipes(newList)
        }

    }

    private fun RecipeResponse.toRecipe(documentId: String): Recipe {
        return Recipe(
            id = documentId,
            title = title ?: "",
            description = description?: emptyList(),
            summary = summary ?: emptyList(),
            author = author
        )
    }


}