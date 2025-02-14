package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerWalletHistoryDetailResponse(

	@field:SerializedName("result")
	val result: WalletHistoryDetails? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Success")
	val success: Int? = null
) : Parcelable

@Parcelize
data class WalletHistoryDetails(

	@field:SerializedName("nCustomerId")
	val nCustomerId: Int? = null,

	@field:SerializedName("cCustomerName")
	val cCustomerName: String? = null,

	@field:SerializedName("IsAligibleForPointRedemption")
	val isAligibleForPointRedemption: Boolean= false,

	@field:SerializedName("fCreditedAmount")
	val fCreditedAmount: Double? = null,

	@field:SerializedName("cCustomerEmail")
	val cCustomerEmail: String? = null,

	@field:SerializedName("cCustomerMobileNo")
	val cCustomerMobileNo: String? = null,

	@field:SerializedName("fDebitedAmount")
	val fDebitedAmount: Double? = null,

	@field:SerializedName("cCurrency")
	val cCurrency: String? = null,

	@field:SerializedName("WalletHistoryDetails")
	val walletHistoryDetails: List<WalletHistoryDetailsItem> = arrayListOf(),

	@field:SerializedName("fTotalWalletAmount")
	val fTotalWalletAmount: Double? = null,

	@field:SerializedName("nMinimumPoint")
	val nMinimumPoint: Int? = null,

	@field:SerializedName("fTotalWalletPoints")
	val fTotalWalletPoints: Double? = null
) : Parcelable

@Parcelize
data class WalletHistoryDetailsItem(

	@field:SerializedName("nOfferId")
	val nOfferId: Int? = null,

	@field:SerializedName("fOrderRedeemAmount")
	val fOrderRedeemAmount: Double? = null,

	@field:SerializedName("dtCreditedDate")
	val dtCreditedDate: String? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("fOrderAmount")
	val fOrderAmount: Double? = null,

	@field:SerializedName("nQuotationMasterId")
	val nQuotationMasterId: Int? = null,

	@field:SerializedName("fDebitedAmount")
	val fDebitedAmount: Double? = null,

	@field:SerializedName("fWalletAmount")
	val fWalletAmount: Double? = null,

	@field:SerializedName("nUserId")
	val nUserId: Int? = null,

	@field:SerializedName("fOrderEarnedPoints")
	val fOrderEarnedPoints: Double? = null,

	@field:SerializedName("cRemark3")
	val cRemark3: String? = null,

	@field:SerializedName("dtDebitedDate")
	val dtDebitedDate: String? = null,

	@field:SerializedName("cRemark4")
	val cRemark4: String? = null,

	@field:SerializedName("nCustomerId")
	val nCustomerId: Int? = null,

	@field:SerializedName("cRemark1")
	val cRemark1: String? = null,

	@field:SerializedName("cReasons")
	val cReasons: String? = null,

	@field:SerializedName("fCreditedAmount")
	val fCreditedAmount: Double? = null,

	@field:SerializedName("cRemark2")
	val cRemark2: String? = null,

	@field:SerializedName("dtDate")
	val dtDate: String? = null,

	@field:SerializedName("fOrderRedeemPoint")
	val fOrderRedeemPoint: Double? = null,

	@field:SerializedName("cRemark")
	val cRemark: String? = null,

	@field:SerializedName("dtOrderDate")
	val dtOrderDate: String? = null,

	@field:SerializedName("fOrderSavings")
	val fOrderSavings: Double? = null
) : Parcelable
