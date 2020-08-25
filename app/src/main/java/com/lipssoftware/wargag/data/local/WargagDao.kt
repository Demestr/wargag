package com.lipssoftware.wargag.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lipssoftware.wargag.data.entities.Post

@Dao
interface WargagDao {

    @Query("SELECT * FROM posts_table ORDER BY created_at DESC")
    fun getAllPosts(): LiveData<List<Post>>

    @Query("SELECT * FROM posts_table WHERE isFavorite = 1 ORDER BY created_at DESC")
    fun getFavoritesPosts(): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(posts: List<Post>)

    @Update
    fun updatePost(post: Post)
}