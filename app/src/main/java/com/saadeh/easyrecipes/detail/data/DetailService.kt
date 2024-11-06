package com.saadeh.easyrecipes.detail.data

import com.saadeh.easyrecipes.common.model.RecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailService {

    @GET("recipes/{id}/information?includeNutrition=false")
    suspend fun getRecipeInformation(@Path("id") id: String): Response<RecipeDto>

}