package com.app.currencyconversion.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.currencyconversion.model.data.Amount

class AmountViewModel  : ViewModel() {
    val amount = MutableLiveData<Amount>()
    /**
     * When amount is updated , it will notify
     */
    fun setRate(amt:Amount) {
        amount.value = amt
    }
}