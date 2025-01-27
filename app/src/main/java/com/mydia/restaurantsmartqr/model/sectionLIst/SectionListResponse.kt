package com.mydia.restaurantsmartqr.model.sectionLIst

import com.google.gson.annotations.SerializedName


data class SectionListResponse (

    @SerializedName("Success"  ) var status  : Int?              = null,
    @SerializedName("Message" ) var message : String?           = null,
    @SerializedName("result"  ) var result  : ArrayList<SectionList> = arrayListOf()

)