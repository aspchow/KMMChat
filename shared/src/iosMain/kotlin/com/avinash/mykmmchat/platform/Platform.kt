package com.avinash.mykmmchat.platform

//iOS
import platform.Foundation.*
import com.avinash.database.db.AppDatabase
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import io.ktor.client.*
import io.ktor.client.engine.ios.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import platform.darwin.dispatch_queue_t
import kotlin.coroutines.CoroutineContext


internal actual val Main: CoroutineDispatcher =
    NsQueueDispatcher(dispatch_get_main_queue())

internal actual val Background: CoroutineDispatcher = Main


internal class NsQueueDispatcher(
    private val dispatchQueue: dispatch_queue_t
) : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        dispatch_async(dispatchQueue) {
            block.run()
        }
    }
}


internal actual val httpClient: HttpClient = HttpClient(Ios) {
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

actual class DatabaseBuilder {
    actual fun getDatabase(): AppDatabase {
        val driver = NativeSqliteDriver(AppDatabase.Schema, "Appdatabase.db")
        return AppDatabase(driver)
    }
}

