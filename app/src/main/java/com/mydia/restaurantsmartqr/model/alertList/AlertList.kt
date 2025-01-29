package com.mydia.restaurantsmartqr.model.alertList

import com.google.gson.annotations.SerializedName

data class AlertList(@SerializedName("nRestaurantLogId" ) var nRestaurantLogId : Int?     = null,
                     @SerializedName("nTableId"         ) var nTableId         : Int?     = null,
                     @SerializedName("nModuleId"        ) var nModuleId        : Int?     = null,
                     @SerializedName("nOrderId"         ) var nOrderId         : Int?     = null,
                     @SerializedName("nEmpId"           ) var nEmpId           : Int?     = null,
                     @SerializedName("nType"            ) var nType            : Int?     = null,
                     @SerializedName("IsNotify"         ) var IsNotify         : Boolean? = null,
                     @SerializedName("nMarkCompleted"   ) var nMarkCompleted   : Int?     = null,
                     @SerializedName("cRequestType"   ) var cRequestType   : String?     = null,
                     @SerializedName("cTableName"   ) var cTableName   : String?     = null,
                     @SerializedName("cSectionName"   ) var cSectionName   : String?     = null,
                     @SerializedName("nUserId"          ) var nUserId          : Int?     = null)
