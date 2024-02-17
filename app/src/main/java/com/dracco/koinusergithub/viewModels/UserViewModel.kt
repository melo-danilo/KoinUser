package com.dracco.koinusergithub.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.api.model.type.DataState
import com.dracco.koinusergithub.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
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

}