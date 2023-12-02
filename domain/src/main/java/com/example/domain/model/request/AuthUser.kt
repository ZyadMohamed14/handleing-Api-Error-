package com.example.domain.model.request

import com.google.gson.annotations.SerializedName

data class AuthUser(
    @SerializedName("email_address")
    val email: String,
    val password: String
)