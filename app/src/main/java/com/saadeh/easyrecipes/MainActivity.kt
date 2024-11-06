package com.saadeh.easyrecipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.saadeh.easyrecipes.detail.presentation.RecipeDetailViewModel
import com.saadeh.easyrecipes.list.presentation.RecipeListViewModel
import com.saadeh.easyrecipes.search.presentation.SearchRecipeViewModel


class MainActivity : ComponentActivity() {

    private val listViewModel by viewModels<RecipeListViewModel> { RecipeListViewModel.Factory  }
    private val detailViewModel by viewModels<RecipeDetailViewModel> { RecipeDetailViewModel.Factory }
    private val searchRecipeViewModel by viewModels<SearchRecipeViewModel> { SearchRecipeViewModel.Factory  }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                color = MaterialTheme.colorScheme.background
            ) {
                EasyRecipeApp(
                    listViewModel,
                    detailViewModel,
                    searchRecipeViewModel
                )
            }
        }
    }
}

