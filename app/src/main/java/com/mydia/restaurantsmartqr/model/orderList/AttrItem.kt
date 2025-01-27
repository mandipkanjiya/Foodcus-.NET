package com.mydia.restaurantsmartqr.model.orderList

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AttrItem(
    @SerializedName("quantity"        ) var quantity       : String? = null,
    @SerializedName("item_name"       ) var itemName       : String? = null,
    @SerializedName("attribute_price" ) var attributePrice : String? = null


):Parcelable
