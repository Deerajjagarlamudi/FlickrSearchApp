package com.example.flickrsearch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flickrsearch.response.FlickrApiService
import com.example.flickrsearch.response.FlickrItem
import com.example.flickrsearch.response.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class FlickrViewModel(private val apiService: FlickrApiService) : ViewModel() {
    private val flickr_Items = MutableStateFlow<List<FlickrItem>>(emptyList())
    val flickrItems: StateFlow<List<FlickrItem>> = flickr_Items

    private val is_Loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = is_Loading

    fun fetchImages(query: String) {
        viewModelScope.launch {
            val formattedQuery = query.trim().replace("\\s+".toRegex(), "")
            Log.d("FlickrViewModel", "Fetching images with tags: $formattedQuery")
            is_Loading.value = true
            try {
                val response = RetrofitInstance.api.searchImages(query)
                if (response.isSuccessful) {
                    flickr_Items.value = response.body()?.items ?: emptyList()
                }
            } catch (e: HttpException) {
                flickr_Items.value = emptyList()
            } catch (e: IOException) {
                flickr_Items.value = emptyList()
            } finally {
                is_Loading.value = false
            }
        }
    }

}
