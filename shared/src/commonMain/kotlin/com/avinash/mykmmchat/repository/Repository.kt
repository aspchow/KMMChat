package com.avinash.mykmmchat.repository

import com.avinash.database.db.Message
import com.avinash.database.db.Post
import com.avinash.mykmmchat.model.MessageApiResponse
import com.avinash.mykmmchat.model.modelmapper.Mapper
import com.avinash.mykmmchat.utils.CFlow
import com.avinash.mykmmchat.utils.asCFlow
import com.avinash.mykmmchat.utils.handle
import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class Repository : KoinComponent {


    @NativeCoroutineScope
    private val coroutineScope: CoroutineScope = MainScope()

    private val localRepository: LocalRepository by inject()
    private val remoteRepository: RemoteRepository by inject()
    private val mapper: Mapper by inject()

    val posts: Flow<List<Post>> = localRepository.posts
    val messages: Flow<List<Message>> = localRepository.messages

    private lateinit var appSocket: AppSocket

    private val json = Json { ignoreUnknownKeys = true }

    init {
        initAppSocket()
    }

    private fun initAppSocket() {
        appSocket = AppSocket("ws://192.168.0.167:8080/chat")
        appSocket.stateListener = {
            println("New state: $it")

            if (it == AppSocket.State.CONNECTED) {

            }
        }

        appSocket.messageListener = { messageString ->
            coroutineScope.launch {

                val message =
                    json.decodeFromString<MessageApiResponse>(
                        messageString
                    )
                val message1: Message = mapper.getMessageFromDownloadedMessage(message, true)
                localRepository.insertMessage(message1)

                println("The received message is $messageString")

            }
        }
        appSocket.connect()
    }


    fun deletePosts() = localRepository.deletePosts()


    fun deleteMessages() = localRepository.deleteMessages()

    fun downloadPosts(): Flow<DownloadState> = flow {
        val response = remoteRepository.getPosts().first()
        response.handle(
            onSuccess = { downloadedPosts ->
                val posts = mapper.getPostsFromDownloadedPosts(downloadedPosts)
                localRepository.insertPosts(posts)
                emit(DownloadState.Success)
            },
            onFailure = {
                emit(DownloadState.Failure)
            }
        )
    }

    fun sendMessage(newMessage: String,userName : String) {
        val message  = "{   \"messageText\": \"$newMessage\",         \"userName\": \"$userName\"    }"
        appSocket.send(message)
    }



}


sealed class DownloadState {
    object Success : DownloadState()
    object Failure : DownloadState()
}