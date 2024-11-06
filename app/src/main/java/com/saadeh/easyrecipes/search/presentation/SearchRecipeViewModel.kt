package com.saadeh.easyrecipes.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.saadeh.easyrecipes.common.data.RetrofitClient
import com.saadeh.easyrecipes.common.model.SearchRecipeDto
import com.saadeh.easyrecipes.search.data.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchRecipeViewModel(
    private val searchService: SearchService
): ViewModel() {
    private val _uiSearchQuery = MutableStateFlow<List<SearchRecipeDto>>(emptyList())
    val uiSearchQuery: StateFlow<List<SearchRecipeDto>> = _uiSearchQuery

    fun fetchSearchQuery(query: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = searchService.searchRecipes(query)
            if (response.isSuccessful){
                _uiSearchQuery.value = response.body()?.results ?: emptyList()
            } else {
                Log.d("SearchRecipesScreen", "Request error :: ${response.errorBody()}")
            }
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofitInstance.create(SearchService::class.java)
                return SearchRecipeViewModel(
                    searchService
                ) as T
            }
        }
    }

}