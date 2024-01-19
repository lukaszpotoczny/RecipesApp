package com.example.recipesapp.presentation.navigator

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.recipesapp.R
import com.example.recipesapp.domain.model.Recipe
import com.example.recipesapp.presentation.home.HomeScreen
import com.example.recipesapp.presentation.home.HomeViewModel
import com.example.recipesapp.presentation.navgraph.Route

@Composable
fun AppNavigator() {
    val items = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
            BottomNavigationItem(icon = R.drawable.ic_favorite_fill, text = "Favorite"),
        )
    }
    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    selectedItem = remember(key1 = backStackState) {
        when (backStackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.FavoritesScreen.route -> 1
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
                        0 -> navigateToTab(navController, Route.HomeScreen.route)
                        1 -> navigateToTab(navController, Route.FavoritesScreen.route)
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
//    navController.currentBackStackEntry?.savedStateHandle?.set("article", article)
//    navController.navigate(Route.DetailsScreen.route)
}