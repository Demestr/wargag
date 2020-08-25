package com.lipssoftware.wargag.ui.detail

import android.accounts.NetworkErrorException
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lipssoftware.wargag.data.entities.Comment
import com.lipssoftware.wargag.data.entities.Post
import com.lipssoftware.wargag.data.repository.WargagRepository
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class DetailViewModel @ViewModelInject constructor(private val repository: WargagRepository) : ViewModel() {
    private val _content = MutableLiveData<Post>()
    val content: LiveData<Post>
        get() = _content

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>>
        get() = _comments

    fun setContent(post: Post?) {
        _content.value = post
        viewModelScope.launch {
            post?.content_id?.let {
                try {
                    _comments.value = repository.getComments(it)?.data
                }
                catch(ex: UnknownHostException){
                    Log.e("error", ex.message)
                }
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