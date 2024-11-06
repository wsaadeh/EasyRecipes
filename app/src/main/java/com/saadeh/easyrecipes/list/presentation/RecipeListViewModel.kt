package com.saadeh.easyrecipes.list.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.easyrecipes.common.data.RetrofitClient
import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.common.model.RecipeResponse
import com.saadeh.easyrecipes.list.data.ListService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecipeListViewModel(
    private val listService: ListService
) : ViewModel() {
    private val _uiRecipeList = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiRecipeList: StateFlow<List<RecipeDto>> = _uiRecipeList

    init {
        fetchRecipeList()
    }

    private fun fetchRecipeList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = listService.getRandom()
            if (response.isSuccessful) {
                _uiRecipeList.value =
                    (response.body()?.recipes ?: emptyList())
            } else {
                Log.d("RecipeScreen", "Request error :: ${response.errorBody()}")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val listService = RetrofitClient.retrofitInstance.create(ListService::class.java)
                return RecipeListViewModel(
                    listService
                ) as T
            }
        }
    }
}