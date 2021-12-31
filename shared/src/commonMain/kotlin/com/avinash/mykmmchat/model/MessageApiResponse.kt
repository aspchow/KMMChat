package com.avinash.mykmmchat.model

import kotlinx.serialization.Serializable

@Serializable
data class MessageApiResponse(
    val messageId: String,
    val userName: String,
    val messageText: String,
    val createdTime: Long,
)