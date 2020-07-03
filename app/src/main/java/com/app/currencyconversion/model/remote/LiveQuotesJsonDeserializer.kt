package com.app.currencyconversion.model.remote

import com.app.currencyconversion.model.data.LiveQuotes
import com.app.currencyconversion.util.helper.Logger
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import java.lang.reflect.Type

/**
 * Custom Deserializer to convert JsonObject to {LiveQuotes} object
 */
class LiveQuotesJsonDeserializer : JsonDeserializer<LiveQuotes> {
    private val KEY_QUOTES:String = "quotes"
    private val KEY_SUCCESS:String = "success"

    override fun deserialize(json: JsonElement?,typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LiveQuotes? {
        val jsonObj:JsonObject = json?.asJsonObject!!

        val status:Boolean = jsonObj.get(KEY_SUCCESS).asBoolean
        if(status) {
            val mapQuoate = readQuotes(jsonObj)
            var liveQuote: LiveQuotes? = mapQuoate?.let { LiveQuotes(it) }
            return liveQuote;
        }else {
            Logger.eLog(LiveQuotesJsonDeserializer::class.java.simpleName,"JsonError :"+json.toString())
            return null;
        }
    }

    /**
     * Method to retrive the {Quotes} JsonObject as Map<String,Float>
     */
    private fun readQuotes(jsonObject:JsonObject) : Map<String, Float>? {
        if(!jsonObject!!.has(KEY_QUOTES)){
            return null;
        }
        val jsonElement:JsonElement = jsonObject.get(KEY_QUOTES)
        if(jsonElement == null) return null;

        val quoteObject:JsonObject= jsonElement.asJsonObject
        var mappedQuotes = mutableMapOf<String,Float>()
        for ((k,v) in quoteObject.entrySet()) {
            mappedQuotes.put(k,v.asFloat)
        }
        return mappedQuotes
    }

}