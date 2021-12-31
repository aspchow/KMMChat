package com.avinash.mykmmchat.networking

import com.avinash.mykmmchat.model.MessageApiResponse
import com.avinash.mykmmchat.platform.httpClient
import com.avinash.mykmmchat.model.PostApiResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ApiUtility {

    private val baseUrl = "http://192.168.0.181:8080"

    suspend fun getPosts() = flow {
        val posts = httpClient.request<List<PostApiResponse>> {
            url("https://jsonplaceholder.typicode.com/posts")
            method = HttpMethod.Get
        }
        emit(Result.success(posts))
    }.catch {
        emit(Result.failure(it))
    }


    suspend fun getMessages() = httpClient.handle<List<MessageApiResponse>>(baseUrl = baseUrl) {
        endUrl("chat")
    }


}


inline infix fun StringBuilder.endUrl(endUrl: String) {
    append("/$endUrl")
}

suspend inline fun <reified T> HttpClient.handle(
    baseUrl: String,
    httpMethod: HttpMethod = HttpMethod.Get,
    crossinline params: StringBuilder.() -> Unit = {},
) = flow {
    val result = request<T> {

        val baseUrlBuilder = StringBuilder(baseUrl)
        baseUrlBuilder.params()
        url(baseUrlBuilder.toString())
        method = httpMethod
    }
    emit(Result.success(result))
}.catch {
    emit(Result.failure(it))
}