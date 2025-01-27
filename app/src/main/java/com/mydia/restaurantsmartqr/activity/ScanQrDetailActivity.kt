package com.mydia.restaurantsmartqr.activity

import android.R
import android.widget.TextView
import androidx.activity.viewModels
import com.mydia.restaurantsmartqr.adapter.AllAlertAdapter
import com.mydia.restaurantsmartqr.adapter.TableAlertAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityScanqrDetailBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.viewModel.VMScanQrDetail
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScanQrDetailActivity : BaseActivity<ActivityScanqrDetailBinding, VMScanQrDetail>(),FirebaseEventListener
{



    private val TAG = ScanQrDetailActivity::class.java.simpleName

    override fun getViewBinding() = ActivityScanqrDetailBinding.inflate(layoutInflater)
    override val viewModel:  VMScanQrDetail by viewModels()
    override fun onActivityCreated() {
        binding.vm = viewModel


        val qrCodeData = intent.getStringExtra("QR_CODE_DATA")


        // Display the scanned data

      //  binding.detailTextView.text = qrCodeData

    }

    override fun onResume() {
        super.onResume()

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

}