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
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.viewModel.VMAlert
import com.mydia.restaurantsmartqr.viewModel.VMLiveOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AlertActivity : BaseActivity<ActivityAlertBinding, VMAlert>(),FirebaseEventListener
{

    var manualOrder="https://admin.foodcus.com/?branch_id=U2sremxXRnZ1dldpelhRRy9rTkVzQT09"
    lateinit var allAlertAdapter: AllAlertAdapter
    lateinit var tableAlertAdapter: TableAlertAdapter


    private val TAG = AlertActivity::class.java.simpleName

    override fun getViewBinding() = ActivityAlertBinding.inflate(layoutInflater)
    override val viewModel:  VMAlert by viewModels()
    override fun onActivityCreated() {
        binding.vm = viewModel
        viewModel.getUserData()
        FirebaseDataManager.firebaseDataReference.firebaseEventListener = this
        val ss:String = intent.getStringExtra("isShowReady").toString()
        val orderId:String = intent.getStringExtra("orderId").toString()
        Log.e(TAG, "Notification id " + orderId)


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
      /*  binding.dLayout.imgClose.setOnClickListener{
            closeDrawer()
        }
        binding.dLayout.llDashboard.setOnClickListener {
            closeDrawer()
        }
        binding.dLayout.llLiveOrder.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,AlertActivity::class.java))
            finish()
        }
        binding.dLayout.llOrderCompleted.setOnClickListener {
            closeDrawer()
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
        }*/
        binding.ivOrderLink.setOnClickListener {
            dialogStartBreak(1)
        }
        binding.ivReview.setOnClickListener {
            dialogStartBreak(2)
        }
        binding.ivRedeemPoint.setOnClickListener {
            dialogStartBreak(4)
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
    private fun dialogStartBreak(type:Int) {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        viewModel._sentLink.value = null
        val dialogRenameDoc =
            Dialog(this)
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
            tvtitleBreak?.text ="Redeam point"
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
}