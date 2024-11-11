# GifEye
An Android application for browsing, searching, and managing GIFs with offline capabilities, powered by the Giphy API.

## Features
- **GIF Search and Browse**: Allows users to search for GIFs based on keywords and browse results with pagination.
- **Offline Caching**: Automatically caches searched GIFs locally, making them accessible offline.
- **GIF Deletion and Blocklist**: Users can delete GIFs, with the option to prevent re-downloading them by adding to a blocklist.
- **Full-Screen View with Swipe Navigation**: Offers full-screen GIF viewing with horizontal swipe navigation for browsing adjacent GIFs.

## Implementation Details
The app leverages the following technologies and libraries:
- **Kotlin** for Android development
- **Retrofit** for network requests to the Giphy API
- **Glide** for efficient image loading and caching
- **Kotlin Coroutines** for background tasks and asynchronous processing
- **Room** for local storage of cached GIFs and blocklist
- **Hilt** for dependency injection
- **ViewPager2** with **DotsIndicator** for smooth, interactive full-screen GIF navigation

## Screenshots

|       Home Screen      |        Search Results       |       Full-Screen View       |       Landscape Mode       |
| :--------------------: | :-------------------------: | :-------------------------: | :----------------------: |
| ![Home Screen](screenshots/Screenshot_1.jpg) | ![Search Results](screenshots/Screenshot_2.jpg) | ![Full-Screen View](screenshots/Screenshot_3.jpg) | ![Landscape Mode](screenshots/Screenshot_4.jpg) |

## Installation
1. Clone the repository: `git clone https://github.com/yourusername/GifEye.git`
2. Open the project in Android Studio
3. Run the app on an emulator or physical device

**Note**: Use your Giphy API key in the `ApiConstants` file for the app to function correctly.
