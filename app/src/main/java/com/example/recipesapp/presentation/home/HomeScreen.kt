package com.example.recipesapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.presentation.common.RecipeList
import com.example.recipesapp.presentation.common.SearchBar

@Composable
fun HomeScreen(
    state: HomeState,
    updateSearchQuery: (String) -> Unit,
    navigateToDetails: (Recipe) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(start = 8.dp, end = 8.dp, top = 32.dp)
    ) {
        SearchBar(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = state.searchQuery,
            onValueChange = {updateSearchQuery(it) },
            onSearch = {}
        )
//        Spacer(modifier = Modifier.height(24.dp))
//        Text(
//            text = "Recipes",
//            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
////            color = colorResource(id = R.color.text_title)
//        )
        Spacer(modifier = Modifier.height(32.dp))

        RecipeList(recipes = state.recipes, onClick = { navigateToDetails(it) })
    }
}