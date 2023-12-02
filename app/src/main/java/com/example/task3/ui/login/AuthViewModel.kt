package com.example.task3.ui.login

import com.example.data.repo.AuthRepository
import com.example.domain.model.responses.ApiResponse
import com.example.domain.model.request.AuthUser
import com.example.domain.model.responses.LoginResponse
import com.example.task3.ui.BaseViewModel
import com.example.task3.ui.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository):BaseViewModel() {
    private val _loginResponse= MutableSharedFlow<ApiResponse<LoginResponse>>()
    val loginResponse : SharedFlow<ApiResponse<LoginResponse>> get() = _loginResponse
    fun login(authUser: AuthUser, coroutinesErrorHandler: CoroutinesErrorHandler)= baseRequest(
        _loginResponse,coroutinesErrorHandler
    ){
        authRepository.login(authUser)
    }

}
/*
 fun login(auth: Auth, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(auth)
    }
 */