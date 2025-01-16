package com.example.flickrsearch

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val title = intent.getStringExtra("title") ?: "No Title"
        val imageUrl = intent.getStringExtra("imageUrl") ?: ""
        val description = intent.getStringExtra("description") ?: "No Description"
        val author = intent.getStringExtra("author") ?: "Unknown Author"
        val publishedDate = intent.getStringExtra("publishedDate") ?: "No Date"

        setContent {
            DetailScreen(
                title = title,
                imageUrl = imageUrl,
                description = description,
                author = author,
                publishedDate = publishedDate
            )
        }
    }
}
@Composable
fun DetailScreen(
    title: String,
    imageUrl: String,
    description: String,
    author: String,
    publishedDate: String
) {
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Image of $title",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Author: $author", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Published: $publishedDate", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Share Button
            Button(
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_TEXT,
                            """
                            Check out this image from Flickr:
                            
                            Title: $title
                            Author: $author
                            Published: $publishedDate
                            Description: $description
                            
                            View Image: $imageUrl
                            """.trimIndent()
                        )
                    }
                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Share")
            }
        }
    } else {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = title,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Author: $author", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Published: $publishedDate", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Description: $description", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))

                // Share Button
                Button(
                    onClick = {
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(
                                Intent.EXTRA_TEXT,
                                """
                                Check out this image from Flickr:
                                
                                Title: $title
                                Author: $author
                                Published: $publishedDate
                                Description: $description
                                
                                View Image: $imageUrl
                                """.trimIndent()
                            )
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Share")
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun DetailScreenPreview(){
    DetailScreen(item = "")
}*/