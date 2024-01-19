package com.example.recipesapp.presentation.navgraph

sealed class Route(val route: String) {

    object HomeScreen: Route("home")
    object SearchScreen: Route("search")
    object FavoritesScreen: Route("favorites")
//    object DetailsScreen: Route("details")
    object AppStartNavigation: Route("appStartNav")
    object RecipeNavigation: Route("recipeNav")
    object RecipeNavigatorScreen: Route("recipeNavScreen")

}