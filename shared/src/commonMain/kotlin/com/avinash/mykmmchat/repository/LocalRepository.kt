package com.avinash.mykmmchat.repository


import com.avinash.database.db.Message
import com.avinash.database.db.MessageQueries
import com.avinash.database.db.Post
import com.avinash.database.db.PostQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow

class LocalRepository(
    private val postQueries: PostQueries,
    private val messageQueries: MessageQueries
) {

    init {
        postQueries.createPostTable()
        messageQueries.createMessageTable()
        messageQueries.deleteMessages()
    }


    val messages = messageQueries.getMessages().asFlow().mapToList()

    val posts: Flow<List<Post>> = postQueries.getAllPosts().asFlow().mapToList()

    fun deletePosts() = postQueries.deleteAllPosts()

    fun insertPosts(posts: List<Post>) {
        postQueries.transaction {
            posts.forEach { post ->
                postQueries.upsertPost(post)
            }
        }
    }

    fun insertMessages(messages: List<Message>) {
        messageQueries.transaction {
            messages.forEach { message ->
                messageQueries.upsertMessage(message)
            }
        }
    }

    fun insertMessage(message: Message) {
        messageQueries.upsertMessage(message)

    }

    fun deleteMessages() {
        messageQueries.deleteMessages()
    }
}