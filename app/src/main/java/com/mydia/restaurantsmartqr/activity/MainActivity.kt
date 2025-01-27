package com.mydia.restaurantsmartqr.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityMainBinding
import com.mydia.restaurantsmartqr.viewModel.VMMain
import dagger.hilt.android.AndroidEntryPoint
import org.checkerframework.checker.units.qual.A

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, VMMain>()  {

    var manualOrder="https://admin.foodcus.com/?branch_id=U2sremxXRnZ1dldpelhRRy9rTkVzQT09"

    private val TAG = MainActivity::class.java.simpleName

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun observeViewModel() {

    }

    override fun onActivityCreated() {
        binding.vm = viewModel
        viewModel.getUserData()
        binding.dLayout.llLiveOrder.isSelected = true
        drawerClickListner()
    }

    override val viewModel:  VMMain by viewModels()



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
        }
        binding.dLayout.llOrderCompleted.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,CompletedOrderActivity::class.java))
        }
        binding.dLayout.llAlerts.setOnClickListener {
            closeDrawer()
            startActivity(Intent(this,AlertActivity::class.java))
        }

        binding.dLayout.llAnalytics.setOnClickListener {
            closeDrawer()
        }
        binding.dLayout.llLogout.setOnClickListener {
            closeDrawer()
            showLogoutDialog()
        }

    }
    private fun openDrawer() {
        binding.drawerLayout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        binding.drawerLayout.closeDrawers()
    }

    private fun openLiveOrder(){

    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_name))
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                   viewModel.clearData()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK // To clean up all activities

                    startActivity(intent)
                    finish()
                })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialogInterface, i -> dialogInterface.dismiss() })
            .show()
    }
}