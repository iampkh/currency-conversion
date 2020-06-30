package com.app.currencyconversion

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface  CurrencyDao {

    @Query("SELECT * from "+Utility.RATE_TABLE_NAME+" ORDER BY "
            +Utility.COLUMN_CURRENCY+" ASC")
    fun getCurrenciesAndRates():LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency : Currency)


}