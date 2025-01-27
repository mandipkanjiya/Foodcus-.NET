package com.mydia.restaurantsmartqr.model.product

import com.mydia.restaurantsmartqr.model.tableLIst.TableList

import com.google.gson.annotations.SerializedName

data class ProductListResponse (

    @SerializedName("Success"  ) var status  : Int?              = null,
    @SerializedName("Message" ) var message : String?           = null,
    @SerializedName("result"  ) var result  : ArrayList<ProductModel> = arrayListOf(),
    @SerializedName("nTotalRecords"  ) var nTotalRecords  : String? = null

)