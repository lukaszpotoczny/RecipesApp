package com.example.recipesapp.presentation.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.RecipeBody

@Composable
fun RecipeContentScreen(
    recipe: Recipe,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 12.dp, end = 12.dp),
    ) {
        Text(
            text = recipe.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))
        recipe.summary.forEach { summary ->
            HeadlineText(text = summary.name ?: "Składniki")
            summary.ingredients?.forEach { ingredient ->
                BulletText(text = ingredient)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        recipe.description.forEach { description ->
            if (!description.steps.isNullOrEmpty()){
                HeadlineText(text = description.name ?: "Przygotowanie")
            }
            description.steps?.forEachIndexed { index, step ->
                NumberText(text = step, number = index)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun RecipeContentScreen(recipeBody: RecipeBody, modifier: Modifier = Modifier) {
    val recipe = Recipe(
        id = "",
        isFavorite = true,
        title = recipeBody.title ?: "",
        summary = recipeBody.summary ?: emptyList(),
        description = recipeBody.description?: emptyList(),
        author = recipeBody.author
    )
    RecipeContentScreen(recipe = recipe, modifier)
}

@Composable
fun HeadlineText(text: String) {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = text,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
fun BulletText(text: String) {
    Row(
        modifier = Modifier.padding(start = 10.dp, top = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_circle),
            contentDescription = null,
            modifier = Modifier
                .size(16.dp)
                .padding(top = 8.dp)
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun NumberText(text: String, number: Int) {
    Row(
        modifier = Modifier.padding(start = 14.dp, top = 4.dp)
    ) {
        Text(
            text = "${number + 1}. ",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            modifier = Modifier.padding(start = 4.dp),
            text = text,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}