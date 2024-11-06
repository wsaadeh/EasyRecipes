package com.saadeh.easyrecipes

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.saadeh.easyrecipes.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Composable
fun RecipeScreen(navHostController: NavHostController) {
    var recipes by rememberSaveable { mutableStateOf<List<RecipeDto>>(emptyList()) }
    val retrofit = RetrofitClient.retrofitInstance.create(ApiService::class.java)
    if (recipes.isEmpty()) {
        retrofit.getRandom().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse>,
                response: Response<RecipeResponse>
            ) {
                if (response.isSuccessful) {
                    recipes = response.body()?.recipes ?: emptyList()
                } else {
                    Log.d("RecipeScreen", "Request error :: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d("RecipeScreen", "Network error :: ${t.message}")
            }

        })
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        RecipeContent(
            recipes = recipes,
            onSearchClicked = { query ->
                //remove spaces from query
                val tempCleanQuery = query.trim()
                if (tempCleanQuery.isNotEmpty()) {
                    navHostController.navigate(route = "search_recipes/$tempCleanQuery")
                }
            },
            onClick = { itemClicked ->
                navHostController.navigate(route = "recipe_detail/${itemClicked.id}")
            }
        )
    }

}

@Composable
fun RecipeContent(
    modifier: Modifier = Modifier,
    recipes: List<RecipeDto>,
    onSearchClicked: (String) -> Unit,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        //Remove the commented code to enable search recipes feature
        var query by remember { mutableStateOf("") }

        SearchSession(
            label = "Find best recipes \nfor cooking",
            query = query,
            onValueChange = { newValue ->
                query = newValue
            },
            onSearchClicked = onSearchClicked
        )

        RecipesSession(
            label = "Recipes",
            recipes = recipes,
            onClick = onClick
        )

    }
}

@Composable
fun SearchSession(
    label: String,
    query: String,
    onValueChange: (String) -> Unit,
    onSearchClicked: (String) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )

    ERSearchBar(
        query = query,
        placeHolder = "Search Recipes",
        onValueChange = onValueChange,
        onSearchClicked = {
            onSearchClicked.invoke(query)
        }
    )
}

@Composable
fun RecipesSession(
    label: String,
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    Text(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        text = label
    )
    RecipeList(
        recipes = recipes,
        onClick = onClick
    )
}

@Composable
fun RecipeList(
    modifier: Modifier = Modifier,
    recipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    LazyColumn(
        modifier = modifier.padding(16.dp)
    ) {
        items(recipes)
        {
            RecipeItem(recipe = it, onClick = onClick)
        }
    }
}

@Composable
fun RecipeItem(
    recipe: RecipeDto,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick.invoke(recipe) }
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 8.dp, topStart = 8.dp))
                .fillMaxWidth()
                .height(150.dp),
            model = recipe.image, contentDescription = "${recipe.title} Image"
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            modifier = Modifier.padding(8.dp),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            text = recipe.title
        )

        ERHtmlText(text = recipe.summary, maxLine = 3)
    }
}

@Preview(showBackground = true)
@Composable
fun RecipePreview(modifier: Modifier = Modifier) {
    EasyRecipesTheme {
        RecipeContent(
            recipes = emptyList(),
            onSearchClicked = {

            },
            onClick = {

            }
        )
    }
}