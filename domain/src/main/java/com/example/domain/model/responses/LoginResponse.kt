package com.example.domain.model.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val token: String
)