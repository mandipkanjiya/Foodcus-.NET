package com.mydia.restaurantsmartqr.model.orderList

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Items(
    @SerializedName("nOrderDetailId"     ) var nOrderDetailId     : Int?     = null,
    @SerializedName("nOrderId"           ) var nOrderId           : Int?     = null,
    @SerializedName("nProductId"         ) var nProductId         : Int?     = null,
    @SerializedName("cProductName"       ) var cProductName       : String?  = null,
    @SerializedName("cProductImage"      ) var cProductImage      : String?  = null,
    @SerializedName("cTaxName"           ) var cTaxName           : String?  = null,
    @SerializedName("fTaxPercentage"     ) var fTaxPercentage     : Int?     = null,
    @SerializedName("fProductPrice"      ) var fProductPrice      : Double?  = null,
    @SerializedName("nWarrantyMonth"     ) var nWarrantyMonth     : Int?     = null,
    @SerializedName("fDiscount"          ) var fDiscount          : Int?     = null,
    @SerializedName("cDetail"            ) var cDetail            : String?  = null,
    @SerializedName("IsActive"           ) var IsActive           : Boolean? = null,
    @SerializedName("IsDisable"          ) var IsDisable          : Boolean? = null,
    @SerializedName("fQuantity"          ) var fQuantity          : Int?     = null,
    @SerializedName("nQuantityId"        ) var nQuantityId        : Int?     = null,
    @SerializedName("cWarrentyType"      ) var cWarrentyType      : String?  = null,
    @SerializedName("dtDate"             ) var dtDate             : String?  = null,
    @SerializedName("nSpecialPrice"      ) var nSpecialPrice      : Int?     = null,
    @SerializedName("dtSpecialPriceFrom" ) var dtSpecialPriceFrom : String?  = null,
    @SerializedName("dtSpecialPriceTo"   ) var dtSpecialPriceTo   : String?  = null,
    @SerializedName("Currency"           ) var Currency           : String?  = null,
    @SerializedName("ChildData"          ) var ChildData          : String?  = null

):Parcelable