package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_STATUS_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@HiltViewModel
class VMOrderDetail @javax.inject.Inject constructor(private val prefs: PreferencesServices,private  val loginRepository: LoginRepository) : BaseViewModel(){
    private val TAG = VMOrderDetail::class.java.simpleName

    private val eventChannel = Channel<Int>()
    val eventFlow = eventChannel.receiveAsFlow()

    var orderRef = ObservableField("")
    var totalItem = ObservableField("")
    var sectionName = ObservableField("")
    var orderType = ObservableField("")
    var orderTypeId = ObservableField("")
    var pickupTime = ObservableField("")
    var paymentMethod = ObservableField("")
    var tableName = ObservableField("")
    var tableTitle = ObservableField("")

    var orderTotal = ObservableField("")
    var orderSubTotal = ObservableField("")
    var tip = ObservableField("")
    var orderStatus = ObservableField("")
    var orderStatusType = ObservableField("")
    var orderId = ObservableField("")
    var customerName = ObservableField("")

    val orderList = ObservableField<OrderList>()
    var orderNote = ObservableField("")
    var isShowOrderNOte = ObservableBoolean(false)

    fun setData(){


        if(orderList.get()!!.tableName!!.isNotEmpty()){
            orderRef.set(orderList.get()!!.tableName)
        }else{
            orderRef.set("Order #"+orderList.get()!!.orderRef)
        }
        if(orderList.get()!!.items.size > 0){
            totalItem.set(orderList.get()!!.items.size.toString()+" Items")
        }else{
            totalItem.set(orderList.get()!!.items.size.toString()+" Item")
        }

        if(orderList.get()!!.orderType.equals("2")){
            //dinin
            customerName.set("")

        }else if(orderList.get()!!.orderType.equals("1")){
            customerName.set(orderList.get()!!.cutomerName)
        }

        orderType.set(orderList.get()!!.order_type_name)
        orderTypeId.set(orderList.get()!!.orderType)
        orderStatusType.set(orderList.get()!!.norderStatus)
        sectionName.set(orderList.get()!!.section_name)
        if(orderList.get()!!.corderNote!!.isNotEmpty()){
            isShowOrderNOte.set(true)
            orderNote.set(orderList.get()!!.corderNote)
        }else{
           isShowOrderNOte.set(false)
        }

        orderId.set(orderList.get()!!.nid)
        pickupTime.set(orderList.get()!!.pickupTime)
       var payment =""
        if(orderList.get()!!.paymentMethod == "1"){
            payment ="Cash"
        }else if(orderList.get()!!.paymentMethod == "2"){
            payment ="Card on pickup"
        }else{
            payment ="Credit Card"
        }
        paymentMethod.set(payment)


        viewModelScope.launch{
            val currency = prefs.getString(PrefKey.CURRENCY)

            val subTotal = (orderList.get()!!.corderTotal!!.toDouble() * 10.0).roundToInt() / 10.0
         //   val tipTotal = (orderList.get()!!.tip!!.toDouble() * 10.0).roundToInt() / 10.0
          //  val finalTotal = subTotal+ tip
            orderTotal.set(currency+subTotal.toString())
         //   orderSubTotal.set(currency+subTotal.toString())
         //   tip.set(currency+tipTotal.toString())
        }

        orderStatus.set(orderList.get()!!.orderStatusName.toString())

        if(orderList.get()!!.ntableId!!.isNotEmpty()){
            tableTitle.set("Table Name")
            tableName.set(orderList.get()!!.tableName)
        }/*else if(orderList.get()!!.co!!.isNotEmpty()){
            tableTitle.set("Counter Name")
            tableName.set(orderList.get()!!.co)
        }*/


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
                    viewModelScope.launch {
                        eventChannel.send(1)
                    }
                }
            }, onErrorAction = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                triggerShowMessage(it)
                Log.e(TAG+"error",it.toString())
            }, orderStatusRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+ ORDER_STATUS_URL
        )
    }

}