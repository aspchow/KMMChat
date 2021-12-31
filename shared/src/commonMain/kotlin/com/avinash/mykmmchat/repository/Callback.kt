package com.avinash.mykmmchat.repository

sealed class Callback {
    object Success : Callback()
    object Failure : Callback()
}