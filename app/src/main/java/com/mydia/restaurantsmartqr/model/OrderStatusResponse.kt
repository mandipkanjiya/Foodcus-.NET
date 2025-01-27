package com.mydia.restaurantsmartqr.model

import com.google.gson.annotations.SerializedName

data class OrderStatusResponse(

    @SerializedName("status"  ) var status  : Int?    = null,
    @SerializedName("message" ) var message : String? = null,


)
