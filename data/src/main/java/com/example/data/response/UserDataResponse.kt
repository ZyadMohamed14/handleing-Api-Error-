package com.example.data.response

import com.example.domain.model.responses.ApiResponse
import com.example.domain.excption.CustomException
import com.example.domain.excption.ErrorCode
import com.example.domain.model.responses.ErrorResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import kotlinx.coroutines.withTimeoutOrNull
import okio.IOException
import retrofit2.Response

object UserDataResponse {
    fun<T> getDataResponse(call: suspend () -> Response<T>): Flow<ApiResponse<Any>> = flow {
        emit(ApiResponse.Loading)
        withTimeoutOrNull(20000){
            val response = call()
            try {
                if(response.isSuccessful){
                    response.body()?.let { data ->
                        emit(ApiResponse.Success(data))
                    }
                }else{
                   response.errorBody()?.let { error->
                       error.close()
                      val errorResponse:ErrorResponse= Gson().fromJson(error.charStream(),ErrorResponse::class.java)
                       emit(ApiResponse.Failure(errorResponse.message,errorResponse.code))

                   }
                }

            }catch (e:IOException){
                emit(ApiResponse.Failure(CustomException.NotFoundException(e.message?:e.toString()),ErrorCode.NOT_FOUND.code))
            }catch (e:IOException){
                emit(ApiResponse.Failure(CustomException.AuthanticationException(e.message?:e.toString()),ErrorCode.FAILD_AUTHANTICATE.code))
            }


        } ?: emit(ApiResponse.Failure(CustomException.RequestTimeoutException("Timeout! Please try again."), ErrorCode.REQUEST_TIMEOUT.code))

    }.flowOn(Dispatchers.IO)


//

}