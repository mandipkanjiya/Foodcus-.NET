package com.mydia.restaurantsmartqr.model.tableLIst

import com.google.gson.annotations.SerializedName

data class TableList(
    @SerializedName("nRestaurantTableId") var nRestaurantTableId: String? = "",
    @SerializedName("nUserId") var nUserId: Int? = null,
    @SerializedName("nSectionId") var nSectionId: String? = "",
    @SerializedName("cTableName") var cTableName: String? = null,
    @SerializedName("cQrCode") var cQrCode: String? = null,
    @SerializedName("SectionName") var sectionName: String? = null,

    @SerializedName("nStoreId") var nStoreId: Int? = null,

    @SerializedName("IsTableView") var isTableView: Boolean? = null,
    @SerializedName("cLogo") var cLogo: String? = null,


    @SerializedName("cHeaderColor") var cHeaderColor: String? = null,
    @SerializedName("cQRColor") var cQRColor: String? = null,


) {
    override fun toString(): String {
        return cTableName!!
    }
}
