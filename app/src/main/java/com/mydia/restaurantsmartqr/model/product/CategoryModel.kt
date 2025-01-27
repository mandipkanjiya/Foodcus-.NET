package com.mydia.restaurantsmartqr.model.product

import com.google.gson.annotations.SerializedName

data class CategoryModel(

	@field:SerializedName("nLanguageId")
	val nLanguageId: Int? = null,

	@field:SerializedName("nParentCateogyId")
	val nParentCateogyId: Int? = null,

	@field:SerializedName("cCategoryIcon")
	val cCategoryIcon: String? = null,

	@field:SerializedName("IsSubCategory")
	val isSubCategory: String? = null,

	@field:SerializedName("cCategoryDesc")
	val cCategoryDesc: String? = null,

	@field:SerializedName("cCategoryName")
	val cCategoryName: String? = null,

	@field:SerializedName("cCategoryImage")
	val cCategoryImage: String? = null,

	@field:SerializedName("nCateogyLangWiseId")
	val nCateogyLangWiseId: Int? = null,

	@field:SerializedName("nCategoryId")
	val nCategoryId: Int? = null
)
