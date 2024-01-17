package com.example.recipesapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.Summary
import com.example.recipesapp.ui.theme.RecipesAppTheme
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import java.io.Serializable

class MainActivity : ComponentActivity() {

    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesAppTheme {
                // A surface container using the 'background' color from the theme
                Button(
                    modifier = Modifier
                        .height(52.dp)
                        .fillMaxWidth(),
                    onClick = {
                        readRecipe()
//                        addRecipe()
                    }

                ) {
                    Text(text = "Start")
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
            name = "aaa",
            ingredients = listOf("bbb", "ccc")
        )

        val recipe = Recipe(
            title = "hello",
            description = "desc",
            summary = listOf(summary)
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