package com.app.currencyconversion.model.remote

import com.app.currencyconversion.model.data.LiveQuotes
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

class LiveQuotesJsonDeserializer : JsonDeserializer<LiveQuotes> {
    private val KEY_QUOTES:String = "quotes"
    override fun deserialize(json: JsonElement?,typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LiveQuotes? {
        val mapQuoate = json?.asJsonObject?.let { readQuotes(it) }
        var liveQuote : LiveQuotes? = mapQuoate?.let { LiveQuotes(it) }
        return liveQuote;
    }

    private fun readQuotes(jsonObject:JsonObject) : Map<String, Float>? {
        val jsonElement:JsonElement = jsonObject!!.get(KEY_QUOTES)
        if(jsonElement == null) return null;

        val quoteObject:JsonObject= jsonElement.asJsonObject
        var mappedQuotes = mutableMapOf<String,Float>()
        for ((k,v) in quoteObject.entrySet()) {
            mappedQuotes.put(k,v.asFloat)
        }
        return mappedQuotes
    }

}