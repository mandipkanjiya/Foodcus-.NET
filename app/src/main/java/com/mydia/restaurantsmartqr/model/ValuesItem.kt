package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ValuesItem(

	val id: String? = null,

	val name: String? = null,

	val attribute_id: String? = null,
	val attribute_name: String? = null,
	val price: String? = null,
	val quantity: String? = null,
	val total: String? = null,
	val price_type: String? = null,
) : Parcelable