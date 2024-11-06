package com.saadeh.easyrecipes.list.data

import com.saadeh.easyrecipes.common.model.RecipeResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ListService {

    @GET("recipes/random?number=20")
    suspend fun getRandom(): Response<RecipeResponse>

}