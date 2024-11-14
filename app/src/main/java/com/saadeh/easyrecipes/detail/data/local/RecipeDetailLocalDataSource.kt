package com.saadeh.easyrecipes.detail.data.local

import com.saadeh.easyrecipes.common.local.EasyRecipeDao
import com.saadeh.easyrecipes.common.model.RecipeDto

class RecipeDetailLocalDataSource(
    private val dao: EasyRecipeDao
) {
    suspend fun getRecipeById(id: String): RecipeDto{
        val recipe = dao.getRecipeById(id).let {
            RecipeDto(
                id=it.id,
                title = it.title,
                image = it.image,
                summary = it.summary,
            )
        }
        return recipe
    }
}