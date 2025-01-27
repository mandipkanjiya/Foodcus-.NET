package com.mydia.restaurantsmartqr.repository

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class OrderStatusRequest(@SerializedName("order_id")
                         val order_id: String = "",
                              @SerializedName("norder_status")
                         val norder_status: String = "",

                              ){

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