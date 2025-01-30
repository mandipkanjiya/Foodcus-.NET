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
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.hbb20.CountryCodePicker
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.AcceptedOrderAdapter
import com.mydia.restaurantsmartqr.adapter.IncomingOrderAdapter
import com.mydia.restaurantsmartqr.adapter.ReadyOrderAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityLiveOrderBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseDataManager
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.viewModel.VMLiveOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LiveOrderActivity : BaseActivity<ActivityLiveOrderBinding, VMLiveOrder>(),FirebaseEventListener
{

    var manualOrder="https://admin.foodcus.com/?branch_id=U2sremxXRnZ1dldpelhRRy9rTkVzQT09"
    lateinit var incomingOrderAdapter: IncomingOrderAdapter
    lateinit var acceptedOrderAdapter: AcceptedOrderAdapter
    lateinit var readyOrderAdapter: ReadyOrderAdapter

    private val TAG = LiveOrderActivity::class.java.simpleName

    override fun getViewBinding() = ActivityLiveOrderBinding.inflate(layoutInflater)

    override fun onActivityCreated() {
        binding.vm = viewModel
        FirebaseDataManager.firebaseDataReference.firebaseEventListener = this
        val ss:String = intent.getStringExtra("isShowReady").toString()
        val orderId:String = intent.getStringExtra("orderId").toString()
        Log.e(TAG, "Notification id " + orderId)
        if(orderId.isNotEmpty()){
            viewModel.allApiCall()
         }
        viewModel.getUserData()
        binding.dLayout.llLiveOrder.isSelected = true
        drawerClickListner()
        //dialogAlertOpen()
        viewModel.allApiCall()
     //   playSound()
        if(ss == "1"){
            binding.llReady.visibility = View.VISIBLE
        }else{
            binding.llReady.visibility = View.GONE
        }
        try {
            // Register receiver
            LocalBroadcastManager.getInstance(this!!)
                .registerReceiver(receiver, IntentFilter("NOTIFICATION_DATA"))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        var selectedBaseUrl1=""
        lifecycleScope.launch {
            selectedBaseUrl1 = prefs.getBaseUrl(PrefKey.BASE_URL).toString()
            if(selectedBaseUrl1.equals("https://admin.foodcus.com"))
            {
                binding.linSentMessage.visibility = View.VISIBLE
                binding.ivLogo.visibility = View.GONE
            }else{
                binding.linSentMessage.visibility = View.VISIBLE
                binding.ivLogo.visibility = View.GONE
            }

        }
        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName,0)
            viewModel.versionName.set(pInfo.versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        /*  binding.imgBack.setOnClickListener{
              finish()
          }*/


    }

    override fun onResume() {
        super.onResume()
     //   viewModel.appUpdateApi()
    }
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
           val  orderId = intent.getStringExtra("orderId").toString()
            if(orderId.isNotEmpty()){
                viewModel.allApiCall()
            }
        }
    }

    private fun drawerClickListner(){
        binding.imgMenu.setOnClickListener {
            openDrawer()
        }
        binding.dLayout.imgClose.setOnClickListener{
            closeDrawer()
        }
        binding.dLayout.llDashboard.setOnClickListener {
            closeDrawer()
        }
        binding.dLayout.llLiveOrder.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,LiveOrderActivity::class.java))
            finish()
        }
        binding.dLayout.llOrderCompleted.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,OrderHistoryActivity::class.java))
        }
        binding.ivAlerts.setOnClickListener {
            startActivity(Intent(this,AlertActivity::class.java))
        }
        binding.ivOrderHistory.setOnClickListener {
            startActivity(Intent(this,OrderHistoryActivity::class.java))
        }
        binding.dLayout.llAlerts.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,AlertActivity::class.java))
        }
        binding.dLayout.llManualOrder.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,WebviewActivity::class.java).putExtra("weburl",manualOrder).putExtra("cTitle","Manual Order"))
        }
        binding.dLayout.llAnalytics.setOnClickListener {
            closeDrawer()
        }
        binding.dLayout.llLogout.setOnClickListener {
            closeDrawer()
            showLogoutDialog()
        }
        binding.ivOrderLink.setOnClickListener {
            dialogStartBreak(1)
        }
        binding.ivReview.setOnClickListener {
            dialogStartBreak(2)
        }
        binding.ivLogout.setOnClickListener {
            showLogoutDialog()
        }
        binding.ivRedeemPoint.setOnClickListener {
            startActivity(Intent(this,ScanQrActivity::class.java))
            //dialogStartBreak(4)
        }
        binding.ivSentPoint.setOnClickListener {
            dialogStartBreak(3)
        }

    }
    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }
    override fun observeViewModel() {
        viewModel.incomingOrder.observe(this){
            if(it.result.isNotEmpty()){

                setIncomingAdapter(it.result)
            }
        }
        viewModel.acceptedOrder.observe(this){
            if(it.result.isNotEmpty()){

                setAcceptedAdapter(it.result)
            } else{

            }
        }

        viewModel.readyOrder.observe(this){
            if(!it.result.isNullOrEmpty()){
                Log.e(TAG," ready order")
                binding.llReady.visibility = View.VISIBLE
                setReadyAdapter(it.result)
            }else{
                binding.llReady.visibility = View.GONE
            }
        }
        viewModel.appupdateData.observe(this, Observer {
            if(it!=null){
                if (it == true) {
                    dialogAppUpdate()
                }
            }
            /*if (it != null) {
                if (it.getIsUpdate() === 1) {
                    dialogAppUpdate()
                }
            }*/
        })
    }

    override val viewModel:  VMLiveOrder by viewModels()

    fun setIncomingAdapter(list:ArrayList<OrderList>){
        Log.e(TAG," incoming ${list.size}")
        incomingOrderAdapter = IncomingOrderAdapter(this,list)
        binding.rvIncomingOrder.adapter = incomingOrderAdapter

        incomingOrderAdapter.setOnItemCLickListener(object : IncomingOrderAdapter.OnItemClickListener{
            override fun onItemAcceptClick(datum: OrderList?, pos: Int) {
                var title = "Are you sure you want to accept this order?"
                showAcceptRejectDialog(title, datum!!.nOrderId.toString(),"2")


            }


            override fun onItemRejectClick(datum: OrderList?, pos: Int) {
                var title = "Are you sure you want to reject this order?"

                showAcceptRejectDialog(title, datum!!.nOrderId.toString(),"5")

            }

        })
    }

    fun setReadyAdapter(list:ArrayList<OrderList>){
        Log.e(TAG," ready order adapter")
        val pickupOrderList = list
        if(pickupOrderList.isNotEmpty()){
            Log.e(TAG,"order${pickupOrderList.size}")
            viewModel.totalReadyOrder.set(pickupOrderList.size.toString())
            binding.llReady.visibility = View.VISIBLE
            readyOrderAdapter = ReadyOrderAdapter(this, list)
            binding.rvReadyOrder.adapter = readyOrderAdapter
        }else{
            Log.e(TAG,"order${pickupOrderList.size}")
            binding.llReady.visibility = View.GONE
        }


    }
    fun setAcceptedAdapter(list:ArrayList<OrderList>){
        Log.e(TAG," accept ${list.size}")
        acceptedOrderAdapter = AcceptedOrderAdapter(this,list)
        binding.rvAcceptedOrder.adapter = acceptedOrderAdapter
        acceptedOrderAdapter.setOnItemCLickListener(object : AcceptedOrderAdapter.OnItemClickListener{

            override fun onItemAcceptClick(datum: OrderList?, pos: Int, orderType: String) {

                if(orderType.equals("DineIn")){
                    var title = "Are you sure this order is punched on the POS?"
                    showAcceptRejectDialog(title, datum!!.nOrderId.toString(),"4")
                    //dinin
                }else if(orderType.equals("Pickup")){
                    var title = "Are you sure this order is prepared?"
                    showAcceptRejectDialog(title, datum!!.nOrderId.toString(),"3")
                }

            }
        })
    }

    private fun showAcceptRejectDialog(title:String,orderId:String,orderStatus:String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage(title)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    lifecycleScope.launch {
                        val orderStatusApi  = OrderStatusRequest(nUserId =prefs.getString(PrefKey.USER_ID), nOrderId = orderId, cStatus = orderStatus)
                        viewModel.orderStatusApi(orderStatusApi)
                    }

                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
            .show()
    }
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    var selectedBaseUrl=""
                    lifecycleScope.launch {
                         selectedBaseUrl = prefs.getBaseUrl(PrefKey.BASE_URL).toString()
                    }
                    viewModel.clearData()
                    val intent = Intent(this, SplashActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK // To clean up all activities

                    startActivity(intent)
                    lifecycleScope.launch {
                        prefs.putString(PrefKey.BASE_URL, selectedBaseUrl)
                    }

                    finish()
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
            .show()
    }
    private fun showNewOrderDialog()=lifecycleScope.launch(Dispatchers.Main) {
        AlertDialog.Builder(this@LiveOrderActivity)
            .setTitle(getString(R.string.app_name))
            .setMessage("New Order")
            .setPositiveButton("OK",
                DialogInterface.OnClickListener { dialog, which ->



                })

            .show()
    }

    override fun onRebootCommand() {

    }

    override fun onRestartCommand() {

    }

    override fun onContentUpdateCommand() {

        var resID = resources.getIdentifier("notification", "raw", packageName)
        val mediaPlayer = MediaPlayer.create(this, resID)
        mediaPlayer.start()

        viewModel.allApiCall()
       // showNewOrderDialog()
    }

    override fun onQueueUpdateCommand() {

    }

    override fun onClearCatchCommand() {

    }

    override fun onPOSOrderCountCommand() {

    }
    private fun dialogAlertOpen() {
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_alert_open, null)

