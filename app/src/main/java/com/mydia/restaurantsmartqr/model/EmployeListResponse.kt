package com.mydia.restaurantsmartqr.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmployeListResponse(

	@field:SerializedName("result")
	val result: List<EmployeItem> = ArrayList(),

	@field:SerializedName("Message")
	val message: String? = null,

	@field:SerializedName("Success")
	val success: Int? = null
) : Parcelable

@Parcelize
data class EmployeItem(

	@field:SerializedName("nEmpId")
	val nEmpId: Int? = null,

	@field:SerializedName("nEmpUpperLevelPersonId")
	val nEmpUpperLevelPersonId: Int? = null,

	@field:SerializedName("dtEmpDOB")
	val dtEmpDOB: String? = null,

	@field:SerializedName("IsRememberEmailPassword")
	val isRememberEmailPassword: Boolean? = null,

	@field:SerializedName("IsActive")
	val isActive: Boolean? = null,

	@field:SerializedName("cEmpScanSignImage")
	val cEmpScanSignImage: String? = null,

	@field:SerializedName("cRemarks")
	val cRemarks: String? = null,

	@field:SerializedName("cEmpEmailId")
	val cEmpEmailId: String? = null,

	@field:SerializedName("cVerificationFilePath")
	val cVerificationFilePath: String? = null,

	@field:SerializedName("cEmpPANNo")
	val cEmpPANNo: String? = null,

	@field:SerializedName("cEmpPassword")
	val cEmpPassword: String? = null,

	@field:SerializedName("cEmpUIDNo")
	val cEmpUIDNo: String? = null,

	@field:SerializedName("cEmpMobileNo")
	val cEmpMobileNo: String? = null,

	@field:SerializedName("cRemarks1")
	val cRemarks1: String? = null,

	@field:SerializedName("cRemarks3")
	val cRemarks3: String? = null,

	@field:SerializedName("totPrice")
	val totPrice: Double? = null,

	@field:SerializedName("cRemarks2")
	val cRemarks2: String? = null,

	@field:SerializedName("cEmpBankAccountNo")
	val cEmpBankAccountNo: String? = null,

	@field:SerializedName("fAmountPerhours")
	val fAmountPerhours: String? = null,

	@field:SerializedName("cEmployeeIsPaid")
	val cEmployeeIsPaid: String? = null,

	@field:SerializedName("cEmpName")
	val cEmpName: String? = null,

	@field:SerializedName("nDesgId")
	val nDesgId: Int? = null,

	@field:SerializedName("IsActive1")
	val isActive1: String? = null,

	@field:SerializedName("cEmpScanPhotoImage")
	val cEmpScanPhotoImage: String? = null,

	@field:SerializedName("dtEmpJoiningDate")
	val dtEmpJoiningDate: String? = null,

	@field:SerializedName("nUserId")
	val nUserId: Int? = null,

	@field:SerializedName("nHoursPerWeek")
	val nHoursPerWeek: String? = null,

	@field:SerializedName("nUserId1")
	val nUserId1: String? = null,

	@field:SerializedName("cDesgName")
	val cDesgName: String? = null,

	@field:SerializedName("nEmployeeId")
	val nEmployeeId: String? = null,

	@field:SerializedName("nLanguageId")
	val nLanguageId: String? = null,

	@field:SerializedName("cDeptName")
	val cDeptName: String? = null,

	@field:SerializedName("UpperLevelPersonName")
	val upperLevelPersonName: String? = null,

	@field:SerializedName("nDeptId")
	val nDeptId: Int? = null,

	@field:SerializedName("nLoginId")
	val nLoginId: Int? = null,

	@field:SerializedName("cEmpBankName")
	val cEmpBankName: String? = null,

	@field:SerializedName("nPayScaleId")
	val nPayScaleId: String? = null,

	@field:SerializedName("cEmpAddress")
	val cEmpAddress: String? = null,

	@field:SerializedName("cCity")
	val cCity: String? = null,

	@field:SerializedName("IsDisable")
	val isDisable: String? = null
) : Parcelable
