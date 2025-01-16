package com.example.flickrsearch.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class FlickrResponse(
    val items: List<FlickrItem>
)

data class FlickrItem(
    val title: String,
    val media: FlickrMedia,
    @SerializedName("Published")
    val publishedDate: String,
    @SerializedName("description")
    val descriptionHtml: String,
    val author: String,
) : Serializable

data class FlickrMedia(
    @SerializedName("m") val imageUrl: String
) : Serializable