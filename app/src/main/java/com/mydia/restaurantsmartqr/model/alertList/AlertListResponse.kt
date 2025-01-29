package com.mydia.restaurantsmartqr.model.alertList

import com.google.gson.annotations.SerializedName

data class AlertListResponse(@SerializedName("Success" ) var Success : String?           = null,
                             @SerializedName("Message" ) var Message : String?           = null,
                             @SerializedName("result"  ) var result  : ArrayList<AlertList> = arrayListOf())
