package com.lipssoftware.wargag.data.repository

import android.content.Context
import com.lipssoftware.wargag.R
import com.lipssoftware.wargag.WargagApplication
import com.lipssoftware.wargag.data.entities.Comment
import com.lipssoftware.wargag.data.entities.Content
import com.lipssoftware.wargag.data.entities.Post
import com.lipssoftware.wargag.data.local.WargagDao
import com.lipssoftware.wargag.data.network.WargagApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WargagRepository @Inject constructor(
    val context: Context,
    private val remoteDataSource: WargagApi,
    private val wargagDao: WargagDao
) {

    val allPosts = wargagDao.getAllPosts()
    val favoritesPosts = wargagDao.getFavoritesPosts()

    suspend fun fetchPostList(page: Int) {
        withContext(Dispatchers.IO) {
            val response =
                remoteDataSource.getContentPage(context.getString(R.string.application_id), page)
            response.body()?.let {
                wargagDao.insertAll(it.data)
            }
        }
    }

    suspend fun getComments(content_id: Int): Content<Comment>? {
        return remoteDataSource.getComments(context.getString(R.string.application_id), content_id).body()
    }

    suspend fun updatePost(post: Post){
        withContext(Dispatchers.IO) {
            wargagDao.updatePost(post)
        }
    }
}