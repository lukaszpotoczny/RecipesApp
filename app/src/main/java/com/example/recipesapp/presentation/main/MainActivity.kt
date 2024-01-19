package com.example.recipesapp.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.recipesapp.domain.model.Description
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.Summary
import com.example.recipesapp.domain.repo.RecipeRepository
import com.example.recipesapp.presentation.navgraph.NavGraph
import com.example.recipesapp.presentation.navgraph.Route
import com.example.recipesapp.ui.theme.RecipesAppTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val db = Firebase.firestore


    @Inject
    lateinit var repo: RecipeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesAppTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val scope = rememberCoroutineScope()
                // A surface container using the 'background' color from the theme
                Column {
                    Button(
                        modifier = Modifier.height(52.dp).fillMaxWidth(),
                        onClick = { scope.launch { viewModel.fetchRecipes() } }
                    ) { Text(text = "Refresh") }
                    Button(
                        modifier = Modifier.height(52.dp).fillMaxWidth(),
                        onClick = { addRecipe() }
                    ) { Text(text = "Add") }

                    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                        NavGraph(startDestination = Route.RecipeNavigation.route)
                    }
                }


            }
        }
    }

    private fun readRecipe() {
        db.collection("recipes")
            .orderBy("title")
          //  .whereArrayContains()
            .get()
            .addOnSuccessListener { result ->

                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    val a = document.toObject<Recipe>()
                    Log.d(TAG, a.summary?.first()?.ingredients?.first().toString())
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }


    private fun addRecipe() {
        val summary = Summary(
            name = "Składniki",
            ingredients = listOf("½ kostki masła", "½ szklanki wody")
        )
        val summary2 = Summary(
            name = "Masa",
            ingredients = listOf("½ litra mleka", "3 łyżki mąki pszennej")
        )
        val desc = Description(
            name = "Przygotowanie",
            steps = listOf("Zagotować masło i wodę", "Do gorącej masy dodać mąkę i mocno mieszać. Ostudzić")
        )

        val recipe = Recipe(
            id = "aaaaa",
            title = "KARPATKA",
            description = listOf(desc),
            summary = listOf(summary, summary2),
            author = "Łukasz"
        )

// Add a new document with a generated ID
        db.collection("recipes")
            .add(recipe)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}


val TAG = "dupa"



@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RecipesAppTheme {
        Greeting("Android")
    }
}