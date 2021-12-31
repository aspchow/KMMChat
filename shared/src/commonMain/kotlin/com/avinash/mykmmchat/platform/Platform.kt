package com.avinash.mykmmchat.platform

import com.avinash.database.db.AppDatabase
import io.ktor.client.*
import kotlinx.coroutines.CoroutineDispatcher


internal expect val Main: CoroutineDispatcher

internal expect val Background: CoroutineDispatcher

internal expect val httpClient: HttpClient


expect class DatabaseBuilder {
    fun getDatabase(): AppDatabase
}

