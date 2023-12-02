package com.example.task3.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel :ViewModel() {
    private var mJob: Job?= null
    protected fun <T> baseRequest(sharedFlow: MutableSharedFlow<T>, errorHandler: CoroutinesErrorHandler, request: () -> Flow<T>) {
        mJob = viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                errorHandler.onError(error.localizedMessage ?: "Error occured! Please try again.")
            }
        }){
            request().collect {
                withContext(Dispatchers.Main) {
                    sharedFlow.emit(it)
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        mJob?.let {
            if(it.isActive)it.cancel()
        }
    }
}
interface CoroutinesErrorHandler {
    fun onError(message:String)
}
