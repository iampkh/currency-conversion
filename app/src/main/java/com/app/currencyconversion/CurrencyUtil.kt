package com.app.currencyconversion

import com.app.currencyconversion.model.data.Currency
import com.app.currencyconversion.util.helper.Logger

object CurrencyUtil {

    const val DB_NAME= "Currency Live Database"
    const val RATE_TABLE_NAME = "live_rate"
    const val COLUMN_CURRENCY = "currency"
    const val COLUMN_RATE = "rate"
    const val IS_DEBUG = false

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

    /**
     * Convert the float to number decimal String
     */
    fun convertFloatToString(value:Float):String{
        return "%.4f".format(value)
    }



}