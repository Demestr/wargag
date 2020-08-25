//package com.lipssoftware.wargag.ui.feed
//
//import android.util.Log
//import androidx.paging.PageKeyedDataSource
//import com.lipssoftware.wargag.data.entities.Post
//import com.lipssoftware.wargag.data.repository.WargagRepository
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//class FeedDataSource @Inject constructor(private val scope: CoroutineScope, private val repository: WargagRepository) : PageKeyedDataSource<Int, Post>() {
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, Post>
//    ) {
//        scope.launch {
//            try {
//                repository.refreshPosts(page = 0)
//                callback.onResult(
//                    emptyList(),
//                    null,
//                    1
//                )
//            } catch (exception: Exception) {
//                Log.e("DataSource", "Failed to fetch data!")
//            }
//        }
//    }
//
//    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
//        scope.launch {
//            try {
//                repository.refreshPosts(page = params.key)
//                callback.onResult(
//                    emptyList(),
//                    params.key.inc()
//                )
//            } catch (exception: Exception) {
//                Log.e("DataSource", "Failed to fetch data!")
//            }
//        }
//    }
//
//    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Post>) {
//        scope.launch {
//            try {
//                repository.refreshPosts(page = params.key)
//                callback.onResult(
//                    emptyList(),
//                    params.key.dec()
//                )
//            } catch (exception: Exception) {
//                Log.e("DataSource", "Failed to fetch data!")
//            }
//        }
//    }
//
//
//}
