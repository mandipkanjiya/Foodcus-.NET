package com.mydia.restaurantsmartqr.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.OrderDetailItemAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityOrderDetailBinding
import com.mydia.restaurantsmartqr.model.orderList.Items
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.viewModel.VMOrderDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, VMOrderDetail>()  {

    private val TAG = OrderDetailActivity::class.java.simpleName

    lateinit var orderDetailItemAdapter: OrderDetailItemAdapter
    override fun getViewBinding() = ActivityOrderDetailBinding.inflate(layoutInflater)



    override fun onActivityCreated() {
        binding.vm = viewModel
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            if (intent.hasExtra("orderDetail")) {
                val orderList = intent.getParcelableExtra<OrderList>("orderDetail")
                viewModel.orderList.set(orderList)

                viewModel.setData()
                setAdapter(orderList!!.items)
            }
        }


        binding.imgBack.setOnClickListener{
            finish()
        }
    }

    override fun observeViewModel() {


        val statusType = viewModel.orderStatusType.get().toString()
        val orderType = viewModel.orderTypeId.get().toString()

        var status = ""
        if(statusType == "1"){
            status= "2"
        }else if(statusType == "2"){
            if(orderType == "2"){
                status ="4"
            }else{
                status= "3"
            }

        }else if(statusType =="3"){
            status ="4"
        }

        if(statusType == "4" || statusType == "5"){
            binding.rlCustomer.visibility = View.GONE
            binding.btnStatus.visibility = View.GONE
            binding.btnReject.visibility = View.GONE
            if(orderType == "2"){
                binding.llbottomViewpickup.visibility = View.GONE
            }else{
                binding.llbottomViewpickup.visibility = View.VISIBLE
                binding.tvPickUpTime.text = viewModel.pickupTime.get()
                binding.tvPaymentType.text = viewModel.paymentMethod.get()
            }


        }else{
            if(orderType == "2"){
                Log.e(TAG,"orderType$orderType")
                binding.btnStatus.visibility = View.VISIBLE
                binding.btnReject.visibility = View.VISIBLE
                binding.rlCustomer.visibility = View.GONE
                binding.llbottomViewpickup.visibility = View.GONE
            }else if(orderType == "1"){
                Log.e(TAG,"orderType$orderType")
                binding.btnStatus.visibility = View.VISIBLE
                binding.btnReject.visibility = View.VISIBLE
                binding.rlCustomer.visibility = View.VISIBLE
                binding.llbottomViewpickup.visibility = View.VISIBLE
                binding.tvPickUpTime.text = viewModel.pickupTime.get()
                binding.tvPaymentType.text = viewModel.paymentMethod.get()

            }
        }
        setStatusButtonBackground(viewModel.orderStatusType.get().toString(),orderType)
        binding.btnStatus.setOnClickListener {
            val orderStatusApi  = OrderStatusRequest(order_id = viewModel.orderId.get().toString(), norder_status = status)
            viewModel.orderStatusApi(orderStatusApi)
        }
        binding.btnReject.setOnClickListener {
            val orderStatusApi  = OrderStatusRequest(order_id = viewModel.orderId.get().toString(), norder_status = "5")
            viewModel.orderStatusApi(orderStatusApi)
        }

        lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->

                when (event) {
                    1 -> {
                        openNextActivity()
                    }

                }
            }
        }
    }

    override val viewModel:  VMOrderDetail by viewModels()


    private fun setAdapter(itemList:ArrayList<Items>){
        lifecycleScope.launch {
            orderDetailItemAdapter = OrderDetailItemAdapter(this@OrderDetailActivity, itemList,prefs.getString(PrefKey.CURRENCY))
            binding.rvOrderItem.adapter = orderDetailItemAdapter
        }
    }

    @SuppressLint("RestrictedApi")
    fun setStatusButtonBackground(orderstatus:String,orderTYpe:String){
        if(orderstatus == "4"){
            binding.btnStatus.text ="Complete"
            binding.btnStatus.supportBackgroundTintList = applicationContext.getColorStateList(R.color.darkGreen)
        }else if(orderstatus =="3"){
            binding.btnStatus.text ="Complete"
            binding.btnStatus.supportBackgroundTintList = applicationContext.getColorStateList(R.color.darkGreen)
        }else if(orderstatus =="2"){
            if(orderTYpe == "2"){
                binding.btnStatus.text ="Punched On POS"
                binding.btnStatus.supportBackgroundTintList = applicationContext.getColorStateList(R.color.darkGreen)
            }else{
                binding.btnStatus.text ="Ready to collect"
                binding.btnStatus.supportBackgroundTintList = applicationContext.getColorStateList(R.color.darkGreen)
            }


        } else if(orderstatus =="1"){
            binding.btnStatus.supportBackgroundTintList = applicationContext.getColorStateList(R.color.darkGreen)
            binding.btnStatus.text ="Accept"
        }


    }

    private fun openNextActivity(){
        startActivity(Intent(this,LiveOrderActivity::class.java))
    }
}