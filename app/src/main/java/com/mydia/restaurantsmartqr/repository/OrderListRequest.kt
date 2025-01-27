package com.mydia.restaurantsmartqr.repository

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class OrderListRequest(  @SerializedName("branch_id")
                         val branch_id: String = "",
                         @SerializedName("status")
                         val status: String = "",
                         @SerializedName("offset")
                         val offset: String = "",
                         @SerializedName("limit")
                         val limit: String = "",
                         @SerializedName("table_id")
                         val table_id : String = "",
                         @SerializedName("start_date")
                         val start_date : String = "",
                          @SerializedName("end_date")
                         val end_date : String = "",
                          @SerializedName("section_id")
                         val sectionId: String = "", ){

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