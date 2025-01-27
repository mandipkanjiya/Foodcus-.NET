package com.mydia.restaurantsmartqr.model.tableLIst

import com.google.gson.annotations.SerializedName

data class TableListResponse (

    @SerializedName("Success"  ) var status  : Int?              = null,
    @SerializedName("Message" ) var message : String?           = null,
    @SerializedName("result"  ) var result  : ArrayList<TableList> = arrayListOf()

)