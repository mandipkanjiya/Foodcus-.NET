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


        if(orderList.get()!!.cTableName!!.isNotEmpty()){
            orderRef.set(orderList.get()!!.cTableName)
        }else{
            orderRef.set("Order #"+orderList.get()!!.cOrderCode)
        }
     //   orderRef.set("Order #"+orderList.get()!!.cOrderCode)
        if(orderList.get()!!.Item.size > 0){
            totalItem.set(orderList.get()!!.Item.size.toString()+" Items")
        }else{
            totalItem.set(orderList.get()!!.Item.size.toString()+" Item")
        }

        if(orderList.get()!!.cOrderType.equals("DineIn")){
            //dinin
            customerName.set("")

        }else if(orderList.get()!!.cOrderType.equals("Pickup")){
            customerName.set(orderList.get()!!.cCustomerName)
        }

        orderType.set(orderList.get()!!.cOrderType)
      //  orderTypeId.set(orderList.get()!!.cOrderCode)
        orderStatusType.set(orderList.get()!!.cCurrentStatus)
        sectionName.set(orderList.get()!!.cSectionName)
        if(orderList.get()!!.cOrderNote!!.isNotEmpty()){
            isShowOrderNOte.set(true)
            orderNote.set(orderList.get()!!.cOrderNote)
        }else{
           isShowOrderNOte.set(false)
        }

        orderId.set(orderList.get()!!.nOrderId.toString())
        pickupTime.set(orderList.get()!!.cDeliveryTime)
       var payment =orderList.get()!!.cPaymentTerms
     /*   if(orderList.get()!!.cPaymentTerms == "1"){
            payment ="Cash"
        }else if(orderList.get()!!.cPaymentTerms == "2"){
            payment ="Card on pickup"
        }else{
            payment ="Credit Card"
        }*/
        paymentMethod.set(payment)


        viewModelScope.launch{
            val currency = prefs.getString(PrefKey.CURRENCY)

            val subTotal = (orderList.get()!!.fTotalPrice!! * 10.0).roundToInt() / 10.0
         //   val tipTotal = (orderList.get()!!.tip!!.toDouble() * 10.0).roundToInt() / 10.0
          //  val finalTotal = subTotal+ tip
            orderTotal.set(currency+subTotal.toString())
         //   orderSubTotal.set(currency+subTotal.toString())
         //   tip.set(currency+tipTotal.toString())
        }

        orderStatus.set(orderList.get()!!.cCurrentStatus.toString())

        if(orderList.get()!!.nEmployeeId!!.toString().isNotEmpty()){
            tableTitle.set("Table Name")
          //  tableName.set(orderList.get()!!.tableName)
        }/*else if(orderList.get()!!.co!!.isNotEmpty()){
            tableTitle.set("Counter Name")
            tableName.set(orderList.get()!!.co)
        }*/


    }

    fun orderStatusApi(orderStatusRequest: OrderStatusRequest)=viewModelScope.launch{
        triggerLoadingDetection(true)
        isLoading.set(true)
        loginRepository.orderStatusApi(
            scope = viewModelScope,
            onSuccess = {
                triggerLoadingDetection(false)
                isLoading.set(false)
                if(it!!.Success == "1"){
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