// Initialize views in the custom layout
        val dialogTitle = dialogView.findViewById<TextView>(R.id.tvTitle)
        val dialogInput = dialogView.findViewById<AppCompatTextView>(R.id.tvTitle1)
        val dialogButton = dialogView.findViewById<AppCompatTextView>(R.id.tvTitle2)

// Build the dialog
        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()




// Show the dialog
        customDialog.show()


    }
    private fun dialogStartBreak(type:Int) {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        viewModel._sentLink.value = null
        val dialogRenameDoc =
            Dialog(this@LiveOrderActivity)
        dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRenameDoc.setContentView(R.layout.dialog_sent_order_link)
        dialogRenameDoc.setCancelable(true)
        dialogRenameDoc.setCanceledOnTouchOutside(true)
        //dialogRenameDoc.window?.setGravity(Gravity.BOTTOM)
        //dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        dialogRenameDoc.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRenameDoc.setOnDismissListener {
            //  getViewModel().breakTypeId.set("")
            //getViewModel().breakTypeName.set("")
        }
        val tvtitleBreak = dialogRenameDoc.findViewById<TextView>(R.id.tvTitle)
        //  val linAddItem = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linAddItem)
        //     val linStartDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linStartDate)
        //   val linEndDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linEndDate)
        val cvSubmit = dialogRenameDoc.findViewById<AppCompatButton>(R.id.btnSubmit)
        val ccpGetNumber = dialogRenameDoc.findViewById<CountryCodePicker>(R.id.ccp_getFullNumber)
        val etAmount = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etAmount)
        val linAmount = dialogRenameDoc.findViewById<LinearLayout>(R.id.linAmount)
        val etMobileNumber = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etMobileNumber)
        ccpGetNumber.registerCarrierNumberEditText(etMobileNumber)

