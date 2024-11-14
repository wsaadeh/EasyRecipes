package com.saadeh.easyrecipes.common.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EasyRecipeDao {

    @Query("Select * from RecipeEntity where id = :id")
    fun getRecipeById(id: String): RecipeEntity

    @Query("Select * from RecipeEntity where title like '%' || :query || '%'")
    fun getRecipeBySearch(query: String): List<RecipeEntity>

    @Query("Select * from RecipeEntity")
    fun getRecipeList(): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(recipes: List<RecipeEntity>)

}