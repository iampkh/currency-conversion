package com.app.currencyconversion.model.repo

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.model.local.CurrencyDao
import com.app.currencyconversion.model.remote.RemoteAPIService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyRepository(private val remoteService :RemoteAPIService
                         , private val currencyDao: CurrencyDao) {

    val currencyList: LiveData<List<Currency>> = currencyDao.getCurrenciesAndRates()

    init{
       // syncLocalWithRemoteData()
    }

    fun syncLocalWithRemoteData() {
        //
        CoroutineScope(Dispatchers.IO).launch {
            val response = remoteService.getLiveCurrencyData()
            try {
                if (response.isSuccessful && response.body() !=null) {
                    val data = response.body()!!

                    Log.d("Json response",""+data.quotes)
                    for((k,v) in data.quotes){
                        insert(Currency(k,v))
                    }
                }
            }catch (e:Exception) {
                Log.e("Json response error ",e.toString())
            }

        }
        //remoteService.getLiveCurrencyData()
    }

    @WorkerThread
    suspend fun insert(currency: Currency){
        currencyDao.insert(currency)
    }

    @WorkerThread
    suspend fun insertBulk(currencyList: List<Currency>){
        currencyDao.insertBulk(currencyList)
    }
}
