package com.app.currencyconversion.model.repo

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.model.local.CurrencyDao

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    val currencyList: LiveData<List<Currency>> = currencyDao.getCurrenciesAndRates()

    @WorkerThread
    suspend fun insert(currency: Currency){
        currencyDao.insert(currency)
    }
}
