package com.kishorramani.ktorwithjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kishorramani.ktorwithjetpackcompose.client.KtorClient
import com.kishorramani.ktorwithjetpackcompose.model.Post
import com.kishorramani.ktorwithjetpackcompose.ui.theme.KtorWithJetpackComposeTheme
import com.kishorramani.ktorwithjetpackcompose.utils.Constants.Companion.toJson
import com.kishorramani.ktorwithjetpackcompose.utils.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var posts by remember { mutableStateOf(emptyList<Post>()) }
            LaunchedEffect(Unit) {
                //Get request ["https://jsonplaceholder.org/posts"]
                posts = KtorClient().getPost()
                Timber.e("API: GET Request: ${posts.toJson()}")

                //Get Request With parameter ["https://jsonplaceholder.typicode.com/comments"]
                val commentFromPostId = KtorClient().getCommentsFromPostId(postId = 1)
                Timber.e("API: Get Request With parameter: ${commentFromPostId.toJson()}")

                //Post request ["https://jsonplaceholder.typicode.com/posts"]
                val postPost = KtorClient().postPost(
                    Post(
                        id = 1,
                        userId = 1,
                        title = "Title",
                        body = "Body"
                    )
                )
                Timber.e("API: Post Request: ${postPost.toJson()}")

                //Patch request ["https://jsonplaceholder.typicode.com/posts/1"]
                //Patch request use to update only specific field where put used to update the whole object
                val patchPost = KtorClient().patchPost(
                    id = 1,
                    map = mapOf("title" to "Title")
                )
                Timber.e("API: Patch Request: ${patchPost.toJson()}")

                //Put request ["https://jsonplaceholder.typicode.com/posts"]
                val putPost = KtorClient().putPost(
                    id = 1,
                    post = Post(
                        id = 1,
                        userId = 1,
                        title = "Title",
                        body = "Body"
                    )
                )
                Timber.e("API: Put Request: ${putPost.toJson()}")

                val deletePost = KtorClient().deletePost(id = 1)
                Timber.e("API: Delete Request: ${deletePost.status}")
            }

            KtorWithJetpackComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainContent(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        list = posts
                    )
                }
            }
        }
    }
}

@Composable
fun MainContent(modifier: Modifier = Modifier, list: List<Post>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(list) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                Text(text = it.id.toString())
                Spacer(Modifier.height(8.dp))
                Text(text = it.title, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text(text = it.body, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KtorWithJetpackComposeTheme {
        Greeting("Android")
    }
}