package com.app.currencyconversion.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.currencyconversion.CurrencyUtil
import com.app.currencyconversion.R
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.util.helper.WorkManagerHelper
import com.app.currencyconversion.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var currencyViewModel: CurrencyViewModel



    override fun onStart() {
        super.onStart()
        WorkManagerHelper.initiateLiveSyncPeriodic(this,true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
