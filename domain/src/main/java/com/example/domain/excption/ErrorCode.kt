package com.example.domain.excption

enum class ErrorCode (val code: Int){
    NOT_FOUND(404),
    REQUEST_TIMEOUT(408),
    FAILD_AUTHANTICATE(401)
}