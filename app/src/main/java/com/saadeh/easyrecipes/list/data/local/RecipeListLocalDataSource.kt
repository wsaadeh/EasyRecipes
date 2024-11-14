package com.saadeh.easyrecipes.list.data.local

import com.saadeh.easyrecipes.common.local.EasyRecipeDao
import com.saadeh.easyrecipes.common.local.RecipeEntity
import com.saadeh.easyrecipes.common.model.RecipeDto

class RecipeListLocalDataSource(
    private val dao: EasyRecipeDao
) {
    suspend fun getRecipeList(): List<RecipeDto>{
        val entities = dao.getRecipeList()

        return entities.map {
            RecipeDto(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary,
            )
        }
    }

    suspend fun updateLocalItems(recipes:List<RecipeDto>){
        val entities = recipes.map {
            RecipeEntity(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary,
            )
        }
        dao.insertAll(entities)
    }

}