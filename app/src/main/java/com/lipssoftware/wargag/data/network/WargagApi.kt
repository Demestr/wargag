package com.lipssoftware.wargag.data.network

import com.lipssoftware.wargag.data.entities.Comment
import com.lipssoftware.wargag.data.entities.Content
import com.lipssoftware.wargag.data.entities.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WargagApi {

    @GET("wgn/wargag/content/")
    suspend fun getContentPage(@Query("application_id") id: String, @Query("page_no") page: Int): Response<Content<Post>>

    @GET("wgn/wargag/comments/")
    suspend fun getComments(@Query("application_id") id: String, @Query("content_id") content_id: Int): Response<Content<Comment>>

    @GET("wot/auth/login/")
    suspend fun auth(@Query("application_id") id: String, @Query("display") display: String)
}