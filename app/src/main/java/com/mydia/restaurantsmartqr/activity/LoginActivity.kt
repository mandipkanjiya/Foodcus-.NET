package com.mydia.restaurantsmartqr.activity

import android.Manifest
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
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
import androidx.core.app.NotificationCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.hbb20.CountryCodePicker
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityLoginBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseDataManager
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.permission.OnRequestPermissionsCallBack
import com.mydia.restaurantsmartqr.permission.PermissionManager
import com.mydia.restaurantsmartqr.prefrences.PrefKey

import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.util.Constants.BASE_URL
import com.mydia.restaurantsmartqr.viewModel.VMLogin
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, VMLogin>() {

    private val TAG = LoginActivity::class.java.simpleName
    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override val viewModel:  VMLogin by viewModels()

    override fun onActivityCreated() {
        binding.vm = viewModel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionManager.Builder(this)
                .addPermissions(
                    arrayOf(
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                )
                .addRequestPermissionsCallBack(object : OnRequestPermissionsCallBack {
                    override fun onGrant() {
                        //showToast("Permission Accepted")

                    }

                    override fun onDenied(permission: String?) {
                        showToast("Please accept permission")
                    }
                }).build().request()
        }
        val notificationBuilder = NotificationCompat.Builder(this)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null) {
                val CHANNEL_ID = getString(R.string.channelid)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
                val alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName +"/" + R.raw.notification)
                val notificationChannel =
                    NotificationChannel(CHANNEL_ID, getString(R.string.app_name), importance)
                notificationBuilder.setChannelId(CHANNEL_ID)
                notificationChannel.setSound(alarmSound,attributes)
                val channelList: List<NotificationChannel> =
                    notificationManager.getNotificationChannels()
                var i = 0
                while (channelList != null && i < channelList.size) {
                    notificationManager.deleteNotificationChannel(channelList[i].id)
                    i++
                }
                notificationManager.createNotificationChannel(notificationChannel)
            }

        }

        binding.ivSettings.setOnClickListener {
            dialogSettings()
        }
    }


    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.eventFlow.collect { event ->

                when (event) {
                    1 -> {
                        openNextActivity()
                    }
                    2 -> {
                        openNextActivity()
                    }

                }
            }
        }
    }
    private fun openNextActivity(){

        startActivity(Intent(this,LiveOrderActivity::class.java)
            .putExtra("isShowReady",viewModel.isShowReady.get()))
        finish()
    }
    private fun openTableOrderActivity(){
        startActivity(Intent(this,TabelListActivity::class.java))
        finish()
    }
    fun dialogSettings() {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        val dialogRenameDoc =
            Dialog(this@LoginActivity)
        dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRenameDoc.setContentView(R.layout.dialog_set_baseurl)
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
        val tvDefaultUrl = dialogRenameDoc.findViewById<TextView>(R.id.tvDefaultUrl)
        //  val linAddItem = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linAddItem)
        //     val linStartDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linStartDate)
        //   val linEndDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linEndDate)
        val cvSubmit = dialogRenameDoc.findViewById<AppCompatButton>(R.id.btnSubmit)

        val etBaseUrl = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etBaseUrl)
        //val etMobileNumber = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etMobileNumber)
        lifecycleScope.launch {
            etBaseUrl.setText(prefs.getBaseUrl(PrefKey.BASE_URL).toString())
           // etBaseUrl.setText("https://tiffin.today/api_v1/")
        }

        val ivCancel = dialogRenameDoc.findViewById<ImageView>(R.id.ivBack)


        ivCancel!!.setOnClickListener {
            dialogRenameDoc.dismiss()

        }
        tvDefaultUrl!!.setOnClickListener {
            lifecycleScope.launch {
                prefs.putString(PrefKey.BASE_URL, "https://admin.foodcus.com")
                //BASE_URL = "https://mydia.ai/smart-qr/api_v1/"
                dialogRenameDoc.dismiss()
            }
        }
        cvSubmit!!.setOnClickListener {
            lifecycleScope.launch {

                if(etBaseUrl.text.toString().length == 0){
                    viewModel.showToast("Please enter API base URL.")
                    return@launch
                }
                if (!etBaseUrl.text.toString().startsWith("http://") && !etBaseUrl.text.toString().startsWith("https://")) {
                    viewModel.showToast("Please enter valid API base URL.")
                    return@launch
                }
                prefs.putString(PrefKey.BASE_URL, etBaseUrl.text.toString())
                //BASE_URL = etBaseUrl.text.toString()
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