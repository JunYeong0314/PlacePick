package com.jyproject.domain

import java.io.IOException

sealed class State<out T>(val _data: T?){
    data object Loading: State<Nothing>(_data = null)

    data class Success<T>(val data: T): State<T>(_data = data)

    data class Error(val exception: Throwable): State<Nothing>(_data = null)
}