package com.mydia.restaurantsmartqr.model

import com.google.gson.annotations.SerializedName

data class CreateOrderModel(

	@field:SerializedName("result")
	val result: Int? = null,

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Success")
	val success: Int? = null
)
