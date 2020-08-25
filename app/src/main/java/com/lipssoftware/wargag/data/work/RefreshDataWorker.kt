package com.lipssoftware.wargag.data.work

import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.lipssoftware.wargag.data.repository.WargagRepository
import retrofit2.HttpException

class RefreshDataWorker(private val repo: WargagRepository, params: WorkerParameters) :
    CoroutineWorker(repo.context, params) {

    companion object{
        const val WORK_NAME = "com.lipssoftware.wargag.data.work.RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        try{
            repo.fetchPostList(0)
        } catch (ex: HttpException){
            return Result.retry()
        }
        return Result.success()
    }
}