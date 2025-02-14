package com.mydia.restaurantsmartqr.activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
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
import androidx.lifecycle.lifecycleScope
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.AllAlertAdapter
import com.mydia.restaurantsmartqr.adapter.ItemTableListAadapter
import com.mydia.restaurantsmartqr.adapter.RedeemHistoryAdapter
import com.mydia.restaurantsmartqr.adapter.TableAlertAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityScanqrDetailBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.model.OrderDetailResult
import com.mydia.restaurantsmartqr.model.WalletHistoryDetailsItem
import com.mydia.restaurantsmartqr.model.tableLIst.TableList
import com.mydia.restaurantsmartqr.util.Utils
import com.mydia.restaurantsmartqr.viewModel.VMScanQrDetail
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ScanQrDetailActivity : BaseActivity<ActivityScanqrDetailBinding, VMScanQrDetail>(),FirebaseEventListener
{



    private val TAG = ScanQrDetailActivity::class.java.simpleName
    var orderId=""
    var orderAmount=""
    //var orderAmount=""

    override fun getViewBinding() = ActivityScanqrDetailBinding.inflate(layoutInflater)
    override val viewModel:  VMScanQrDetail by viewModels()
    override fun onActivityCreated() {
        binding.vm = viewModel
        viewModel.getUserData()

        val qrCodeData = intent.getStringExtra("QR_CODE_DATA")
        viewModel.nCustomerId.set(qrCodeData)

        // Display the scanned data

      //  binding.detailTextView.text = qrCodeData


        binding.btnCreateOrder.setOnClickListener {
           // dialogTransactionDetails()
            lifecycleScope.launch {
                viewModel.fTotal.set(binding.etAmount.text.toString())
                if (viewModel.fTotal.get() == "") {
                    Log.e("error", "Please enter email")
                    showToast("Please enter order total amount")
                    return@launch
                }
                /* if(binding.etBirthDate.text.toString().length == 0){
                 viewModel.showToast("Please select Name.")
                 return@launch
             }*/
                viewModel.cOrderType.set("3")

                viewModel.placeOrderApiCall()
            }
        }
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

    }
    fun setRedeempointHistoryAdapter(list:List<WalletHistoryDetailsItem>){
        //Log.e(TAG," incoming ${list.size}")
       val itemTableListAadapter = RedeemHistoryAdapter(this,list)
        binding.rvRedeemPoint.adapter = itemTableListAadapter

        itemTableListAadapter.setOnItemCLickListener(object : RedeemHistoryAdapter.OnItemClickListener{

            override fun onItemClick(datum: WalletHistoryDetailsItem, pos: Int) {
                dialogTransactionDetails(datum)
            }

        })
    }


    override fun observeViewModel() {
        viewModel.getCustomerWalletHistoryApiCall()
        viewModel.customerWalletDetailHistory.observe(this){
           // if (it.status == 1){
                setRedeempointHistoryAdapter(it.walletHistoryDetails)
            viewModel.nCustomerId.set(it?.nCustomerId.toString())
            viewModel.emailCustomer.set(it?.cCustomerEmail)
            viewModel.customerName.set(it?.cCustomerName)
            viewModel.cMobileNo.set(it?.cCustomerMobileNo)
            binding.tvCustomerName.text = it?.cCustomerName
            binding.tvProfileEmail.text = it?.cCustomerEmail
            binding.tvcontactNo.text = it?.cCustomerMobileNo
            binding.tvAvailablePoint.text = it.fTotalWalletPoints.toString()
            //if(it.isAligibleForPointRedemption ==)

            if(it.isAligibleForPointRedemption == true) {
                viewModel.isUsingRedeemptionPoints.set("1")
                viewModel.fRedeemPoint.set(it.fTotalWalletPoints.toString())
                viewModel.fRedeemAmount.set(it.fTotalWalletAmount.toString())
            }else{
                viewModel.isUsingRedeemptionPoints.set("0")
                viewModel.fRedeemPoint.set("0")
                viewModel.fRedeemAmount.set("0")
            }
               // viewModel.tableListApiCAll(it.result.get(0).nSectionId.toString())
                //setSectionAdapter(getItemList())
        }
        viewModel.placeorder.observe(this){
            if (it.success == 1){
                dialogTransactionDetails(it.result!!)
                //showToast("Order placed successfully")
               // finish()
            }
        }

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
    fun dialogTransactionDetails(datum: WalletHistoryDetailsItem) {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        val dialogRenameDoc = Dialog(this@ScanQrDetailActivity)
        dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRenameDoc.setContentView(R.layout.dialog_transaction_details)
        dialogRenameDoc.setCancelable(true)
        dialogRenameDoc.setCanceledOnTouchOutside(true)
        //dialogRenameDoc.window?.setGravity(Gravity.BOTTOM)
        //dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
        dialogRenameDoc.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogRenameDoc.setOnDismissListener {
            //  getViewModel().breakTypeId.set("")
            //getViewModel().breakTypeName.set("")
        }
        val tvTitle = dialogRenameDoc.findViewById<TextView>(R.id.tvTitle)

        //  val linAddItem = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linAddItem)
        //     val linStartDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linStartDate)
        //   val linEndDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linEndDate)

        val tvEarnPoint = dialogRenameDoc.findViewById<TextView>(R.id.tvEarnPoint)
        val tvTransactionnumber = dialogRenameDoc.findViewById<TextView>(R.id.tvTransactionnumber)
        val tvCustomerName = dialogRenameDoc.findViewById<TextView>(R.id.tvCustomerName)
        val tvTotalAmount = dialogRenameDoc.findViewById<TextView>(R.id.tvTotalAmount)
        val tvSavingsAmount = dialogRenameDoc.findViewById<TextView>(R.id.tvSavingsAmount)
        val tvDate = dialogRenameDoc.findViewById<TextView>(R.id.tvDate)
        val tvStoreName = dialogRenameDoc.findViewById<TextView>(R.id.tvStoreName)

       tvDate.text = Utils.convertWalleteHistoryDate(datum.dtOrderDate)
        //tvCustomerName.text = datum..toString()
        tvTransactionnumber.text = datum.nQuotationMasterId.toString()
        tvEarnPoint.text = datum.fOrderEarnedPoints.toString()
        tvTotalAmount.text = "$"+" "+datum.fOrderAmount.toString()
        tvSavingsAmount.text = "$"+" "+datum.fOrderSavings.toString()

     //   tvEarnPoint.setText(viewModel.customerName.get().toString())
       // tvTransactionnumber.setText(viewModel.cMobileNo.get().toString())

        val ivCancel = dialogRenameDoc.findViewById<ImageView>(R.id.ivBack)
        ivCancel!!.setOnClickListener {
            dialogRenameDoc.dismiss()

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