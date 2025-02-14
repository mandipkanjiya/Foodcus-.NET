package com.mydia.restaurantsmartqr.viewModel
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.CreateOrderModel
import com.mydia.restaurantsmartqr.model.CustomerResponse
import com.mydia.restaurantsmartqr.model.login.NewLoginModel
import com.mydia.restaurantsmartqr.model.product.CategoryListResponse
import com.mydia.restaurantsmartqr.model.product.ProductListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.util.Constants.ADD_CUSTOMER
import com.mydia.restaurantsmartqr.util.Constants.CATEGORY_LIST
import com.mydia.restaurantsmartqr.util.Constants.CUSTOMER_LIST
import com.mydia.restaurantsmartqr.util.Constants.PLACE_ORDER
import com.mydia.restaurantsmartqr.util.Constants.PRODUCT_LIST
import com.mydia.restaurantsmartqr.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMProductMenu @javax.inject.Inject constructor(private val prefs: PreferencesServices, private  val loginRepository: LoginRepository) : BaseViewModel(){
    private val _categoryList = MutableLiveData<CategoryListResponse>()
    val categoryList: LiveData<CategoryListResponse> = _categoryList
    private val _productList = MutableLiveData<ProductListResponse>()
    val productList: LiveData<ProductListResponse> = _productList
    private val _placeorder = MutableLiveData<CreateOrderModel>()
    val placeorder: LiveData<CreateOrderModel> = _placeorder
    var nUserId = ObservableField("")
    var fTotal = ObservableField("")
    var jsonString = ObservableField("")
    var customerName = ObservableField("")
    var cMobileNo = ObservableField("")
    var cAlterMobileNo = ObservableField("")
    var versionName = ObservableField("")
    var nTableId = ObservableField("")
    var nSectionId = ObservableField("")
    var nEmployeeId = ObservableField("")
    var nCustomerId = ObservableField("")
    var currency = ObservableField("")
    var emailCustomer = ObservableField("")
    var birthDate = ObservableField("")
    var annivarsaryDate = ObservableField("")
    var isNoDataSection = ObservableBoolean(false)
    var isNoDataTable = ObservableBoolean(false)
    var isNoDataReady = ObservableBoolean(false)
    private val _addCustomer = MutableLiveData<CustomerResponse>()
    val addCustomer: LiveData<CustomerResponse> = _addCustomer

    private val _customerList = MutableLiveData<CustomerResponse>()
    val customerList: LiveData<CustomerResponse> = _customerList

    var isUsingRedeemptionPoints = ObservableField("0")
    var fRedeemPoint = ObservableField("0")
    var fRedeemAmount = ObservableField("0")
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
    fun categoryListApiCAll(){
        viewModelScope.launch {
            //passing parameter
            val map = HashMap<String, String>()
            map["nUserId"] = nUserId.get().toString()
            map["cToken"] = ""
            map["nLanguageId"] ="0"
            map["nCountryId"] = ""

            categoryListApi(map)
        }
    }
    fun productListApiCall(categoryId:String){
        viewModelScope.launch {
            val map = HashMap<String, String>()
            map["nUserId"] = nUserId.get().toString()
            map["cToken"] = ""
            map["nCategoryId"] = categoryId
            map["cFilterType"] = "" //--->temporary static
            map["nFromId"] = "1"
            map["nToId"] = "100"
            map["nBrandId"] = "0"
            map["nLanguageId"] = "0"
            map["cAttributeJsonData"] = ""
            map["nCountryId"] =""

            productListApi(map)
        }
    }
    fun placeOrderApiCall(){
        viewModelScope.launch {

            //passing parameter
            val map = HashMap<String, Any>()
            map["nUserId"] = nUserId.get().toString()
            map["cToken"] = ""
            map["nLanguageId"] ="0"
            map["nCountryId"] = ""
            map["nCustId"] = "12"
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
            map["cOrderType"] = "1"
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
            map["nTableId"] = nTableId.get().toString()
            map["nSectionId"] = nSectionId.get().toString()
            map["nEmployeeId"] = nEmployeeId.get().toString()
            map["IsUsingRedeemptionPoints"] = isUsingRedeemptionPoints.get().toString()
            map["fRedeemPoint"] = fRedeemPoint.get().toString()
            map["fRedeemAmount"] = fRedeemAmount.get().toString()
            Log.e("CreateOrderParam", map.toString())
            placeOrderApi(map)
        }
    }

    fun productListApi(orderListRequest: HashMap<String,String>)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.productList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    viewModelScope.launch {
                        _productList.postValue(it)
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
            }, orderListRequest,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ PRODUCT_LIST
        )
    }
    fun categoryListApi(orderListRequest: HashMap<String,String>)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.categoryList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    viewModelScope.launch {

                        _categoryList.postValue(it)
                        isNoDataTable.set(false)
                    }


                }else{
                    isNoDataTable.set(true)
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                isNoDataTable.set(true)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, orderListRequest,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ CATEGORY_LIST
        )
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
            }, orderListRequest,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ PLACE_ORDER
        )
    }

    fun addCustomerApiCall(){
        viewModelScope.launch {
            //passing parameter
            val map = HashMap<String, String>()
            map["nUserId"] = nUserId.get().toString()
            map["cCustomerFirstName"] = customerName.get().toString()
            map["cCustomerLastName"] =" "
            map["cCustomerContactNo"] = cMobileNo.get().toString()
            map["cCustomerEmailId"] = emailCustomer.get().toString()
            map["dtAnniversary"] =annivarsaryDate.get().toString()
            map["dtBirthDay"] = birthDate.get().toString()


            Log.e("CreateOrderParam", map.toString())
            addCustomerApi(map)
        }

    }
    fun addCustomerApi(orderListRequest: HashMap<String,String>)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.addCustomerApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.success == 1){
                    viewModelScope.launch {
                        _addCustomer.postValue(it)
                        // isNoDataSection.set(false)
                    }


                }else{
                    showToast(it.message.toString())
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                //isNoDataSection.set(true)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, orderListRequest,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ADD_CUSTOMER
        )
    }
    fun customerListApiCall(){
        viewModelScope.launch {
            //passing parameter
            val map = HashMap<String, String>()
            map["nUserId"] = nUserId.get().toString()
            map["cCustomerName"] = ""


            Log.e("CreateOrderParam", map.toString())
            customerListApi(map)
        }

    }

    fun customerListApi(orderListRequest: HashMap<String,String>)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.customerListApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.success == 1){
                    viewModelScope.launch {
                        _customerList.postValue(it)
                        // isNoDataSection.set(false)
                    }


                }else{
                    showToast(it.message.toString())
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                //isNoDataSection.set(true)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, orderListRequest,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ CUSTOMER_LIST
        )
    }
}