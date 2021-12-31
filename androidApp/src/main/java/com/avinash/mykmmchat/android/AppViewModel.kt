package com.avinash.mykmmchat.android

import androidx.lifecycle.ViewModel
import com.avinash.mykmmchat.repository.Repository

class AppViewModel(private val repository: Repository) : ViewModel() {
    val posts = repository.posts

    val messages = repository.messages

    fun downloadPosts() = repository.downloadPosts()
    fun deletePosts() {
        repository.deletePosts()
    }

    fun sendNewMessage(newMessage: String) {
        repository.sendMessage(newMessage,"android")
    }
}