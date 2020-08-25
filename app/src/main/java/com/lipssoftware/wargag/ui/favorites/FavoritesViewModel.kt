package com.lipssoftware.wargag.ui.favorites

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.wargag.data.entities.Post
import com.lipssoftware.wargag.data.repository.WargagRepository
import kotlinx.coroutines.launch
import java.io.IOException

class FavoritesViewModel @ViewModelInject constructor(private val repository: WargagRepository) : ViewModel() {

    val favorites = repository.favoritesPosts

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