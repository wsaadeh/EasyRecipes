package com.saadeh.easyrecipes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun EasyRecipeApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "recipe_start") {

        composable(route = "recipe_start") {
            RecipeStartScreen(navController)
        }

        composable(route = "recipe_screen") {
            RecipeScreen(navController)
        }

        composable(
            route = "recipe_detail" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val id = requireNotNull(navBackStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(id, navController)
        }

        composable(
            route = "search_recipes" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController)
        }

    }

}