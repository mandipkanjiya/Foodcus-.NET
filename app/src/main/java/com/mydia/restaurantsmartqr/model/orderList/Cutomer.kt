package com.mydia.restaurantsmartqr.model.orderList

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cutomer (
                    @SerializedName("id"               ) var id              : String? = null,
                    @SerializedName("branch_id"        ) var branchId        : String? = null,
                    @SerializedName("wallet_amount"    ) var walletAmount    : String? = null,
                    @SerializedName("phone"            ) var phone           : String? = null,
                    @SerializedName("gender"           ) var gender          : String? = null,
                    @SerializedName("birth_date"       ) var birthDate       : String? = null,
                    @SerializedName("anniversary_date" ) var anniversaryDate : String? = null,
                    @SerializedName("name"             ) var name            : String? = null,
                    @SerializedName("email"            ) var email           : String? = null,
                    @SerializedName("otp"              ) var otp             : String? = null,
                    @SerializedName("otp_created_on"   ) var otpCreatedOn    : String? = null,
                    @SerializedName("is_default"       ) var isDefault       : String? = null,
                    @SerializedName("is_deleted"       ) var isDeleted       : String? = null,
                    @SerializedName("created_on"       ) var createdOn       : String? = null

):Parcelable