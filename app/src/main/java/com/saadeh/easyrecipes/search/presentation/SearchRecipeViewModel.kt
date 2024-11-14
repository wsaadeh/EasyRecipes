package com.saadeh.easyrecipes.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.easyrecipes.EasyRecipeApplication
import com.saadeh.easyrecipes.common.data.RetrofitClient
import com.saadeh.easyrecipes.common.model.SearchRecipeDto
import com.saadeh.easyrecipes.search.data.RecipeSearchRepository
import com.saadeh.easyrecipes.search.data.remote.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SearchRecipeViewModel(
    private val repository: RecipeSearchRepository
): ViewModel() {
    private val _uiSearchQuery = MutableStateFlow<List<SearchRecipeDto>>(emptyList())
    val uiSearchQuery: StateFlow<List<SearchRecipeDto>> = _uiSearchQuery

    fun fetchSearchQuery(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRecipeBySearch(query)
            if (response.isSuccess){
                _uiSearchQuery.value = response.getOrNull()!!
            } else {
                val ex = response.exceptionOrNull()
                if (ex is UnknownHostException){
                    _uiSearchQuery.value = listOf(
                        SearchRecipeDto(
                            id = 0,
                            title = "No internet connection",
                            image = ""
                        )
                    )
                }
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofitInstance.create(SearchService::class.java)
                val application = checkNotNull(extras[APPLICATION_KEY])
                return SearchRecipeViewModel(
                    repository = (application as EasyRecipeApplication).repSearch
                ) as T
            }
        }
    }

}