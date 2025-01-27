package com.mydia.restaurantsmartqr.model.sectionLIst

import com.google.gson.annotations.SerializedName

data class SectionList(
    @SerializedName("id") var id        : String? = "",
    @SerializedName("name") var name    : String? = "",
    @SerializedName("branch_id") var branch_id  : String? = "",
    @SerializedName("created_on") var created_on : String? = "",
    @SerializedName("cQRColor") var cQRColor: String? = null,
    @SerializedName("nStoreId") var nStoreId: Int? = null,
    @SerializedName("cName") var cName: String? = null,
    @SerializedName("cHeaderColor") var cHeaderColor: String? = null,
    @SerializedName("nUserId") var nUserId: Int? = null,
    @SerializedName("cLogo") var cLogo: String? = null,
    @SerializedName("nSectionId") var nSectionId: Int? = null

) {
    override fun toString(): String {
        return name!!
    }
}
