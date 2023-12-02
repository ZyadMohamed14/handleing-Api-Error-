package com.example.domain.model.responses

import com.google.gson.annotations.SerializedName

data class UserInfoResponse(
    @SerializedName("data")
    val userInfo: UserInfo,
    val message: String
)