package com.dracco.koinusergithub.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dracco.koinusergithub.api.model.response.SearchResponse
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.model.type.DataState
import com.dracco.koinusergithub.repository.SearchRepository
import com.dracco.koinusergithub.repository.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel(
private val repository: SearchRepository
) : ViewModel() {

    val error: LiveData<String>
    get() = _error

    private val _error = MutableLiveData<String>()

    val appState: LiveData<DataState>
    get() = _appState

    private val _appState = MutableLiveData<DataState>()

    val search: LiveData<SearchResponse>
    get() = _search

    private val _search = MutableLiveData<SearchResponse>()

    fun getSearch(q: String){
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = repository.getSearch(q)
            Log.e("UserViewModel", "getSearch: $result")
            result.fold(
                onSuccess = {
                    _search.value = it
                    _appState.value = DataState.Success
                },
                onFailure = {
                    it.message?.let { e ->
                        _error.value = e
                    } ?: run {
                        _error.value = "Erro Interno"
                    }
                    _appState.value = DataState.Error
                }
            )
        }
    }

}