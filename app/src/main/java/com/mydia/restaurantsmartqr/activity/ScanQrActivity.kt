package com.mydia.restaurantsmartqr.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import com.google.zxing.integration.android.IntentIntegrator
import com.mydia.restaurantsmartqr.adapter.AllAlertAdapter
import com.mydia.restaurantsmartqr.adapter.TableAlertAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityScanqrBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseEventListener
import com.mydia.restaurantsmartqr.viewModel.VMScanQr
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ScanQrActivity : BaseActivity<ActivityScanqrBinding, VMScanQr>(),FirebaseEventListener
{

    private val TAG = ScanQrActivity::class.java.simpleName

    override fun getViewBinding() = ActivityScanqrBinding.inflate(layoutInflater)
    override val viewModel:  VMScanQr by viewModels()
    override fun onActivityCreated() {
        binding.vm = viewModel



       binding.scanButton.setOnClickListener {
           startQrScanner()
       }

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
    private fun startQrScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Only QR codes
        integrator.setPrompt("Scan a QR Code")
        integrator.setCameraId(0) // Use a specific camera of the device
        integrator.setBeepEnabled(true)
        integrator.setBarcodeImageEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // Pass the scanned data to the DetailActivity
                val intent = Intent(this, ScanQrDetailActivity::class.java)
                intent.putExtra("QR_CODE_DATA", result.contents)
                startActivity(intent)
            } else {
                // Handle scan cancellation
            }
        }
    }

}