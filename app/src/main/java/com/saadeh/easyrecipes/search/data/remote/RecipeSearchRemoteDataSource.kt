package com.saadeh.easyrecipes.search.data.remote

import android.accounts.NetworkErrorException
import com.saadeh.easyrecipes.common.model.SearchRecipeDto

class RecipeSearchRemoteDataSource(
    private val searchService: SearchService
) {
    suspend fun getRecipeSearch(query: String): Result<List<SearchRecipeDto>?>{
        return try {
            val response = searchService.searchRecipes(query)
            if (response.isSuccessful){
                val recipes = response.body()?.results
                return Result.success(recipes)
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}