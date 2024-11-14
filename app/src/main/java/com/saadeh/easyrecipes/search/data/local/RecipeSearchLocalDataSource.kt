package com.saadeh.easyrecipes.search.data.local

import com.saadeh.easyrecipes.common.local.EasyRecipeDao
import com.saadeh.easyrecipes.common.model.SearchRecipeDto

class RecipeSearchLocalDataSource(
    private val dao: EasyRecipeDao
) {
    suspend fun getRecipeSearch(query: String): List<SearchRecipeDto>{
        val recipes = dao.getRecipeBySearch(query).map {
            SearchRecipeDto(
                id = it.id,
                title = it.title,
                image = it.image,
            )
        }
        return recipes
    }
}