package com.app.currencyconversion.util.helper

import android.util.Log
import com.app.currencyconversion.CurrencyUtil

object Logger {

    fun eLog(tag:String,msg:String) {
        Log.e(tag,msg);
    }
    fun dLog(tag:String,msg:String) {
        if(CurrencyUtil.IS_DEBUG)
            Log.d(tag,msg);
    }
    fun iLog(tag:String,msg:String) {
        Log.i(tag,msg);
    }
}