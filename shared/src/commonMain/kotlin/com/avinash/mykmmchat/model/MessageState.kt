package com.avinash.mykmmchat.model

sealed interface MessageState{
    object MessageSent : MessageState
    object MessageReceived : MessageState
}