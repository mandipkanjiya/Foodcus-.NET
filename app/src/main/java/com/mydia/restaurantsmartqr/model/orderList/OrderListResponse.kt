package com.mydia.restaurantsmartqr.model.orderList

import com.google.gson.annotations.SerializedName

data class OrderListResponse(
    @SerializedName("status"      ) var status     : Int?              = null,
    @SerializedName("message"     ) var message    : String?           = null,
    @SerializedName("timestamp"   ) var timestamp  : Long?              = null,
    @SerializedName("next_offset" ) var nextOffset : Int?              = null,
    @SerializedName("total"       ) var total      : Int?              = null,
    @SerializedName("result"      ) var result     : ArrayList<OrderList> = arrayListOf()

)
