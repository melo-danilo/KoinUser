package com.dracco.koinusergithub.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dracco.koinusergithub.api.model.response.Repository
import com.dracco.koinusergithub.api.model.response.SearchResponse
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.model.response.UserDetail
import com.dracco.koinusergithub.api.model.type.DataState
import com.dracco.koinusergithub.repository.SearchRepository
import com.dracco.koinusergithub.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    private val searchRepository: SearchRepository
) : ViewModel() {

    val error: LiveData<String>
        get() = _error

    private val _error = MutableLiveData<String>()

    val appState: LiveData<DataState>
        get() = _appState

    private val _appState = MutableLiveData<DataState>()

    val userList: LiveData<List<User>>
        get() = _userList

    private val _userList = MutableLiveData<List<User>>()

    val user: LiveData<UserDetail>
        get() = _user

    private val _user = MutableLiveData<UserDetail>()

    val repos: LiveData<List<Repository>>
        get() = _repos

    private val _repos = MutableLiveData<List<Repository>>()

    val search: LiveData<SearchResponse>
        get() = _search

    private val _search = MutableLiveData<SearchResponse>()

    fun getUsers(){
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = repository.getUsers()
            Log.e("UserViewModel", "getUsers: $result")
            result.fold(
                onSuccess = {
                    _userList.value = it
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

    fun getUserByUsername(userName: String){
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = repository.getUserByUsername(userName)
            Log.e("UserViewModel", "getUserByUsername: $result")
            result.fold(
                onSuccess = {
                    _user.value = it
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

    fun getReposByUsername(userName: String){
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = repository.getReposByUsername(userName)
            Log.e("UserViewModel", "getUserByUsername: $result")
            result.fold(
                onSuccess = {
                    _repos.value = it
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

    fun getSearch(q: String){
        _appState.postValue(DataState.Loading)
        viewModelScope.launch {
            val result = searchRepository.getSearch(q)
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