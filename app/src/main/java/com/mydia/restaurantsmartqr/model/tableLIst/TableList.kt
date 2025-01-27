package com.mydia.restaurantsmartqr.model.tableLIst

import com.google.gson.annotations.SerializedName

data class TableList(
    @SerializedName("nid"         ) var nid        : String? = null,
    @SerializedName("nuser_id"    ) var nuserId    : String? = null,
    @SerializedName("section_id"  ) var sectionId  : String? = null,
    @SerializedName("ctable_name" ) var ctableName : String? = null,
    @SerializedName("slug"        ) var slug       : String? = null,
    @SerializedName("cqr_code"    ) var cqrCode    : String? = null,
    @SerializedName("is_active"   ) var isActive   : String? = null,
    @SerializedName("ncreated_on" ) var ncreatedOn : String? = null,

    @SerializedName("nRestaurantTableId") var nRestaurantTableId: Int? = null,
    @SerializedName("cQRColor") var cQRColor: String? = null,
    @SerializedName("nStoreId") var nStoreId: Int? = null,
    @SerializedName("cHeaderColor") var cHeaderColor: String? = null,
    @SerializedName("IsTableView") var isTableView: Boolean? = null,
    @SerializedName("nUserId") var nUserId: Int? = null,
    @SerializedName("cTableName") var cTableName: String? = null,
    @SerializedName("cQrCode") var cQrCode: String? = null,
    @SerializedName("cLogo") var cLogo: String? = null,
    @SerializedName("SectionName") var sectionName: String? = null,
    @SerializedName("nSectionId") var nSectionId: Int? = null
) {
    override fun toString(): String {
        return ctableName!!
    }
}
