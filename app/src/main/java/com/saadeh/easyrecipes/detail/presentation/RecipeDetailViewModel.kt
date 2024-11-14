package com.saadeh.easyrecipes.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.easyrecipes.EasyRecipeApplication
import com.saadeh.easyrecipes.common.data.RetrofitClient
import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.detail.data.RecipeDetailRepository
import com.saadeh.easyrecipes.detail.data.remote.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class RecipeDetailViewModel(
    private val repository: RecipeDetailRepository
): ViewModel() {
    private val _uiRecipeDetailById = MutableStateFlow<RecipeDto?>(null)
    val uiRecipeDetailById: StateFlow<RecipeDto?> = _uiRecipeDetailById

    fun fetchRecipeById(recipeId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRecipeById(recipeId)
            if (response.isSuccess){
                _uiRecipeDetailById.value = response.getOrNull()
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiRecipeDetailById.value = RecipeDto(
                        id = 0,
                        title = "No internet connection",
                        image = "",
                        summary = "No internet connection",
                    )
                }
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofitInstance.create(DetailService::class.java)
                val application = checkNotNull(extras[APPLICATION_KEY])
                return RecipeDetailViewModel(repository = (application as EasyRecipeApplication).repDetail) as T
            }
        }
    }
}