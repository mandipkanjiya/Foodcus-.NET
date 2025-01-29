package com.mydia.restaurantsmartqr.model.orderList

import com.google.gson.annotations.SerializedName

data class OrderListResponse(
    @SerializedName("Success"       ) var Success       : String?           = null,
    @SerializedName("Message"       ) var Message       : String?           = null,
    @SerializedName("result"        ) var result        : ArrayList<OrderList> = arrayListOf(),
    @SerializedName("nTotalRecords" ) var nTotalRecords : Int?              = null

)
