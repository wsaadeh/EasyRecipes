package com.saadeh.easyrecipes.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.easyrecipes.EasyRecipeApplication
import com.saadeh.easyrecipes.common.model.RecipeDto
import com.saadeh.easyrecipes.list.data.RecipeListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class RecipeListViewModel(
    private val repository: RecipeListRepository
) : ViewModel() {
    private val _uiRecipeList = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiRecipeList: StateFlow<List<RecipeDto>> = _uiRecipeList

    init {
        fetchRecipeList()
    }

    private fun fetchRecipeList() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRecipeList()
            if (response.isSuccess) {
                _uiRecipeList.value =
                    (response.getOrNull()?: emptyList())
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiRecipeList.value = listOf(
                        RecipeDto(
                            id = 0,
                            title = "No internet Connection",
                            image = "",
                            summary = "No internet Connection"
                        )
                    )
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val application = checkNotNull(extras[APPLICATION_KEY])
                return RecipeListViewModel(
                    repository = (application as EasyRecipeApplication).repository
                ) as T
            }
        }
    }
}