package com.example.recipesapp.presentation.navgraph

sealed class Route(val route: String) {

    object HomeScreen: Route("home")
    object AddRecipe: Route("addRecipe")
    object RecipePreview: Route("preview")
    object FavoritesScreen: Route("favorites")
    object DetailsScreen: Route("details")
    object AppStartNavigation: Route("appStartNav")
    object RecipeNavigation: Route("recipeNav")
    object RecipeNavigatorScreen: Route("recipeNavScreen")

}