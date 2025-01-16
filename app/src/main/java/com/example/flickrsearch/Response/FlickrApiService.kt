package com.example.flickrsearch.response

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApiService {
    @GET("services/feeds/photos_public.gne?format=json&nojsoncallback=1")
    suspend fun searchImages(@Query("tags") tags: String): Response<FlickrResponse>
}