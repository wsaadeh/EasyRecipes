package com.saadeh.easyrecipes.detail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saadeh.easyrecipes.designsystem.components.ERHtmlText
import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.detail.presentation.RecipeDetailViewModel


@Composable
fun RecipeDetailScreen(
    id: String,
    navHostController: NavHostController,
    viewModel: RecipeDetailViewModel) {

    val recipeDto = viewModel.uiRecipeDetailById.collectAsState()
    viewModel.fetchRecipeById(id)

    recipeDto.value?.let {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { navHostController.popBackStack()}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back Button")
                }
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = it.title
                )
            }
            RecipeDetailContent(it)
        }
    }
}

@Composable
fun RecipeDetailContent(recipe: RecipeDto) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .height(400.dp)
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )

        ERHtmlText(
            text = recipe.summary
        )
    }
}