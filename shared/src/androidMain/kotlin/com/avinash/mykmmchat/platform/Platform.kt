package com.avinash.mykmmchat.platform

import android.content.Context
import com.avinash.database.db.AppDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


internal actual val Main: CoroutineDispatcher
    get() = Dispatchers.Main


internal actual val Background: CoroutineDispatcher
    get() = Main


internal actual val httpClient: HttpClient = HttpClient(Android) {
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }

    install(JsonFeature) {
        val json = kotlinx.serialization.json.Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
        serializer = KotlinxSerializer(json)
    }
}

actual class DatabaseBuilder(private val context: Context) {
    actual fun getDatabase(): AppDatabase {
        val driver = AndroidSqliteDriver(AppDatabase.Schema, context, "Appdatabase.db")
        return AppDatabase(driver)
    }
}

