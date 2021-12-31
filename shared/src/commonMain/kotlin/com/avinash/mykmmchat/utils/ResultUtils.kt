package com.avinash.mykmmchat.utils

suspend fun <T> Result<T>.handle(
    onComplete: suspend () -> Unit = {},
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (Throwable) -> Unit
) {
    onComplete()

    if (isSuccess) {
        onSuccess(getOrNull()!!)
    } else {
        onFailure(exceptionOrNull()!!)
    }
}