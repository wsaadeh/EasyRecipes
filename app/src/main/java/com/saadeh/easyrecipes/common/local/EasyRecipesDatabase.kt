package com.saadeh.easyrecipes.common.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database([RecipeEntity::class], version = 1)
abstract class EasyRecipesDatabase: RoomDatabase() {
    abstract fun getRecipeDao():EasyRecipeDao
}