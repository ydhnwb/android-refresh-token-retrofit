package com.ydhnwb.android_refresh_token_example.domain.common.wrapper

sealed class Either <out T : Any, out U : Any> {
    data class Success <T: Any>(val data : T) : Either<T, Nothing>()
    data class Error <U : Any>(val err: U) : Either<Nothing, U>()
}