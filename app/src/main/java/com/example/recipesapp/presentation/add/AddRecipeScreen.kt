package com.example.recipesapp.presentation.add

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.Description
import com.example.recipesapp.domain.model.RecipeBody
import com.example.recipesapp.domain.model.Summary
import com.example.recipesapp.ui.theme.RecipesAppTheme
import kotlinx.coroutines.launch

@Composable
fun AddRecipeScreen(
    recipe: RecipeBody?,
    navigateToPreview: (RecipeBody) -> Unit,
) {
    var title by remember { mutableStateOf(recipe?.title ?: "") }
    var author by remember { mutableStateOf(recipe?.author ?: "") }
    val ingredients = remember {
        recipe?.summary?.firstOrNull()?.ingredients?.let {
            mutableStateListOf<String>().apply { addAll(it) }
        } ?: run { mutableStateListOf("") }
    }
    val steps = remember {
        recipe?.description?.firstOrNull()?.steps?.let {
            mutableStateListOf<String>().apply { addAll(it) }
        } ?: run { mutableStateListOf("") }
    }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Add new recipe",
            modifier = Modifier.padding(horizontal = 16.dp).align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
        )


        EditTextField(value = title, onValueChange = { title = it }, label = "Title")
        Spacer(modifier = Modifier.height(22.dp))

        Text(
            text = "Ingredients:", modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )

        ingredients.forEachIndexed { index, item ->
            SmallEditTextField(
                value = item,
                onValueChange = {
                    ingredients.removeAt(index)
                    ingredients.add(index, it)
                },
                label = "Ingredient ${index + 1}"
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        AddItemText(text = "Add next item", onClick = { ingredients.add("") })

        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Steps:", modifier = Modifier.padding(horizontal = 16.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        steps.forEachIndexed { index, item ->
            SmallEditTextField(
                value = item,
                onValueChange = {
                    steps.removeAt(index)
                    steps.add(index, it)
                },
                label = "Step ${index + 1}"
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        AddItemText(text = "Add next item", onClick = { steps.add("") })

        Spacer(modifier = Modifier.height(32.dp))
        SmallEditTextField(
            value = author,
            onValueChange = { author = it },
            label = "Author"
        )

        Spacer(modifier = Modifier.height(22.dp))
        Button(
            onClick = {
                if (title.isNotEmpty() && ingredients.first().isNotEmpty()) {
                    val newRecipe = RecipeBody(
                        title = title,
                        author = author.takeIf { it.isNotEmpty() },
                        summary = listOf(
                            Summary(
                                name = "Ingredients",
                                ingredients = ingredients.mapNotNull { it.ifEmpty { null } }
                            )
                        ),
                        description = listOf(
                            Description(
                                name = "Description",
                                steps = if (!steps.firstOrNull().isNullOrEmpty()) {
                                    steps.mapNotNull { it.ifEmpty { null } }
                                } else emptyList()
                            )
                        ),
                    )
                    navigateToPreview(newRecipe)
                } else {
                    coroutineScope.launch {
                        Toast.makeText(
                            context, "Title and ingredients are required!", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
        ) {
            Text(text = "See Preview")
        }

        Spacer(modifier = Modifier.height(42.dp))
    }
}


@Composable
fun AddItemText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_add_item), contentDescription = null)
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = text)
    }
}


@Preview(showBackground = true)
@Composable
fun AddRecipeScreenPreview() {
    RecipesAppTheme {
        AddRecipeScreen(navigateToPreview = {}, recipe = null)
    }
}