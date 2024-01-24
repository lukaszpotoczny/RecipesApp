package com.example.recipesapp.presentation.navigator

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.domain.model.RecipeBody
import com.example.recipesapp.presentation.add.AddRecipeEvent
import com.example.recipesapp.presentation.add.AddRecipeScreen
import com.example.recipesapp.presentation.add.RecipePreviewScreen
import com.example.recipesapp.presentation.add.AddRecipeViewModel
import com.example.recipesapp.presentation.details.DetailsEvent
import com.example.recipesapp.presentation.details.DetailsScreen
import com.example.recipesapp.presentation.details.DetailsViewModel
import com.example.recipesapp.presentation.favorite.FavoriteScreen
import com.example.recipesapp.presentation.favorite.FavoriteViewModel
import com.example.recipesapp.presentation.home.HomeScreen
import com.example.recipesapp.presentation.home.HomeViewModel
import com.example.recipesapp.presentation.navgraph.Route

@Composable
fun AppNavigator() {
    val items = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_add_recipe, text = "Add Recipe"),
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_favorite_fill, text = "Favorite"),
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(1) }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.AddRecipe.route -> 0
            Route.HomeScreen.route -> 1
            Route.FavoritesScreen.route -> 2
            else -> -1
        }
    }

    val isBottomBarVisible = remember(key1 = selectedItem) { selectedItem != -1 }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomNavigation(items = items, selectedItem = selectedItem, onItemClick = {
                    when (it) {
                        0 -> navigateToTab(navController, Route.AddRecipe.route)
                        1 -> navigateToTab(navController, Route.HomeScreen.route)
                        2 -> navigateToTab(navController, Route.FavoritesScreen.route)
                    }
                })
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val state = viewModel.state.value
                HomeScreen(
                    state = state,
                    navigateToDetails = { recipe ->
                        navigateToDetails(navController, recipe)
                    }
                )
            }

            composable(Route.FavoritesScreen.route) {
                val viewModel: FavoriteViewModel = hiltViewModel()
                val state = viewModel.state.value
                FavoriteScreen(
                    state = state,
                    navigateToDetails = { recipe ->
                        navigateToDetails(navController, recipe)
                    }
                )
            }

            composable(Route.DetailsScreen.route) {
                val viewModel: DetailsViewModel = hiltViewModel()
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(DetailsEvent.RemoveSideEffect)
                }
                navController.previousBackStackEntry?.savedStateHandle?.get<Recipe?>("recipe")
                    ?.let { recipe ->
                        DetailsScreen(
                            recipe = recipe,
                            onEvent = viewModel::onEvent,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }

            composable(Route.AddRecipe.route) { backStackEntry ->
                val sharedEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.AddRecipe.route)
                }
                val viewModel: AddRecipeViewModel = hiltViewModel(sharedEntry)
                AddRecipeScreen(
                    recipe = viewModel.recipe,
                    navigateToPreview = { recipe ->
                        viewModel.onEvent(AddRecipeEvent.UpdateRecipe(recipe))
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "recipeBody",
                            recipe
                        )
                        navController.navigate(Route.RecipePreview.route)
                    }
                )
            }

            composable(Route.RecipePreview.route) { backStackEntry ->
                val sharedEntry = remember(backStackEntry) {
                    navController.getBackStackEntry(Route.AddRecipe.route)
                }
                val viewModel: AddRecipeViewModel = hiltViewModel(sharedEntry)
                if (viewModel.sideEffect != null) {
                    Toast.makeText(LocalContext.current, viewModel.sideEffect, Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onEvent(AddRecipeEvent.RemoveSideEffect)
                    navController.navigateUp()
                }
                navController.previousBackStackEntry?.savedStateHandle
                    ?.get<RecipeBody?>("recipeBody")?.let { recipe ->
                        RecipePreviewScreen(
                            recipe = recipe,
                            onConfirm = { viewModel.onEvent(AddRecipeEvent.PushRecipe) },
                            navigateUp = { navController.navigateUp() }
                        )
                    }
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) { saveState = true }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun navigateToDetails(navController: NavController, recipe: Recipe) {
    navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
    navController.navigate(Route.DetailsScreen.route)
}