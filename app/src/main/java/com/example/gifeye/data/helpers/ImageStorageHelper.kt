package com.example.gifeye.data.helpers

import android.content.Context
import com.bumptech.glide.Glide
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class ImageStorageHelper @Inject constructor(
    @ApplicationContext private val context: Context
) {

    suspend fun saveImageLocally(url: String, id: String): String = withContext(Dispatchers.IO) {
        val file = File(context.filesDir, "$id.gif")
        val inputStream = Glide.with(context)
            .downloadOnly()
            .load(url)
            .submit()
            .get()
            .inputStream()
        file.outputStream().use { output -> inputStream.copyTo(output) }
        return@withContext file.absolutePath
    }
}