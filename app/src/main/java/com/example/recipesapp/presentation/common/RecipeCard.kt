package com.example.recipesapp.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.ui.theme.RecipesAppTheme

@Composable
fun RecipeCard(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    onClick: () -> Unit
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Image(
            modifier = Modifier
                .size(86.dp)
                .clip(MaterialTheme.shapes.medium),
            painter = painterResource(id = R.drawable.food),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 20.dp)
                .height(86.dp)
        ) {
            Text(
                text = recipe.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            recipe.author?.let { author ->
                Text(
                    modifier = Modifier.align(Alignment.End),
                    text = author,
                    style = MaterialTheme.typography.labelMedium.copy(fontStyle = FontStyle.Italic),
//                    color = colorResource(id = R.color.body)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    RecipesAppTheme(dynamicColor = false) {
        RecipeCard(
            recipe = Recipe(
               id = "aaa",
                title = "Tasty chicken curry",
                description = listOf(),
                summary = listOf(),
                author = "Łukasz"
                ),
            onClick = {}
        )
    }
}