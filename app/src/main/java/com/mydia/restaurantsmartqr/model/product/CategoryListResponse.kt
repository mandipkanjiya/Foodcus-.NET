package com.mydia.restaurantsmartqr.model.product

import com.google.gson.annotations.SerializedName

data class CategoryListResponse (

    @SerializedName("Success"  ) var status  : Int?              = null,
    @SerializedName("Message" ) var message : String?           = null,
    @SerializedName("result"  ) var result  : ArrayList<CategoryModel> = arrayListOf(),
    @SerializedName("nTotalRecords"  ) var nTotalRecords  : String? = null

)