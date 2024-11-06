package com.saadeh.easyrecipes.common.model

data class SearchRecipesResponse(
    val results: List<SearchRecipeDto>
)

data class SearchRecipeDto(
    val id: Int,
    val title: String,
    val image: String
)
