package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AppUpdateResponse(
	@field:SerializedName("message")
	val message: String? = null,
	@field:SerializedName("force_update")
	val forceUpdate: Boolean? = null,
	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable
