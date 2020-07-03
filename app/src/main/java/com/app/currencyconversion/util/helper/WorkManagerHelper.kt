package com.app.currencyconversion.util.helper

import android.content.Context
import android.content.SharedPreferences
import androidx.work.*
import com.app.currencyconversion.model.scheduler.LiveFetchWorker
import java.util.concurrent.TimeUnit

object WorkManagerHelper{
    const val TAG_LIVE_RATE_SYNC_PERIODIC :String = "LiveRateSync_Periodic"
    const val TAG_LIVE_RATE_SYNC_NOW :String = "LiveRateSync_Now"
    const val KEY_SYNC_STOP_BY_USER = "sync_stop_by_user"

    /**
     * Worker for syncing live currency rate periodically
     * @param context : Application context
     * @param start : decides start/stops the background scheduler, if false other param are invalide
     *
     */
    fun initiateLiveSyncPeriodic(context : Context,start : Boolean){
        if (start) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val liveRatePerioricRequest: PeriodicWorkRequest =
                PeriodicWorkRequestBuilder<LiveFetchWorker>(30, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .addTag(TAG_LIVE_RATE_SYNC_PERIODIC)
                    .build()
            /*WorkManager.getInstance(context)
                .enqueue(liveRatePerioricRequest)*/

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(TAG_LIVE_RATE_SYNC_PERIODIC
                    ,ExistingPeriodicWorkPolicy.KEEP,liveRatePerioricRequest)
        }else{
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG_LIVE_RATE_SYNC_PERIODIC)
            stopSyncPeriodic(context)
        }
    }

    /**
     * Worker for syncing live currency rate Immediately
     * @param context : Application context
     * @param start : decides start/stops the background scheduler,
     *
     */
    fun initiateLiveSyncNow(context : Context,start : Boolean){
        if (start) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
            val liveRatePerioricRequest: OneTimeWorkRequest =
                OneTimeWorkRequestBuilder<LiveFetchWorker>()
                    .setConstraints(constraints)
                    .addTag(TAG_LIVE_RATE_SYNC_NOW)
                    .build()

            WorkManager.getInstance(context).enqueueUniqueWork(TAG_LIVE_RATE_SYNC_NOW
                ,ExistingWorkPolicy.KEEP
                ,liveRatePerioricRequest)
        }else{
            WorkManager.getInstance(context).cancelAllWorkByTag(TAG_LIVE_RATE_SYNC_NOW)
        }
    }

    /**
     * Method to get details whether regular-sync stoped by user or not
     * @param context :  application context
     * @return Boolean :'true' when the sync stopped by user otherwise 'flase'
     */
    fun isSyncStoppedByUser(context:Context):Boolean{
        var PRIVATE_MODE = 0
        val sharedPref:SharedPreferences = context.getSharedPreferences(KEY_SYNC_STOP_BY_USER,PRIVATE_MODE)
        return sharedPref.getBoolean(KEY_SYNC_STOP_BY_USER,false)

    }
    /**
     * Method to get details whether regular-sync stoped by user or not
     * @param context :  application context
     * @return Boolean :'true' when the sync stopped by user otherwise 'flase'
     */
    fun stopSyncPeriodic(context:Context){
        var PRIVATE_MODE = 0
        val sharedPref:SharedPreferences = context.getSharedPreferences(KEY_SYNC_STOP_BY_USER,PRIVATE_MODE)
        sharedPref.edit().putBoolean(KEY_SYNC_STOP_BY_USER,true).apply()


    }
}