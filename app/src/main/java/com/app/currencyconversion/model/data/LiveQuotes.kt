package com.app.currencyconversion.model.data

import com.google.gson.annotations.SerializedName

/**
 * Pojo class to map values from remote-json
 */
data class LiveQuotes(@SerializedName("quotes")var quotes:Map<String,Float>) {

}