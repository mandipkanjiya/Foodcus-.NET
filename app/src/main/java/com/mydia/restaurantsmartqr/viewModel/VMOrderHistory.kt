package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.login.LoginModel
import com.mydia.restaurantsmartqr.model.login.NewLoginModel
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionListResponse

import com.mydia.restaurantsmartqr.model.tableLIst.TableListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.TableListRequest
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.SECTION_LIST
import com.mydia.restaurantsmartqr.util.Constants.TABLE_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMOrderHistory @javax.inject.Inject constructor(private val prefs: PreferencesServices,
                                                      private  val loginRepository: LoginRepository
) : BaseViewModel(){

    private val TAG = VMOrderHistory::class.java.simpleName


    private val _completedOrderList = MutableLiveData<OrderListResponse>()
    val completedOrderList: LiveData<OrderListResponse> = _completedOrderList
    private val _tableList = MutableLiveData<TableListResponse>()
    val tableList: LiveData<TableListResponse> = _tableList

    private val _sectionList = MutableLiveData<SectionListResponse>()
    val sectionList: LiveData<SectionListResponse> = _sectionList
    var nUserId = ObservableField("")
    var isNoData = ObservableBoolean(false)
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
    fun orderListUsingFilter(status:String,tableId:String,sectionId:String,startDate:String,endDate:String){
        viewModelScope.launch {
            val orderListRequest = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = status, offset = "0", limit = "1000", table_id = tableId,start_date = startDate, end_date = endDate, sectionId = sectionId)
            completedOrderListApi(orderListRequest)
        }

    }
    fun tableListApiCAll(sectionId:String){
        viewModelScope.launch {
            val orderListRequest = TableListRequest(nUserId = nUserId.get().toString(), nTableId = "0", nSection = sectionId)
            tableListApi(orderListRequest)
        }
    }
    fun sectionListApiCall(){
        viewModelScope.launch {
            val orderListRequest = TableListRequest(nUserId = nUserId.get().toString())
            sectionListApi(orderListRequest)
        }
    }

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


    fun sectionListApi(orderListRequest: TableListRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.sectionList(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.status == 1){
                    viewModelScope.launch {
                        _sectionList.postValue(it)
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
            }, orderListRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ SECTION_LIST
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