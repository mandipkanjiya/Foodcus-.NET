package com.mydia.restaurantsmartqr.model.orderList

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Attributes(@SerializedName("cprice"             ) var cprice           : String? = null,
                      @SerializedName("attribute_id"       ) var attributeId      : String? = null,
                      @SerializedName("price_type"         ) var priceType        : String? = null,
                      @SerializedName("quantity"           ) var quantity         : String? = null,
                      @SerializedName("nid"                ) var nid              : String? = null,
                      @SerializedName("total"              ) var total            : String? = null,
                      @SerializedName("cattribute"         ) var cattribute       : String? = null,
                      @SerializedName("attribute_title"         ) var attribute_title       : String? = null,
                      @SerializedName("attributes"              ) var attributes            : ArrayList<AttrItem> = arrayListOf(),
                      @SerializedName("attribute_group_id" ) var attributeGroupId : String? = null

):Parcelable
