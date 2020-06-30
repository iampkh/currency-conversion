package com.app.currencyconversion

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class CurrencyRepository(private val currencyDao: CurrencyDao) {

    val currencyList: LiveData<List<Currency>> = currencyDao.getCurrenciesAndRates()

    @WorkerThread
    suspend fun insert(currency: Currency){
        currencyDao.insert(currency)
    }
}