if(type == 1){
    tvtitleBreak?.text ="Order Url"
    linAmount.visibility = View.GONE
}else if(type==2)
{
    tvtitleBreak?.text ="Give Review"
    linAmount.visibility = View.GONE
}else if(type==3)
{
    tvtitleBreak?.text ="Give point"
    linAmount.visibility = View.VISIBLE
}else if(type==4)
{
    tvtitleBreak?.text ="Redeem point"
    linAmount.visibility = View.VISIBLE
}


        val ivCancel = dialogRenameDoc.findViewById<ImageView>(R.id.ivBack)
        viewModel.sentLinkData.observe(this, Observer {
            if(it!=null){
                viewModel.showToast(it.toString())
            }
        })

        ivCancel!!.setOnClickListener {
            Log.e("ccpGetNumber.fullNumber",ccpGetNumber.fullNumber)
            Log.e("ccpGetNumber.fullNumberWithPlus",ccpGetNumber.fullNumberWithPlus)
            Log.e("ccpGetNumber.formattedFullNumber",ccpGetNumber.formattedFullNumber)
            Log.e("ccpGetNumber.selectedCountryCode",ccpGetNumber.selectedCountryCode)
            dialogRenameDoc.dismiss()

        }
        cvSubmit!!.setOnClickListener {
            lifecycleScope.launch {
                if(type==3 || type==4) {
                    if (etAmount.text.toString().length == 0) {
                        viewModel.showToast("Please enter point")
                        return@launch
                    }
                }
                if(etMobileNumber.text.toString().length == 0){
                    viewModel.showToast("Please enter mobile number")
                    return@launch
                }
                if(type == 1) {
                    val reviewAndBranchLinkRequest = ReviewAndBranchLinkRequest(
                        branch_id = prefs.getString(
                            PrefKey.BRANCH_ID
                        ), mobile = ccpGetNumber.fullNumberWithPlus.toString()
                    )
                    viewModel.branchLinkApi(reviewAndBranchLinkRequest)
                }else if(type==2)
                {
                    val reviewAndBranchLinkRequest = ReviewAndBranchLinkRequest(
                        branch_id = prefs.getString(
                            PrefKey.BRANCH_ID
                        ), mobile = ccpGetNumber.fullNumberWithPlus.toString()
                    )
                    viewModel.reviewLinkApi(reviewAndBranchLinkRequest)
                }else if(type==3)
                {
                    val addPointRequest = AddPointRequest(
                        branch_id = prefs.getString(
                            PrefKey.BRANCH_ID
                        ), mobile = ccpGetNumber.fullNumberWithPlus.toString(),etAmount.text.toString()
                    )
                    viewModel.addPointApi(addPointRequest)
                }else if(type==4)
                {
                    val redeemPointRequest = RedeemPointRequest(
                        branch_id = prefs.getString(
                            PrefKey.BRANCH_ID
                        ), mobile = ccpGetNumber.fullNumberWithPlus.toString(),etAmount.text.toString()
                    )
                    viewModel.redeemPointApi(redeemPointRequest)
                }
                dialogRenameDoc.dismiss()
            }
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogRenameDoc.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.BOTTOM
        lp.windowAnimations = R.style.DialogAnimation
        dialogRenameDoc.show()

    }
    private fun dialogAppUpdate() {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        //viewModel._sentLink.value = null
        val dialogRenameDoc =
            Dialog(this@LiveOrderActivity)
        dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRenameDoc.setContentView(R.layout.dialog_app_update)
        dialogRenameDoc.setCancelable(false)
        dialogRenameDoc.setCanceledOnTouchOutside(false)
        //dialogRenameDoc.window?.setGravity(Gravity.BOTTOM)
        //dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        dialogRenameDoc.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRenameDoc.setOnDismissListener {
            //  getViewModel().breakTypeId.set("")
            //getViewModel().breakTypeName.set("")
        }
        val tvtitleBreak = dialogRenameDoc.findViewById<TextView>(R.id.tvTitle)
        val tvDescription = dialogRenameDoc.findViewById<TextView>(R.id.tvDescription)
        //  val linAddItem = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linAddItem)
        //     val linStartDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linStartDate)
        //   val linEndDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linEndDate)
        val cvSubmit = dialogRenameDoc.findViewById<AppCompatButton>(R.id.btnUpdate)


        cvSubmit!!.setOnClickListener {
            lifecycleScope.launch {
                redirectStore()
                dialogRenameDoc.dismiss()
            }
        }

        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialogRenameDoc.window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.MATCH_PARENT
        lp.gravity = Gravity.BOTTOM
        lp.windowAnimations = R.style.DialogAnimation
        dialogRenameDoc.show()

    }
    private fun redirectStore() {
        /*final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);*/
        val appPackageName: String = packageName.toString() // getPackageName() from Context or Activity object
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appPackageName")
                )
            )
        } catch (anfe: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")
                )
            )
        }
    }
}