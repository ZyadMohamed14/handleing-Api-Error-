package com.example.data.remote

import com.example.domain.model.responses.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("user/info")
    suspend fun getUserInfo(): Response<UserInfoResponse>
}