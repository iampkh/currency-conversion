package com.app.currencyconversion.util.helper

import android.content.Context
import androidx.work.*
import com.app.currencyconversion.model.scheduler.LiveFetchWorker
import java.util.concurrent.TimeUnit

object WorkManagerHelper{
    const val TAG_LIVE_RATE_SYNC :String = "LiveRateSync"

    /**
     * Worker for syncing live currency rate periodically
     */
    fun initiateLiveSyncPeriodic(context : Context,start : Boolean){
        if (start) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val liveRatePerioricRequest: WorkRequest =
                PeriodicWorkRequestBuilder<LiveFetchWorker>(30, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .addTag(TAG_LIVE_RATE_SYNC)
                    .build()
            WorkManager.getInstance(context)
                .enqueue(liveRatePerioricRequest)
        }else{
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG_LIVE_RATE_SYNC)
        }
    }
}