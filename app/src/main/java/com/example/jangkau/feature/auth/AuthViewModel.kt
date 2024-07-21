package com.example.jangkau.feature.auth

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.Resource
import com.example.domain.model.Auth
import com.example.domain.model.Login
import com.example.domain.model.User
import com.example.domain.usecase.auth.LoginUseCase
import com.example.domain.usecase.user.GetUserUseCase
import com.example.jangkau.State
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthViewModel(
    private val loginUseCase : LoginUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State<Login>>()
    val state : MutableLiveData<State<Login>> = _state

//    private val _userLoggedIn = MutableLiveData<Boolean>()
//    val userLoggedIn: MutableLiveData<Boolean> = _userLoggedIn

    fun loginUser(username : String, password : String){
        loginUseCase(
            Auth(username = username, password = password)
        ).onEach { result ->
            when(result){
                is Resource.Error -> {
                    Log.e("LoginUser", "Error: ${result.message}")
                    _state.value = State.Error(result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = State.Loading
                }
                is Resource.Success -> {
                    _state.value = result.data?.let { State.Success(it) }
                }
            }
        }.launchIn(viewModelScope)
    }



}