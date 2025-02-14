package com.mydia.restaurantsmartqr.model.sectionLIst

import com.google.gson.annotations.SerializedName

data class SectionList(
    @SerializedName("nSectionId") var nSectionId: String? = "",
    @SerializedName("cName") var cName: String? = null,

    @SerializedName("nStoreId") var nStoreId: Int? = null,
    @SerializedName("nUserId") var nUserId: Int? = null,


    @SerializedName("cLogo") var cLogo: String? = null,
    @SerializedName("cHeaderColor") var cHeaderColor: String? = null,
    @SerializedName("cQRColor") var cQRColor: String? = null,




) {
    override fun toString(): String {
        return cName!!
    }
}
