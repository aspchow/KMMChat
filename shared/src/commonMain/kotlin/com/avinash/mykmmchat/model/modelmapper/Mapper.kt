package com.avinash.mykmmchat.model.modelmapper

import com.avinash.database.db.Message
import com.avinash.database.db.Post
import com.avinash.mykmmchat.model.MessageApiResponse
import com.avinash.mykmmchat.model.PostApiResponse

class Mapper {
    fun getPostsFromDownloadedPosts(downloadedPosts: List<PostApiResponse>): List<Post> =
        downloadedPosts.map { downloadedPost ->
            Post(
                id = downloadedPost.id.toLong(),
                userId = downloadedPost.userId.toLong(),
                title = downloadedPost.title,
                body = downloadedPost.body
            )
        }

    fun getMessageFromDownloadedMessage(
        message: MessageApiResponse,
        isMessageReceived: Boolean
    ): Message = Message(
        messageId = message.messageId,
        userName = message.userName,
        messageText = message.messageText,
        creationTime = message.createdTime,
        isMessageReceived = isMessageReceived
    )
}