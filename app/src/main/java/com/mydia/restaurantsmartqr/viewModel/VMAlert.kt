package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.alertList.AlertList
import com.mydia.restaurantsmartqr.model.login.LoginModel
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.AlertListRequest
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.repository.UpdateAlertRequest
import com.mydia.restaurantsmartqr.util.Constants.ADD_POINT
import com.mydia.restaurantsmartqr.util.Constants.ALERT_LIST
import com.mydia.restaurantsmartqr.util.Constants.APP_VERSIONS
import com.mydia.restaurantsmartqr.util.Constants.BRANCH_LINK
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_STATUS_URL
import com.mydia.restaurantsmartqr.util.Constants.PLACE_ORDER
import com.mydia.restaurantsmartqr.util.Constants.REDEEM_POINT
import com.mydia.restaurantsmartqr.util.Constants.REVIEW_LINK
import com.mydia.restaurantsmartqr.util.Constants.UPDATE_ALERT_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMAlert @javax.inject.Inject constructor(private val prefs: PreferencesServices, private  val loginRepository: LoginRepository) : BaseViewModel(){
    private val TAG = VMAlert::class.java.simpleName
    var totalIncomingOrder = ObservableField("")
    var totalAcceptedOrder = ObservableField("")
    var userName = ObservableField("")
    var email = ObservableField("")
    var versionName = ObservableField("")
    var totalReadyOrder = ObservableField("")
    var isNoDataIncoming = ObservableBoolean(false)
    var isProgressShowing = ObservableBoolean(false)
    var isNoDataAccept = ObservableBoolean(false)
    var isNoDataReady = ObservableBoolean(false)

    private val _alertList = MutableLiveData<ArrayList<AlertList>>()
    val alertList: LiveData<ArrayList<AlertList>> = _alertList

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
            val alertListRequest  = AlertListRequest(nTableId = "0", nUserId =prefs.getString(PrefKey.USER_ID))
            alertListApi(alertListRequest)
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


    fun alertListApi(alertListRequest: AlertListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)

        loginRepository.alertListApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.Success == "1"){
                    isNoDataIncoming.set(false)
                    viewModelScope.launch {
                        _alertList.value = it.result
                    }


                }else{
                    isLoading.set(false)
                    isNoDataIncoming.set(true)
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isNoDataIncoming.set(true)
                isLoading.set(false)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, alertListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ALERT_LIST
        )
    }

    fun updateAlertApi(alertListRequest: UpdateAlertRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.updateAlertApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.Success == "1"){
                    isNoDataIncoming.set(false)
                    viewModelScope.launch {
                        allApiCall()
                    }


                }else{
                    isNoDataIncoming.set(true)
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isNoDataIncoming.set(true)
                isLoading.set(false)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, alertListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ UPDATE_ALERT_LIST
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