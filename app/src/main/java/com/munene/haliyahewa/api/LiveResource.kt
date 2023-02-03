package com.munene.haliyahewa.api

sealed class LiveResource<out T>(
    open val data: T? = null,
    open val errorMessage: String? = null
) {
    data class Loading<out T>(override val data: T?) : LiveResource<T>()
    data class Success<out T>(override val data: T?) : LiveResource<T>()
    data class Error<out T>(override val data: T?, override val errorMessage: String) :
        LiveResource<T>()

    companion object {
        fun <T> loading(data: T? = null) = Loading(data)
        fun <T> success(data: T? = null) = Success(data)
        fun <T> error(data: T? = null, message: String) = Error(data, message)
    }
}
