package com.saadeh.easyrecipes.detail.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.easyrecipes.common.data.RetrofitClient
import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.detail.data.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    private val detailService: DetailService
): ViewModel() {
    private val _uiRecipeDetailById = MutableStateFlow<RecipeDto?>(null)
    val uiRecipeDetailById: StateFlow<RecipeDto?> = _uiRecipeDetailById

    fun fetchRecipeById(recipeId: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = detailService.getRecipeInformation(recipeId)
            if (response.isSuccessful){
                _uiRecipeDetailById.value = response.body()
            } else {
                Log.d("RecipeDetailScreen", "Request error :: ${response.errorBody()}")
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService = RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return RecipeDetailViewModel(detailService) as T
            }
        }
    }
}