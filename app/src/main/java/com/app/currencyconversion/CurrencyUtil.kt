package com.app.currencyconversion

import com.app.currencyconversion.model.data.Currency

object CurrencyUtil {

    const val DB_NAME= "Currency Live Database"
    const val RATE_TABLE_NAME = "live_rate"
    const val COLUMN_CURRENCY = "currency"
    const val COLUMN_RATE = "rate"

    fun getDisplayName(currency:Currency):String{
        if(currency != null && currency.currency.length >= 3 ) {
            return currency.currency.substring(3,currency.currency.length)
        }
        return "N/A"
    }

    /**
     * method to convert one region's currency to another.
     */
    fun convert(from:Currency,to:Currency,amt:Float):Float{
        if(from != null && to != null) {
            return amt / from.rate * to.rate
        }
        return 0f
    }



}