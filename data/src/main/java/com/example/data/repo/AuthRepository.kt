package com.example.data.repo

import com.example.data.remote.AuthApiService
import com.example.data.response.UserDataResponse
import com.example.domain.model.request.AuthUser
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authApiService: AuthApiService) {
    fun login(auth: AuthUser)= UserDataResponse.getDataResponse {
        authApiService.login(auth)
    }

}
