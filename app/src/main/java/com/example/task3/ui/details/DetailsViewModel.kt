package com.example.task3.ui.details

import com.example.data.repo.MainRepository
import com.example.domain.model.responses.ApiResponse
import com.example.domain.model.responses.UserInfoResponse
import com.example.task3.ui.BaseViewModel
import com.example.task3.ui.CoroutinesErrorHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class DetailsViewModel@Inject constructor(
    private val mainRepository: MainRepository,
): BaseViewModel() {
    private val _userInfoResponse = MutableSharedFlow<ApiResponse<UserInfoResponse>>()
    val userInfoResponse = _userInfoResponse
    fun getUserInfo(coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _userInfoResponse,
        coroutinesErrorHandler,
    ) {
        mainRepository.getUserInfo()
    }
}