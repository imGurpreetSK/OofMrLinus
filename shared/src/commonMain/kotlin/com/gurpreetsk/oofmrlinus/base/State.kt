package com.gurpreetsk.oofmrlinus.base

sealed interface State<T : Any?> {

    data class Loading<T>(
        val data: T? = null,
        val error: Throwable? = null
    ) : State<T>

    data class Success<T>(val data: T) : State<T>

    data class Error<T>(
        val error: Throwable,
        val data: T? = null
    ) : State<T>
}
