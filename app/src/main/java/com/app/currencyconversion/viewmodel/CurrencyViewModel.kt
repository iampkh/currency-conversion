package com.app.currencyconversion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.model.local.LiveRateDatabase
import com.app.currencyconversion.model.repo.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CurrencyViewModel(application: Application):AndroidViewModel(application) {

    private val repository: CurrencyRepository

    val currenciesAndRatesList : LiveData<List<Currency>>

    init {
        val currencyDao = LiveRateDatabase.getDatbase(application,
            viewModelScope).currencyDao()
        repository = CurrencyRepository(currencyDao)
        currenciesAndRatesList = repository.currencyList
    }


    fun insert(currency: Currency) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(currency)
    }

}