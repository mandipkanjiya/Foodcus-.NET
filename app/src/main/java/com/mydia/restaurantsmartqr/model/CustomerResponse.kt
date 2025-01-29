package com.mydia.restaurantsmartqr.model

import com.google.gson.annotations.SerializedName

data class CustomerResponse(

	@field:SerializedName("result")
	val result: List<CustomerItem>? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Success")
	val success: Int? = null
)

data class CustomerItem(

	@field:SerializedName("nCustomerId")
	val nCustomerId: Int? = null,

	@field:SerializedName("cCustomerName")
	val cCustomerName: String? = null,

	@field:SerializedName("nCountryID")
	val nCountryID: Int? = null,

	@field:SerializedName("cToken")
	val cToken: Any? = null,

	@field:SerializedName("cEmail")
	val cEmail: String? = null,

	@field:SerializedName("nLoginId")
	val nLoginId: Int? = null,

	@field:SerializedName("cGender")
	val cGender: Any? = null,

	@field:SerializedName("nUserId")
	val nUserId: Int? = null,

	@field:SerializedName("cContactNo")
	val cContactNo: String? = null,

	@field:SerializedName("cCustomerType")
	val cCustomerType: String? = null,

	@field:SerializedName("cCustomerImage")
	val cCustomerImage: String? = null
)
