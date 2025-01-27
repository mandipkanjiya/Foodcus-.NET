package com.mydia.restaurantsmartqr.model.login

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("Success") var status  : Int?    = null,
    @SerializedName("Message") var message : String? = null,
    @SerializedName("result") var result  : NewLoginModel? = NewLoginModel()

)
