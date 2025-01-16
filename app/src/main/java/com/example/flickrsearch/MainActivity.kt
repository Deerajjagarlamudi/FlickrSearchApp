package com.example.flickrsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flickrsearch.ui.theme.FlickrSearchTheme
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import androidx.compose.ui.semantics.*
import com.example.flickrsearch.response.FlickrItem

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrSearchTheme {
                val viewModel: FlickrViewModel = viewModel()
                FlickrSearchApp(viewModel)
            }
        }
    }
}

@Composable
fun FlickrSearchApp(viewModel: FlickrViewModel) {
    val flickrItems by viewModel.flickrItems.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val configuration = LocalConfiguration.current

    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
        PortraitLayout(viewModel, flickrItems, isLoading)
    } else {
        LandscapeLayout(viewModel, flickrItems, isLoading)
    }
}

@Composable
fun PortraitLayout(viewModel: FlickrViewModel, flickrItems: List<FlickrItem>, isLoading: Boolean) {
    Column {
        SearchBar(viewModel)
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else {
            ImageGrid(flickrItems)
        }
    }
}

@Composable
fun LandscapeLayout(viewModel: FlickrViewModel, flickrItems: List<FlickrItem>, isLoading: Boolean) {
    Row {
        Column(modifier = Modifier.weight(1f)) {
            SearchBar(viewModel)
        }
        Column(modifier = Modifier.weight(2f)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else {
                ImageGrid(flickrItems)
            }
        }
    }
}

@Composable
fun SearchBar(viewModel: FlickrViewModel) {
    var query by remember { mutableStateOf("") }

    TextField(
        value = query,
        onValueChange = {
            query = it
            viewModel.fetchImages(query)
        },
        label = { Text("Search Flickr") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .semantics { contentDescription = "Search bar for Flickr images" }
    )
}

@Composable
fun ImageGrid(flickrItems: List<FlickrItem>) {
    LazyVerticalGrid(columns = GridCells.Adaptive(150.dp)) {
        items(flickrItems.size) { index ->
            val item = flickrItems[index]
            ImageCard(item)
        }
    }
}

@Composable
fun ImageCard(item: FlickrItem) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("title", item.title)
                    putExtra("imageUrl", item.media.imageUrl)
                    putExtra("description", item.descriptionHtml)
                    putExtra("author", item.author)
                    putExtra("publishedDate", item.publishedDate)
                }
                context.startActivity(intent)
            }
            .semantics { contentDescription = "Image card for ${item.title}" }
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = item.media.imageUrl),
                contentDescription = "Thumbnail of ${item.title}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Text(
                text = item.title,
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
/*
@Preview(showBackground = true)
@Composable
fun FlickrSearchbarPreview(){
    FlickrSearchApp()
}*/