package com.saadeh.easyrecipes.list.data

import androidx.compose.ui.text.font.emptyCacheFontFamilyResolver
import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.list.data.local.RecipeListLocalDataSource
import com.saadeh.easyrecipes.list.data.remote.RecipeListRemoteDataSource

class RecipeListRepository(
    private val local: RecipeListLocalDataSource,
    private val remote: RecipeListRemoteDataSource,
) {
    suspend fun getRecipeList(): Result<List<RecipeDto>?>{
        return try {
            val apiResult = remote.getRecipeList()
            if (apiResult.isSuccess){
                val recipesRemote = apiResult.getOrNull() ?: emptyList()
                if (recipesRemote.isNotEmpty()){
                    local.updateLocalItems(recipesRemote)
                }
                return Result.success(local.getRecipeList())
            }else{
                val localData = local.getRecipeList()
                if (localData.isEmpty()){
                    return apiResult
                }else{
                    Result.success(localData)
                }
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}