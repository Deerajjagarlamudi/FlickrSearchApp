# FlickrSearchApp

FlickrSearchApp is an Android application that allows users to search for images on Flickr and view them within the app. The application is built using Kotlin and follows the MVVM (Model-View-ViewModel) architecture pattern to ensure a clean separation of concerns and maintainable code.

## Features

- **Image Search**: Users can search for images by entering keywords.
- **Image Display**: Displays a list of images based on the search query.
- **Image Details**: Users can view a detailed version of the image upon selection.

## Architecture

The application is structured using the MVVM architecture:

- **Model**: Handles the data layer, including fetching data from the Flickr API.
- **View**: The UI layer that displays data to the user.
- **ViewModel**: Acts as a bridge between the Model and the View, holding UI-related data and handling logic.

## Libraries Used

- **Kotlin**: For programming language.
- **Picasso**: For image loading and caching.
- **Retrofit**: For network requests to the Flickr API.
- **LiveData**: For observing data changes.
- **ViewModel**: To manage UI-related data lifecycle consciously.

## Getting Started

### Prerequisites

- Android Studio installed on your machine.
- A Flickr API key. You can obtain one by creating an app on the [Flickr App Garden](https://www.flickr.com/services/apps/create/).

### Installation

1. **Clone the repository**:
   ```bash
   git clone git@github.com:Deerajjagarlamudi/FlickrSearchApp.git
