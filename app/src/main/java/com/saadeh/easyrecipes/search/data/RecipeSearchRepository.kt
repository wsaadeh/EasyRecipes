package com.saadeh.easyrecipes.search.data

import com.saadeh.easyrecipes.common.model.SearchRecipeDto
import com.saadeh.easyrecipes.search.data.local.RecipeSearchLocalDataSource
import com.saadeh.easyrecipes.search.data.remote.RecipeSearchRemoteDataSource

class RecipeSearchRepository(
    private val localDataSource: RecipeSearchLocalDataSource,
    private val remoteDataSource: RecipeSearchRemoteDataSource
) {
    suspend fun getRecipeBySearch(query: String): Result<List<SearchRecipeDto>?>{
        return try {
            val apiResult = remoteDataSource.getRecipeSearch(query)
            if (apiResult.isSuccess){
                return Result.success(apiResult.getOrNull())
            }else {
                return Result.success(localDataSource.getRecipeSearch(query))
            }
//            }else{
//                val localData = localDataSource.getRecipeSearch(query)
//                return Result.success(localData.getOrNull())
//            }

        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}