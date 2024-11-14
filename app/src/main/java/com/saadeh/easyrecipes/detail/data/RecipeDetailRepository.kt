package com.saadeh.easyrecipes.detail.data

import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.detail.data.local.RecipeDetailLocalDataSource
import com.saadeh.easyrecipes.detail.data.remote.RecipeDetailRemoteDataSource

class RecipeDetailRepository(
    private val localDataSource: RecipeDetailLocalDataSource,
    private val remoteDataSource: RecipeDetailRemoteDataSource
) {
    suspend fun getRecipeById(id: String): Result<RecipeDto?>{
        return try {
            val apiResult = remoteDataSource.getRecipeById(id)
            if (apiResult.isSuccess){
                val detailRemote = apiResult.getOrNull()
                if (detailRemote != null){
                    return Result.success(detailRemote)
                }else{
                    return Result.success(localDataSource.getRecipeById(id))
                }
            }else{
                val localData = localDataSource.getRecipeById(id)
                return Result.success(localData)
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}