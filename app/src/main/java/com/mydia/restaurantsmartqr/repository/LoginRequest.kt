package com.mydia.restaurantsmartqr.repository

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class LoginRequest(
    @SerializedName("cUserName")
    val cUserName: String = "",
    @SerializedName("cPassword")
    val cPassword: String = "",
    @SerializedName("cIPAddress")
    val cIPAddress : String = "",
    @SerializedName("cMType")
    val cMType : String = "",

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
