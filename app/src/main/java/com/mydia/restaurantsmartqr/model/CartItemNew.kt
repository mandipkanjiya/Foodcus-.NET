package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CartItemNew(
	val product_id: String? = null,
	val quantity: String? = null,
	val item_tax: String? = null,
	val tax_rate: String? = null,
	val unit_price: String? = null,
	val total_price: String? = null,
	val attributes: List<ValuesItem>? = null,

	val product_name: String? = null,

) : Parcelable