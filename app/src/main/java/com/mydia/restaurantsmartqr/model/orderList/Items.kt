package com.mydia.restaurantsmartqr.model.orderList

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Items(
    @SerializedName("nid"          ) var nid         : String?               = null,
    @SerializedName("norder_id"    ) var norderId    : String?               = null,
    @SerializedName("nitem_id"     ) var nitemId     : String?               = null,
    @SerializedName("product_type" ) var productType : String?               = null,
    @SerializedName("cname"        ) var cname       : String?               = null,
    @SerializedName("nqty"         ) var nqty        : String?               = null,
    @SerializedName("item_price"   ) var itemPrice   : String?               = null,
    @SerializedName("item_total"   ) var itemTotal   : String?               = null,
    @SerializedName("item_tax"     ) var itemTax     : String?               = null,
    @SerializedName("tax_rate"     ) var taxRate     : String?               = null,
    @SerializedName("attributes"   ) var attributes  : ArrayList<Attributes> = arrayListOf()

):Parcelable