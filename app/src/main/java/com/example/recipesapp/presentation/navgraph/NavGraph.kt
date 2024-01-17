package com.example.recipesapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(startDestination: String){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.RecipeNavigation.route,
            startDestination = Route.RecipeNavigatorScreen.route
        ) {
            composable(route = Route.RecipeNavigatorScreen.route) {
             //   RecipeNavigator()
            }
        }
    }
}