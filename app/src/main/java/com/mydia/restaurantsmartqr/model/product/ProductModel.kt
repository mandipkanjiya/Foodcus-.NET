package com.mydia.restaurantsmartqr.model.product

import com.google.gson.annotations.SerializedName

data class ProductModel(

	@field:SerializedName("nTagId")
	val nTagId: Int? = null,

	@field:SerializedName("IsTrackInventory")
	val isTrackInventory: Boolean? = null,

	@field:SerializedName("cProductUniqueCode")
	val cProductUniqueCode: String? = null,

	@field:SerializedName("fStock")
	val fStock: Double? = null,

	@field:SerializedName("cRemark3")
	val cRemark3: String? = null,

	@field:SerializedName("fMRP")
	val fMRP: Double? = null,

	@field:SerializedName("IsVirualProduct")
	val isVirualProduct: Boolean? = null,

	@field:SerializedName("cCsvFile")
	val cCsvFile: String? = null,

	@field:SerializedName("nParentProductId")
	val nParentProductId: Int? = null,

	@field:SerializedName("dtNewProductFrom")
	val dtNewProductFrom: String? = null,

	@field:SerializedName("cSKU")
	val cSKU: String? = null,

	@field:SerializedName("cRemark1")
	val cRemark1: String? = null,

	@field:SerializedName("cTagName")
	val cTagName: String? = null,

	@field:SerializedName("cRemark2")
	val cRemark2: String? = null,

	@field:SerializedName("cName")
	val cName: String? = null,

	@field:SerializedName("cTaxName")
	val cTaxName: Any? = null,

	@field:SerializedName("Currency")
	val currency: String? = null,

	@field:SerializedName("nAttributeSetId")
	val nAttributeSetId: Int? = null,

	@field:SerializedName("nCategoryId")
	val nCategoryId: Int? = null,

	@field:SerializedName("nBrandId")
	val nBrandId: Int? = null,

	@field:SerializedName("cImage")
	val cImage: String? = null,

	@field:SerializedName("cShortDescription")
	val cShortDescription: String? = null,

	@field:SerializedName("nWeight")
	val nWeight: Int? = null,

	@field:SerializedName("Images")
	val images: List<ImagesItem?>? = null,

	@field:SerializedName("fPrice")
	val fPrice: Double? = null,

	@field:SerializedName("dtSpecialPriceFrom")
	val dtSpecialPriceFrom: String? = null,

	@field:SerializedName("IsCartExists")
	val isCartExists: Boolean? = null,

	@field:SerializedName("nUserId")
	val nUserId: Int? = null,

	@field:SerializedName("IsFeatured")
	val isFeatured: Boolean? = null,

	@field:SerializedName("nLanguageId")
	val nLanguageId: Int? = null,

	@field:SerializedName("cProductCode")
	val cProductCode: String? = null,

	@field:SerializedName("cWarrantyType")
	val cWarrantyType: String? = null,

	@field:SerializedName("nUnitType")
	val nUnitType: Int? = null,

	@field:SerializedName("cUrlKey")
	val cUrlKey: String? = null,

	@field:SerializedName("IsDisable")
	val isDisable: Boolean? = null,

	@field:SerializedName("Attribute")
	val attribute: List<Any?>? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("nMinimumStockAlert")
	val nMinimumStockAlert: Int? = null,

	@field:SerializedName("cDescription")
	val cDescription: String? = null,

	@field:SerializedName("IswishList")
	val iswishList: Int? = null,

	@field:SerializedName("cMetaTitle")
	val cMetaTitle: String? = null,

	@field:SerializedName("nBaseCurrencyId")
	val nBaseCurrencyId: Int? = null,

	@field:SerializedName("nProductId")
	val nProductId: Int? = null,

	@field:SerializedName("nWarrentyDuration")
	val nWarrentyDuration: Int? = null,

	@field:SerializedName("nWarrentyId")
	val nWarrentyId: Int? = null,

	@field:SerializedName("nTaxId")
	val nTaxId: Int? = null,

	@field:SerializedName("dtCreatedDate")
	val dtCreatedDate: String? = null,

	@field:SerializedName("fSpecialPrice")
	val fSpecialPrice: Double? = null,

	@field:SerializedName("cMetaDescription")
	val cMetaDescription: String? = null,

	@field:SerializedName("nOptionTemplatesId")
	val nOptionTemplatesId: Int? = null,

	@field:SerializedName("nOtherLangProductId")
	val nOtherLangProductId: Int? = null,

	@field:SerializedName("cCategoryName")
	val cCategoryName: Any? = null,

	@field:SerializedName("nParentProductIdLanguage")
	val nParentProductIdLanguage: Int? = null,

	@field:SerializedName("nSpecialPrice")
	val nSpecialPrice: Int? = null,

	@field:SerializedName("dtNewProductTo")
	val dtNewProductTo: String? = null,

	@field:SerializedName("dtSpecialPriceTo")
	val dtSpecialPriceTo: String? = null,

	@field:SerializedName("quantity")
	var quantity: String? = ""
)

data class ImagesItem(

	@field:SerializedName("Image")
	val image: String? = null
)
