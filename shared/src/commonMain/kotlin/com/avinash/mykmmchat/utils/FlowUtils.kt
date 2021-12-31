package com.avinash.mykmmchat.utils

import com.avinash.mykmmchat.platform.Main
import com.squareup.sqldelight.db.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.*

fun <T> getFlow(job: suspend FlowCollector<T>.() -> Unit) = flow {
    job()
}


@OptIn(ObsoleteCoroutinesApi::class)
fun <T> ConflatedBroadcastChannel<T>.wrap(): CFlow<T> = CFlow(asFlow())

fun <T> Flow<T>.asCFlow(): CFlow<T> = CFlow(this)

class CFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun collect(block: (T) -> Unit): Closeable {
        val job = Job(/*ConferenceService.coroutineContext[Job]*/)
        onEach {
            block(it)
        }.launchIn(CoroutineScope(Main + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}