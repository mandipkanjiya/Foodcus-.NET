package com.mydia.restaurantsmartqr.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.hbb20.CountryCodePicker
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.AcceptedOrderAdapter
import com.mydia.restaurantsmartqr.adapter.AllAlertAdapter
import com.mydia.restaurantsmartqr.adapter.IncomingOrderAdapter
import com.mydia.restaurantsmartqr.adapter.ReadyOrderAdapter
import com.mydia.restaurantsmartqr.adapter.TableAlertAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityAlertBinding
import com.mydia.restaurantsmartqr.databinding.ActivityLiveOrderBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseDataManager
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.model.alertList.AlertList
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.AlertListRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.repository.UpdateAlertRequest
import com.mydia.restaurantsmartqr.viewModel.VMAlert
import com.mydia.restaurantsmartqr.viewModel.VMLiveOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlertActivity : BaseActivity<ActivityAlertBinding, VMAlert>(),FirebaseEventListener
{

    lateinit var alertListAdapter: AllAlertAdapter

    private val TAG = AlertActivity::class.java.simpleName

    override fun getViewBinding() = ActivityAlertBinding.inflate(layoutInflater)
    override val viewModel:  VMAlert by viewModels()
    override fun onActivityCreated() {
        binding.vm = viewModel

        FirebaseDataManager.firebaseDataReference.firebaseEventListener = this

        viewModel.allApiCall()
        drawerClickListner()

     //   playSound()

        try {
            // Register receiver
            LocalBroadcastManager.getInstance(this!!)
                .registerReceiver(receiver, IntentFilter("NOTIFICATION_DATA"))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }


    }

    override fun onResume() {
        super.onResume()

    }
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
           val  orderId = intent.getStringExtra("orderId").toString()
            if(orderId.isNotEmpty()){

            }
        }
    }

    private fun drawerClickListner(){
        binding.imgMenu.setOnClickListener {
            openDrawer()
        }
        binding.imgHome.setOnClickListener {
            startActivity(Intent(this,LiveOrderActivity::class.java))
        }

    }
    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }
    override fun observeViewModel() {
        viewModel.alertList.observe(this){
            if(it.isNotEmpty()){
                binding.tvCount.text = it.size.toString()
                setAlertAdapter(it)
            }
        }

    }
    fun setAlertAdapter(list:ArrayList<AlertList>){
        Log.e(TAG," alertList ${list.size}")
        alertListAdapter = AllAlertAdapter(this,list)
        binding.rvAllAlert.adapter = alertListAdapter
        alertListAdapter.setOnItemCLickListener(object: AllAlertAdapter.OnItemClickListener{
            override fun onItemAcceptClick(datum: AlertList?, pos: Int) {
                val updateListRequest  = UpdateAlertRequest(nRestaurantLogId = datum!!.nRestaurantLogId.toString(), nMarkCompleted = "1")
               viewModel.updateAlertApi(updateListRequest)
            }

        })


    }
    override fun onRebootCommand() {

    }

    override fun onRestartCommand() {

    }

    override fun onContentUpdateCommand() {


       // showNewOrderDialog()
    }

    override fun onQueueUpdateCommand() {

    }

    override fun onClearCatchCommand() {

    }

    override fun onPOSOrderCountCommand() {

    }


}