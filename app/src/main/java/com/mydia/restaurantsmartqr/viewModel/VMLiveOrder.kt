package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.login.LoginModel
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.util.Constants.ADD_POINT
import com.mydia.restaurantsmartqr.util.Constants.APP_VERSIONS
import com.mydia.restaurantsmartqr.util.Constants.BRANCH_LINK
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_STATUS_URL
import com.mydia.restaurantsmartqr.util.Constants.REDEEM_POINT
import com.mydia.restaurantsmartqr.util.Constants.REVIEW_LINK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMLiveOrder @javax.inject.Inject constructor(private val prefs: PreferencesServices,private  val loginRepository: LoginRepository) : BaseViewModel(){
    private val TAG = VMLiveOrder::class.java.simpleName
    var totalIncomingOrder = ObservableField("")
    var totalAcceptedOrder = ObservableField("")
    var userName = ObservableField("")
    var email = ObservableField("")
    var versionName = ObservableField("")
    var totalReadyOrder = ObservableField("")
    var isNoDataIncoming = ObservableBoolean(false)
    var isNoDataAccept = ObservableBoolean(false)
    var isNoDataReady = ObservableBoolean(false)

    private val _incomingOrder = MutableLiveData<OrderListResponse>()
    val incomingOrder: LiveData<OrderListResponse> = _incomingOrder

    private val _acceptedOrder = MutableLiveData<OrderListResponse>()
    val acceptedOrder: LiveData<OrderListResponse> = _acceptedOrder

    private val _readyOrder = MutableLiveData<OrderListResponse>()
    val readyOrder: LiveData<OrderListResponse> = _readyOrder

     val _sentLink = MutableLiveData<String>()
    val sentLinkData: LiveData<String> = _sentLink

    val _appupdateData = MutableLiveData<Boolean?>()
    val appupdateData: LiveData<Boolean?> = _appupdateData

    var incomingOrderPage = 0
    var perPage =10

    var acceptedOrderPage = 0
    var perPageAccept=10

    var readyOrderPage = 0
    var perPageReady=10

    fun getUserData(){
        viewModelScope.launch {
            val user = prefs.get(PrefKey.SAVE_LOGIN,LoginModel::class.java)
            userName.set(user!!.name)
            email.set(user.email)
        }
    }
    fun clearData(){
        viewModelScope.launch {
            prefs.clearData()
        }
    }

    fun allApiCall(){
        viewModelScope.launch {
            val offset = incomingOrderPage
            val incomingOrderRequest  = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "1", offset = "0", limit = "1000")
            incomingOrderListApi(incomingOrderRequest)

            val offset2 = acceptedOrderPage
            val acceptedOrderRequest  = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "2", offset ="0", limit = "1000")
            acceptedOrderListApi(acceptedOrderRequest)

            val readyOrderRequest  = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "3", offset = "0", limit = "1000")
            readyOrderListApi(readyOrderRequest)
        }
    }

   /* fun nextPageIncomingOrder(){
        viewModelScope.launch {
            incomingOrderPage++
            val offset = incomingOrderPage
            val incomingOrderRequest  = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "1", offset = offset.toString(), limit = perPage.toString())
            incomingOrderListApi(incomingOrderRequest)
        }
    }
    fun nextPageAcceptedOrder(){
        viewModelScope.launch {
            acceptedOrderPage++
            val offset = acceptedOrderPage
            val incomingOrderRequest  = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "1", offset = offset.toString(), limit = perPageAccept.toString())
            incomingOrderListApi(incomingOrderRequest)
        }
    }*/
    fun incomingOrderListApi(orderListRequest: OrderListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.orderList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){

                    totalIncomingOrder.set(it.total.toString())
                    _incomingOrder.postValue(it)
                    isNoDataIncoming.set(false)

                    Log.e(TAG+"deviceId", it.result[0].orderType.toString())

                }else{
                    _incomingOrder.postValue(it)
                    isNoDataIncoming.set(true)
                    totalIncomingOrder.set("0")
                   /* if (incomingOrderPage == 0){
                        isNoDataIncoming.set(true)

                    }else{
                        isNoDataIncoming.set(false)
                    }*/
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                isNoDataIncoming.set(true)
                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ORDER_LIST_URL
        )
    }

    fun acceptedOrderListApi(orderListRequest: OrderListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.orderList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    totalAcceptedOrder.set(it.total.toString())
                    _acceptedOrder.postValue(it)
                    isNoDataAccept.set(false)

                    Log.e(TAG+"deviceId", it.result[0].orderType.toString())

                }else{
                    _acceptedOrder.postValue(it)
                    isNoDataAccept.set(true)
                    totalAcceptedOrder.set("0")
                   /* if (acceptedOrderPage == 0){

                    }else{
                        isNoDataAccept.set(false)
                    }*/

                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                isNoDataAccept.set(true)
                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ORDER_LIST_URL
        )
    }
    fun readyOrderListApi(orderListRequest: OrderListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.orderList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    Log.e(TAG,"ready list ${it.result.size}")
                    //   totalReadyOrder.set(it.result.size.toString())
                    _readyOrder.postValue(it)
                    isNoDataReady.set(false)
                     Log.e(TAG+"deviceId", it.result[0].orderType.toString())

                }else{
                    _readyOrder.postValue(it)
                    isNoDataReady.set(true)
                  //  totalReadyOrder.set("0")
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                isNoDataReady.set(true)
                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ORDER_LIST_URL
        )
    }

    fun orderStatusApi(orderStatusRequest: OrderStatusRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.orderStatus(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    allApiCall()


                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)

                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderStatusRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ORDER_STATUS_URL
        )
    }

    fun reviewLinkApi(orderStatusRequest: ReviewAndBranchLinkRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.reviewLink(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    //allApiCall()
                    _sentLink.value = it.message.toString()

                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)

                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderStatusRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ REVIEW_LINK
        )
    }
    fun branchLinkApi(orderStatusRequest: ReviewAndBranchLinkRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.branchLink(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    //allApiCall()
                    _sentLink.value = it.message.toString()

                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)

                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderStatusRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ BRANCH_LINK
        )
    }

    fun addPointApi(orderStatusRequest: AddPointRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.addPointApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    //allApiCall()
                    _sentLink.value = it.message.toString()

                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)

                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderStatusRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ADD_POINT
        )
    }
    fun redeemPointApi(orderStatusRequest: RedeemPointRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.redeemPointApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    //allApiCall()
                    _sentLink.value = it.message.toString()

                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)

                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderStatusRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ REDEEM_POINT
        )
    }

    fun appUpdateApi()=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        var map = HashMap<String, String>()
        map.put("type","1")
        map.put("version",versionName.get().toString())
        loginRepository.appUpdateApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    //allApiCall()
                    _appupdateData.value = it.forceUpdate

                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)

                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, map,prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ APP_VERSIONS
        )
    }
}