package com.lipssoftware.wargag.ui.feed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.wargag.data.entities.Post
import com.lipssoftware.wargag.data.repository.WargagRepository
import kotlinx.coroutines.launch
import java.io.IOException

class FeedViewModel @ViewModelInject constructor(private val repository: WargagRepository) : ViewModel() {

    var page = -1
    val content = repository.allPosts

    init {
        refreshDataFromRepository()
    }

    fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                repository.fetchPostList(++page)
            } catch (networkError: IOException) {
                // Show a Toast error message and hide the progress bar.
            }
        }
    }

    fun updatePost(post: Post){
        viewModelScope.launch {
            try {
                repository.updatePost(post)
            } catch (exception: Exception) {
                // Show a Toast error message and hide the progress bar.
            }
        }
    }
}