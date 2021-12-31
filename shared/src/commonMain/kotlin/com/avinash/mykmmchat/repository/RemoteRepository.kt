package com.avinash.mykmmchat.repository

import com.avinash.mykmmchat.networking.ApiUtility

class RemoteRepository(private val apiUtility: ApiUtility) {
    suspend fun getPosts() = apiUtility.getPosts()
}