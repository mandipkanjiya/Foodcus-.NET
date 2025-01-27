package com.mydia.restaurantsmartqr.model.login

import com.google.gson.annotations.SerializedName

data class NewLoginModel(

	@field:SerializedName("nDefaultCurrency")
	val nDefaultCurrency: String? = null,

	@field:SerializedName("cSlug")
	val cSlug: String? = null,

	@field:SerializedName("cToken")
	val cToken: String? = null,

	@field:SerializedName("IsValidEmail")
	val isValidEmail: Boolean? = null,

	@field:SerializedName("cAddress")
	val cAddress: String? = null,

	@field:SerializedName("cEmail")
	val cEmail: String? = null,

	@field:SerializedName("nUserId")
	val nUserId: String? = null,

	@field:SerializedName("cCompanyName")
	val cCompanyName: String? = null,

	@field:SerializedName("cLogo")
	val cLogo: String? = null,

	@field:SerializedName("dtRegistrationDate")
	val dtRegistrationDate: String? = null
)
