package com.app.currencyconversion.model.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.CurrencyUtil

@Dao
interface  CurrencyDao {

    @Query("SELECT * from "+ CurrencyUtil.RATE_TABLE_NAME +" ORDER BY "
            + CurrencyUtil.COLUMN_CURRENCY +" ASC")
    fun getCurrenciesAndRates():LiveData<List<Currency>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(currency : Currency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBulk(currencyList : List<Currency>)


}