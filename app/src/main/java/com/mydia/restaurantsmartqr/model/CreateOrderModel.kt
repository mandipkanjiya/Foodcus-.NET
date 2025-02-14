package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
@Parcelize
data class CreateOrderModel(

	@field:SerializedName("result")
	val result: WalletHistoryDetailsItem? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Success")
	val success: Int? = null
): Parcelable
@Parcelize
data class OrderDetailResult(

	@field:SerializedName("fSavings")
	val fSavings: Double? = null,

	@field:SerializedName("nEarnedPoints")
	val nEarnedPoints: Int? = null,

	@field:SerializedName("fPayableAmount")
	val fPayableAmount: Double? = null,

	@field:SerializedName("fOrderAmount")
	val fOrderAmount: Double? = null,

	@field:SerializedName("fRedeemAmount")
	val fRedeemAmount: Double? = null,

	@field:SerializedName("fRedeemPoint")
	val fRedeemPoint: Double? = null,

	@field:SerializedName("nOrderId")
	val nOrderId: Int? = null
) : Parcelable

