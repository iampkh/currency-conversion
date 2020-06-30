package com.app.currencyconversion

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@Database(entities = [Currency::class],version = 1)
abstract class LiveRateDatabase: RoomDatabase() {

    abstract fun currencyDao() : CurrencyDao

    companion object{
        @Volatile
        private var INSTANCE: LiveRateDatabase?=null

        fun getDatbase(context:Context,
                       scope: CoroutineScope):LiveRateDatabase{
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LiveRateDatabase::class.java,
                    Utility.DB_NAME
                ).fallbackToDestructiveMigration()
                    .addCallback(LiveRateDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        
        private class LiveRateDatabaseCallback(
            private val scope: CoroutineScope
        ):RoomDatabase.Callback(){

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { liveRateDatabase ->
                    scope.launch(Dispatchers.IO) {
                        populateDatbase(liveRateDatabase.currencyDao())
                    }
                }
            }
        }

        fun populateDatbase(currencyDao: CurrencyDao){

            var currency = Currency("USDUSD","1")
            currencyDao.insert(currency)
        }
        

    }

}