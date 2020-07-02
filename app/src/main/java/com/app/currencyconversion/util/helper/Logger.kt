package com.app.currencyconversion.util.helper

import android.util.Log

object Logger {

    fun eLog(tag:String,msg:String) {
        Log.e(tag,msg);
    }
    fun dLog(tag:String,msg:String) {
        Log.d(tag,msg);
    }
    fun iLog(tag:String,msg:String) {
        Log.i(tag,msg);
    }
}