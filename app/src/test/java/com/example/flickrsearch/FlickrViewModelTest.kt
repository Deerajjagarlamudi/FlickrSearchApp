import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.flickrsearch.FlickrViewModel
import com.example.flickrsearch.response.FlickrApiService
import com.example.flickrsearch.response.FlickrItem
import com.example.flickrsearch.response.FlickrMedia
import com.example.flickrsearch.response.FlickrResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class FlickrViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockApiService = mockk<FlickrApiService>() // Mocked API Service
    private lateinit var viewModel: FlickrViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher()) // Set up for testing
        viewModel = FlickrViewModel(mockApiService)  // Provide the mocked API service
    }

    @Test
    fun `fetchImages updates flickrItems when successful`() = runBlocking {
        // Prepare mock response from API
        val mockResponse = FlickrResponse(
            items = listOf(
                FlickrItem(
                    title = "Test Title",
                    media = FlickrMedia("https://example.com/image.jpg"),
                    descriptionHtml = "Test Description",
                    author = "Test Author",
                    publishedDate = "2025-01-16"
                )
            )
        )

        // Mock the API call
        coEvery { mockApiService.searchImages("test") } returns Response.success(mockResponse)

        // Call fetchImages
        viewModel.fetchImages("test")

        // Retrieve the first emitted value from the flickrItems flow
        val items = viewModel.flickrItems.first()

        // Assert the response
        assertEquals(1, items.size)
        assertEquals("Test Title", items[0].title)
    }
}