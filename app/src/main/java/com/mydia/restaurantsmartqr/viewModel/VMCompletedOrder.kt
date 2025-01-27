package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse

import com.mydia.restaurantsmartqr.model.tableLIst.TableListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.TableListRequest
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.TABLE_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class VMCompletedOrder @javax.inject.Inject constructor(private val prefs: PreferencesServices,
                                                        private  val loginRepository: LoginRepository
) : BaseViewModel(){
    private val TAG = VMCompletedOrder::class.java.simpleName

    private val eventChannel = Channel<Int>()
    val eventFlow = eventChannel.receiveAsFlow()
    private val _completedOrderList = MutableLiveData<OrderListResponse>()
    val completedOrderList: LiveData<OrderListResponse> = _completedOrderList
    private val _tableList = MutableLiveData<TableListResponse>()
    val tableList: LiveData<TableListResponse> = _tableList

    var isNoData = ObservableBoolean(false)



    fun completedOrderListApi(orderListRequest: OrderListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.orderList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    viewModelScope.launch {

                        _completedOrderList.postValue(it)
                        isNoData.set(false)
                    }

                    Log.e("deviceId", it.result[0].orderType.toString())

                }else{
                    isNoData.set(true)
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                isNoData.set(true)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, orderListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ORDER_LIST_URL
        )
    }

    fun tableListApi(orderListRequest: TableListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.tableList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    viewModelScope.launch {

                        _tableList.postValue(it)
                        isNoData.set(false)
                    }


                }else{
                    isNoData.set(true)
                }

            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                isNoData.set(true)
                triggerShowMessage(it)
                Log.e("error",it.toString())
            }, orderListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ TABLE_LIST
        )
    }

}