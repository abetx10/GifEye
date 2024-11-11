package com.example.gifeye.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gifeye.data.model.GifData
import com.example.gifeye.domain.usecase.SearchGifsUseCase
import com.example.gifeye.domain.usecase.DeleteGifUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifListViewModel @Inject constructor(
    private val searchGifsUseCase: SearchGifsUseCase,
    private val deleteGifUseCase: DeleteGifUseCase
) : ViewModel() {

    private val _gifs = MutableStateFlow<List<GifData>>(emptyList())
    val gifs: StateFlow<List<GifData>> get() = _gifs

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> get() = _errorState

    private var currentPage = 0
    private val limit = 10
    private var isEndReached = false
    private var currentQuery: String? = null

    init {
        searchGifs("fun")
    }

    fun searchGifs(query: String) {
        isEndReached = false
        currentPage = 0
        currentQuery = query
        _gifs.value = emptyList()
        fetchGifs()
    }

    fun fetchNextPage() {
        if (!_isLoading.value) fetchGifs()
    }

    private fun fetchGifs() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val offset = currentPage * limit
                val newGifs = currentQuery?.let { searchGifsUseCase(it, limit, offset) } ?: emptyList()
                if (newGifs.isNotEmpty()) {
                    _gifs.value = _gifs.value + newGifs
                    currentPage++
                }
                isEndReached = newGifs.size < limit
            } catch (e: Exception) {
                _errorState.value = "Error loading GIF images: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteGif(id: String) {
        viewModelScope.launch {
            deleteGifUseCase(id)
            _gifs.value = _gifs.value.filter { it.id != id }
        }
    }
}