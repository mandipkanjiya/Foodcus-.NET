package com.mydia.restaurantsmartqr.repository

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class OrderListRequest(  @SerializedName("nUserId")
                         val nUserId: String = "",
                         @SerializedName("nCustomerId")
                         val nCustomerId: String = "",
                         @SerializedName("nOrderType")
                         val nOrderType: String = "",
                         @SerializedName("nFromId")
                         val nFromId: String = "",
                         @SerializedName("nToId")
                         val nToId : String = "",
                         @SerializedName("cTableId")
                         val cTableId : String = "",
                          @SerializedName("cSectionId")
                         val cSectionId : String ="",
                        @SerializedName("nStatus")
                         val nStatus : String ="",
                        @SerializedName("FromDate")
                         val FromDate : String ="",
                         @SerializedName("ToDate")
                         val ToDate : String ="",
                          @SerializedName("cToken")
                         val cToken : String = "", ){

    fun toFieldMap() : HashMap<String,String>{
        val requestMap = HashMap<String,String>()
        val jsonObjectStr = Gson().toJson(this)
        val jsonObject = JSONObject(jsonObjectStr)
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.optString(key)
            requestMap[key] = value
        }
        return requestMap
    }
}