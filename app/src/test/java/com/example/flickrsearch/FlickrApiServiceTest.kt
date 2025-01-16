package com.example.flickrsearch

import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.flickrsearch.response.FlickrApiService

class FlickrApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: FlickrApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlickrApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `searchImages makes correct API call`() = runBlocking {
        val mockResponse = MockResponse()
            .setBody(
                """
                {
                  "items": [
                    {
                      "title": "Test Image",
                      "media": {"m": "https://example.com/image.jpg"},
                      "description": "Test Description",
                      "published": "2025-01-16",
                      "author": "Test Author"
                    }
                  ]
                }
                """
            )
        mockWebServer.enqueue(mockResponse)

        val response = apiService.searchImages("test")
        val items = response.body()?.items

        val request = mockWebServer.takeRequest()
        assertEquals("/services/feeds/photos_public.gne?format=json&nojsoncallback=1&tags=test", request.path)
        assertEquals(1, items?.size)
        assertEquals("Test Image", items?.get(0)?.title)
    }
}