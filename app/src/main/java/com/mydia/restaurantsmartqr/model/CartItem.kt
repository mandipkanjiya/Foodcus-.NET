package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class CartItem(
	val productId: String? = null,
	val productName: String? = null,
	val image: String? = null,
	val quantity: String? = null,
	val totalPrice: String? = null,
	val price: String? = null,
	val color: String? = null,
	val size: String? = null,
	val item_tax: String? = null,
	val tax_rate: String? = null,
	val attributes: List<ValuesItem>? = null,
) : Parcelable