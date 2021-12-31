package com.avinash.mykmmchat.model

import kotlinx.serialization.Serializable

@Serializable
data class PostApiResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
)