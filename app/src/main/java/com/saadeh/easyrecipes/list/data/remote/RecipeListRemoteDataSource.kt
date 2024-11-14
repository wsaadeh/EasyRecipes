package com.saadeh.easyrecipes.list.data.remote

import android.accounts.NetworkErrorException
import com.saadeh.easyrecipes.common.model.RecipeDto

class RecipeListRemoteDataSource(
    private val listService: ListService
) {
   suspend fun getRecipeList(): Result<List<RecipeDto>?>{
       return try {
           val response = listService.getRandom()
           if (response.isSuccessful){
               Result.success(response.body()?.recipes ?: emptyList())
           }else{
               Result.failure(NetworkErrorException(response.message()))
           }
       }catch (ex: Exception){
           ex.printStackTrace()
           Result.failure(ex)
       }
   }

}