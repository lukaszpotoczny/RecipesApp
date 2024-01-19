package com.example.recipesapp.presentation.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.recipesapp.domain.model.Recipe

@Composable
fun RecipeList(
    modifier: Modifier = Modifier,
    recipes: List<Recipe>,
    onClick: (Recipe) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(all = 3.dp)
    ) {
        items(count = recipes.size) {
            RecipeCard(recipe = recipes[it], onClick = { onClick(recipes[it]) })
        }
    }
}