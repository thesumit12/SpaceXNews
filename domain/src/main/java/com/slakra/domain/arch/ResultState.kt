package com.slakra.domain.arch

sealed class ResultState<out T> {
    data class Success<T>(val data: T): ResultState<T>()
    object InvalidData: ResultState<Nothing>()
    data class Error(val errorMsg: String): ResultState<Nothing>()
    data class NetworkException(val exceptionMsg: String): ResultState<Nothing>()
    sealed class HttpErrors: ResultState<Nothing>() {
        data class ResourceForbidden(val exceptionMsg: String) : HttpErrors()
        data class ResourceNotFound(val exceptionMsg: String) : HttpErrors()
        data class InternalServerError(val exceptionMsg: String) : HttpErrors()
        data class BadGateWay(val exceptionMsg: String) : HttpErrors()
        data class ResourceRemoved(val exceptionMsg: String) : HttpErrors()
        data class RemovedResourceFound(val exceptionMsg: String) : HttpErrors()
    }
}
