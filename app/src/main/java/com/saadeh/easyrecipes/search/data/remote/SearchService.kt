package com.saadeh.easyrecipes.search.data.remote

import com.saadeh.easyrecipes.common.model.SearchRecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("/recipes/complexSearch?")
    suspend fun searchRecipes(@Query("query") query: String): Response<SearchRecipesResponse>

}