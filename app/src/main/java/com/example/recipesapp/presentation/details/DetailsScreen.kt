package com.example.recipesapp.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipesapp.domain.model.Description
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.Summary
import com.example.recipesapp.presentation.common.RecipeContentScreen

@Composable
fun DetailsScreen(
    recipe: Recipe,
    onEvent: (DetailsEvent) -> Unit,
    navigateUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            isFavorite = recipe.isFavorite,
            onFavoriteClick = { onEvent(DetailsEvent.ChangeFavoriteState(recipe)) },
            onBackClick = navigateUp
        )
        RecipeContentScreen(recipe = recipe)
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    val r= Recipe(
        id = "a",
        author = "a",
        isFavorite = true,
        summary = listOf(Summary("Składniki", listOf("Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną",
            "Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną"))),
        description = listOf(
            Description("Przygotowanie", listOf("Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną",
            "Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną"))
        ),
        title = "Karpatka"
    )
   DetailsScreen(recipe = r, onEvent = {}) {}
}