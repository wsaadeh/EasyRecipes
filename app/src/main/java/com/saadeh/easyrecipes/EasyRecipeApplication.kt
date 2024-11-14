package com.saadeh.easyrecipes

import android.app.Application
import androidx.room.Room
import com.saadeh.easyrecipes.common.data.RetrofitClient
import com.saadeh.easyrecipes.common.local.EasyRecipesDatabase
import com.saadeh.easyrecipes.detail.data.RecipeDetailRepository
import com.saadeh.easyrecipes.detail.data.local.RecipeDetailLocalDataSource
import com.saadeh.easyrecipes.detail.data.remote.DetailService
import com.saadeh.easyrecipes.detail.data.remote.RecipeDetailRemoteDataSource
import com.saadeh.easyrecipes.list.data.RecipeListRepository
import com.saadeh.easyrecipes.list.data.local.RecipeListLocalDataSource
import com.saadeh.easyrecipes.list.data.remote.ListService
import com.saadeh.easyrecipes.list.data.remote.RecipeListRemoteDataSource
import com.saadeh.easyrecipes.search.data.RecipeSearchRepository
import com.saadeh.easyrecipes.search.data.local.RecipeSearchLocalDataSource
import com.saadeh.easyrecipes.search.data.remote.RecipeSearchRemoteDataSource
import com.saadeh.easyrecipes.search.data.remote.SearchService

class EasyRecipeApplication: Application() {
    val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            EasyRecipesDatabase::class.java,"database-easy-recipe"
        ).build()
    }

    private val listService by lazy {
        RetrofitClient.retrofitInstance.create(ListService::class.java)
    }

    private val detailService by lazy {
        RetrofitClient.retrofitInstance.create(DetailService::class.java)
    }

    private val searchService by lazy {
        RetrofitClient.retrofitInstance.create(SearchService::class.java)
    }

    private val localDataSource: RecipeListLocalDataSource by lazy {
        RecipeListLocalDataSource(db.getRecipeDao())
    }

    private val remoteDataSource: RecipeListRemoteDataSource by lazy {
        RecipeListRemoteDataSource(listService)
    }

    private val localDetailDataSource: RecipeDetailLocalDataSource by lazy {
        RecipeDetailLocalDataSource(db.getRecipeDao())
    }

    private val remoteDetailDatasource: RecipeDetailRemoteDataSource by lazy {
        RecipeDetailRemoteDataSource(detailService)
    }

    private val localSearchDataSource: RecipeSearchLocalDataSource by lazy {
        RecipeSearchLocalDataSource(db.getRecipeDao())
    }

    private val remoteSearchDataSource: RecipeSearchRemoteDataSource by lazy {
        RecipeSearchRemoteDataSource(searchService)
    }

    val repository: RecipeListRepository by lazy {
        RecipeListRepository(
            local = localDataSource,
            remote = remoteDataSource
        )
    }

    val repDetail: RecipeDetailRepository by lazy {
        RecipeDetailRepository(
            localDataSource = localDetailDataSource,
            remoteDataSource = remoteDetailDatasource
        )
    }

    val repSearch: RecipeSearchRepository by lazy {
        RecipeSearchRepository(
            localDataSource = localSearchDataSource,
            remoteDataSource =  remoteSearchDataSource
        )
    }

}