package com.saadeh.easyrecipes.detail.data.remote

import android.accounts.NetworkErrorException
import com.saadeh.easyrecipes.common.model.RecipeDto

class RecipeDetailRemoteDataSource(
    private val detailService: DetailService
) {
    suspend fun getRecipeById(id: String): Result<RecipeDto?>{
        return try {
            val response = detailService.getRecipeInformation(id)
            if (response.isSuccessful){
                val recipe = response.body()
                return Result.success(recipe)
            }else{
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }

    }
}