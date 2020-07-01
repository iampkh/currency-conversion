package com.app.currencyconversion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.viewmodel.CurrencyViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var currencyViewModel: CurrencyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currency_input_fragment)

        currencyViewModel = ViewModelProvider(this).get(CurrencyViewModel::class.java)
        currencyViewModel.currenciesAndRatesList.observe(this, Observer {currencyList ->
            val editText = findViewById<EditText>(R.id.currency_value)
            val button = findViewById<Button>(R.id.currency_select_btn)

            editText.setText(""+currencyList.get(0).rate)
            button.setText(CurrencyUtil.getDisplayName(currencyList.get(0)))

            var CUSD = Currency("USDUSD",1f)

            Log.d("ConvertRate","CurrencyUtil.getDisplayName(CUSD) ="+CurrencyUtil.getDisplayName(CUSD))




        })

    }
}
