package com.app.currencyconversion.model.scheduler

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.app.currencyconversion.model.local.LiveRateDatabase
import com.app.currencyconversion.model.remote.RetrofitBuilder
import com.app.currencyconversion.model.repo.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class LiveFetchWorker(appContext:Context, workerParams:WorkerParameters)
    :CoroutineWorker(appContext,workerParams){
    var context: Context

    init {
        this.context = appContext;
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO){
        return@withContext try {

            val currencyDao = LiveRateDatabase.getDatbase(context,
                this).currencyDao()
            val remoteService = RetrofitBuilder.apiService
            var repository = CurrencyRepository(remoteService,currencyDao)

            repository.syncLocalWithRemoteData();

            Log.d(this::class.java.simpleName,"LiveFetchWorker in progress")
            /*withContext(Dispatchers.Main){
                Toast.makeText(context,"Toast from worker thread",Toast.LENGTH_LONG).show()
            }*/

           // Result.success()
            Result.retry()
        }catch (error:Throwable){
            Result.failure()
        }
    }

}