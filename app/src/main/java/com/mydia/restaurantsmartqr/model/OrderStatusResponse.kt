package com.mydia.restaurantsmartqr.model

import com.google.gson.annotations.SerializedName

data class OrderStatusResponse(

    @SerializedName("Success"  ) var Success  : String?    = null,
    @SerializedName("Message" ) var Message : String? = null,
    @SerializedName("result" ) var result : String? = null,


)
