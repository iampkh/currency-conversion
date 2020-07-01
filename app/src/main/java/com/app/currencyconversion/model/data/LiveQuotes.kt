package com.app.currencyconversion.model.data

import com.google.gson.annotations.SerializedName

data class LiveQuotes(@SerializedName("quotes")var quotes:Map<String,Float>) {

}