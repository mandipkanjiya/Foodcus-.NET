package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.CreateOrderModel
import com.mydia.restaurantsmartqr.model.WalletHistoryDetails
import com.mydia.restaurantsmartqr.model.login.LoginModel
import com.mydia.restaurantsmartqr.model.login.NewLoginModel
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.repository.TableListRequest
import com.mydia.restaurantsmartqr.util.Constants.ADD_POINT
import com.mydia.restaurantsmartqr.util.Constants.APP_VERSIONS
import com.mydia.restaurantsmartqr.util.Constants.BRANCH_LINK
import com.mydia.restaurantsmartqr.util.Constants.CUSTOMERWALLET_DETAIL
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_STATUS_URL
import com.mydia.restaurantsmartqr.util.Constants.PLACE_ORDER
import com.mydia.restaurantsmartqr.util.Constants.REDEEM_POINT
import com.mydia.restaurantsmartqr.util.Constants.REVIEW_LINK
import com.mydia.restaurantsmartqr.util.Constants.SECTION_LIST
import com.mydia.restaurantsmartqr.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMScanQrDetail@javax.inject.Inject constructor(private val prefs: PreferencesServices,private  val loginRepository: LoginRepository) : BaseViewModel(){
   init {

   }
   var nUserId = ObservableField("")
   var fTotal = ObservableField("")

   var jsonString = ObservableField("")
   var nCustomerId = ObservableField("")
   var customerName = ObservableField("Aadil Mansuri")
   var birthDate = ObservableField("")
   var annivarsaryDate = ObservableField("")
   var cMobileNo = ObservableField("+918141849244")
   var cAlterMobileNo = ObservableField("+918141849244")
   var emailCustomer = ObservableField("+918141849244")
   var cOrderType = ObservableField("1")
   var versionName = ObservableField("")
   var nTableId = ObservableField("")
   var nSectionId = ObservableField("")
   private val _placeorder = MutableLiveData<CreateOrderModel>()
   val placeorder: LiveData<CreateOrderModel> = _placeorder
   var isNoDataSection = ObservableBoolean(false)
   var isUsingRedeemptionPoints = ObservableField("0")
   var fRedeemPoint = ObservableField("")
   var fRedeemAmount = ObservableField("")
   private val _customerWalletDetailHistory = MutableLiveData<WalletHistoryDetails>()
   val customerWalletDetailHistory: LiveData<WalletHistoryDetails> = _customerWalletDetailHistory

   fun getUserData(){
      viewModelScope.launch {
         val user = prefs.get(PrefKey.SAVE_LOGIN, NewLoginModel::class.java)
         nUserId.set(user!!.nUserId)
         //  email.set(user.cEmail)
      }
   }
   fun clearData(){
      viewModelScope.launch {
         prefs.clearData()
      }
   }



   fun getCustomerWalletHistoryApiCall(){
      viewModelScope.launch {
         val map = HashMap<String, String>()
         map["nUserId"] = nUserId.get().toString()
         map["nCustomerId"] = nCustomerId.get().toString()
         map["cToken"] = ""
         map["nLanguageId"] ="0"
         //map["nCountryId"] = ""
         getCustomerWalletHistoryApi(map)
      }
   }
   fun getCustomerWalletHistoryApi(map: HashMap<String,String>)=viewModelScope.launch{
      triggerLoadingDetection(true)
      isLoading.set(true)
      loginRepository.getCustomerWalletHistory(
         scope = viewModelScope,
         onSuccess = {
            triggerLoadingDetection(false)
            isLoading.set(false)
            if(it!!.success == 1){
               viewModelScope.launch {
                  _customerWalletDetailHistory.postValue(it.result)
                  isNoDataSection.set(false)
               }


            }else{
               isNoDataSection.set(true)
            }

         }, onErrorAction = {
            triggerLoadingDetection(false)
            isLoading.set(false)
            isNoDataSection.set(true)
            triggerShowMessage(it)
            Log.e("error",it.toString())
         }, map,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ CUSTOMERWALLET_DETAIL
      )
   }

   fun placeOrderApiCall(){
      viewModelScope.launch {
         //passing parameter
         val map = HashMap<String, Any>()
         map["nUserId"] = nUserId.get().toString()
         map["cToken"] = ""
         map["nLanguageId"] ="0"
         map["nCountryId"] = ""
         map["nCustId"] = nCustomerId.get().toString()
         map["fTotal"] = fTotal.get().toString()
         map["cPaymentTerms"] = "1"
         map["dtDelivaryDate"] = Utils.getCurrentDate()
         map["cAddress"] = ""
         map["cCountryCode"] ="CAD"
         map["cTime"] = ""
         map["cCouponCode"] = ""
         map["cJsonData"] = jsonString.get().toString()
         map["cOrderNotes"] = ""
         map["cAddressSpecialInstructions"] = ""
         map["fDeliveryCharge"] = "0"
         map["cBlock"] = ""
         map["cStreet"] =""
         map["cAvenue"] = ""
         map["cHouse"] = ""
         map["cFloor"] = ""
         map["cBuildingNo"] = ""
         map["cName"] = customerName.get().toString()
         map["cMobileNo"] = cMobileNo.get().toString()
         map["cAlterMobileNo"] = cAlterMobileNo.get().toString()
         map["cGovernorate"] = ""
         map["cArea"] = ""
         map["cSpecialInstruction"] = ""
         map["cOrderType"] ="3"
         map["fCouponDiscount"] = "0"
         map["cFrom"] = ""
         map["cTo"] =""
         map["cLinkGiftCard"] =""
         map["cMessage"] = ""
         map["nBouquetSizeId"] ="0"
         map["nBouquetTypeId"] ="0"
         map["cDeviceName"] = "android"
         map["cAppVersion"] = versionName.get().toString()
         //map["IsCredit"] = isCredit
         map["fWalletAmount"] = "0"
         map["nStoreId"] ="0"
         map["cGiftCardImage"] = ""
         map["nDeliveryTimeType"] = "0"
         map["nTableId"] = "0"
         map["nSectionId"] ="0"
         map["nEmployeeId"] = "0"
         map["IsUsingRedeemptionPoints"] = isUsingRedeemptionPoints.get().toString()
         map["fRedeemPoint"] = fRedeemPoint.get().toString()
         map["fRedeemAmount"] = fRedeemAmount.get().toString()

         Log.e("CreateOrderParam", map.toString())
         placeOrderApi(map)
      }

   }
   fun placeOrderApi(orderListRequest: HashMap<String,Any>)=viewModelScope.launch{
      triggerLoadingDetection(true)
      isLoading.set(true)
      loginRepository.placeOrderApi(
         scope = viewModelScope,
         onSuccess = {
            triggerLoadingDetection(false)
            isLoading.set(false)
            if(it!!.success == 1){
               viewModelScope.launch {
                  _placeorder.postValue(it)
                  //  isNoDataSection.set(false)
               }


            }else{
               showToast(it.message.toString())
               // isNoDataSection.set(true)
            }

         }, onErrorAction = {
            triggerLoadingDetection(false)
            isLoading.set(false)
            // showToast(it.toString())
            // isNoDataSection.set(true)
            triggerShowMessage(it)
            Log.e("error",it.toString())
         }, orderListRequest,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ PLACE_ORDER
      )
   }
}