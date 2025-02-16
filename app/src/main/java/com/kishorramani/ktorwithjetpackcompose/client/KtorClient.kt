package com.kishorramani.ktorwithjetpackcompose.client

import com.kishorramani.ktorwithjetpackcompose.model.Comment
import com.kishorramani.ktorwithjetpackcompose.model.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class KtorClient {

    // https://jsonplaceholder.typicode.com/posts
    companion object {
        fun getClient(): HttpClient = HttpClient {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }

            install(HttpTimeout) {
                socketTimeoutMillis = 3000
                requestTimeoutMillis = 3000
                connectTimeoutMillis = 3000
            }

            install(DefaultRequest) {
                url {
                    host = "jsonplaceholder.typicode.com"
                    protocol = URLProtocol.HTTPS
                    headers {
                        append(HttpHeaders.Authorization, "FakeAuthorizations")
                    }
                }
            }

            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.ALL
            }
        }
    }

    //get request ["https://jsonplaceholder.typicode.com/posts"]
    suspend fun getPost(): List<Post> {
        return getClient().get("/posts").body<List<Post>>()
    }

    //get request with parameter ["https://jsonplaceholder.typicode.com/comments?postId=1"]
    suspend fun getCommentsFromPostId(postId: Int): List<Comment> {
        return getClient().get {
            url {
                path("/comments")
                parameter("postId", postId)
            }
        }.body<List<Comment>>()
    }

    //Post request ["https://jsonplaceholder.typicode.com/posts"]
    suspend fun postPost(post: Post): Post {
        return getClient().post {
            url {
                path("/posts")
            }
            contentType(ContentType.Application.Json)
            setBody(post)
        }.body<Post>()
    }

    //Patch request use to update only specific field where put used to update the whole object
    //Patch request ["https://jsonplaceholder.typicode.com/posts/1"]
    suspend fun patchPost(id: Int, map: Map<String, String>): Post {
        return getClient().patch {
            url {
                path("/posts/$id")
                contentType(ContentType.Application.Json)
                setBody(map)
            }
        }.body<Post>()
    }

    //Patch request use to update only specific field where put used to update the whole object
    //Patch request ["https://jsonplaceholder.typicode.com/posts/1"]
    suspend fun putPost(id: Int, post: Post): Post {
        return getClient().put {
            url {
                path("/posts/$id")
                contentType(ContentType.Application.Json)
                setBody(post)
            }
        }.body<Post>()
    }

    //Delete request ["https://jsonplaceholder.typicode.com/posts/1"]
    suspend fun deletePost(id: Int): HttpResponse {
        return getClient().delete {
            url {
                path("/posts/$id")
            }
        }
    }
}