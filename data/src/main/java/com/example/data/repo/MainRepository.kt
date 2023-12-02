package com.example.data.repo

import com.example.data.remote.MainApiService
import com.example.data.response.UserDataResponse
import javax.inject.Inject

class MainRepository @Inject constructor(private val mainApiService: MainApiService){
    fun getUserInfo ()= UserDataResponse.getDataResponse {
        mainApiService.getUserInfo()
    }
}