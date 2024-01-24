package com.example.recipesapp.presentation.add

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipesapp.domain.model.Description
import com.example.recipesapp.domain.model.RecipeBody
import com.example.recipesapp.domain.model.Summary
import com.example.recipesapp.presentation.common.RecipeContentScreen
import com.example.recipesapp.presentation.details.DetailsTopBar

@Composable
fun RecipePreviewScreen(
    recipe: RecipeBody,
    onConfirm: () -> Unit,
    navigateUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        DetailsTopBar(
            isFavorite = true,
            onFavoriteClick = { },
            onBackClick = navigateUp
        )
        RecipeContentScreen(recipeBody = recipe, modifier = Modifier.padding(bottom = 24.dp))
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 20.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Button(
            onClick = onConfirm,
            modifier = Modifier.height(50.dp)
        ) {
            Text(text = "Confirm")
        }
    }

}

@Preview(showBackground = true)
@Composable
fun RecipePreviewScreenPreview() {
    val r = RecipeBody(
        summary = listOf(
            Summary(
                "Składniki", listOf(
                    "Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną",
                    "Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną"
                )
            )
        ),
        description = listOf(
            Description(
                "Przygotowanie", listOf(
                    "Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną",
                    "Odlać pół szklanki mleka i rozrobić w niej mąkę pszenną i ziemniaczaną"
                )
            )
        ),
        title = "Karpatka"
    )
    RecipePreviewScreen(recipe = r, {}) {}
}