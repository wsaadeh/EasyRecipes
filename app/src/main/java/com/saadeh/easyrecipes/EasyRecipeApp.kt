package com.saadeh.easyrecipes

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.saadeh.easyrecipes.detail.presentation.RecipeDetailViewModel
import com.saadeh.easyrecipes.detail.presentation.ui.RecipeDetailScreen
import com.saadeh.easyrecipes.list.presentation.RecipeListViewModel
import com.saadeh.easyrecipes.list.presentation.ui.RecipeScreen
import com.saadeh.easyrecipes.search.presentation.SearchRecipeViewModel
import com.saadeh.easyrecipes.search.presentation.ui.SearchRecipesScreen

@Composable
fun EasyRecipeApp(
    listViewModel: RecipeListViewModel,
    detailViewModel: RecipeDetailViewModel,
    searchRecipeViewModel: SearchRecipeViewModel
) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "recipe_start") {

        composable(route = "recipe_start") {
            RecipeStartScreen(navController)
        }

        composable(route = "recipe_screen") {
            RecipeScreen(navController, listViewModel)
        }

        composable(
            route = "recipe_detail" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            val id = requireNotNull(navBackStackEntry.arguments?.getString("itemId"))
            RecipeDetailScreen(id, navController, detailViewModel)
        }

        composable(
            route = "search_recipes" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val id = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchRecipesScreen(id, navController,searchRecipeViewModel)
        }

    }

}