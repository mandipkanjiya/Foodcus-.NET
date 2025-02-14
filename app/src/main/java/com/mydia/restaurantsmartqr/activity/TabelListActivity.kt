package com.mydia.restaurantsmartqr.activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.IncomingOrderAdapter
import com.mydia.restaurantsmartqr.adapter.ItemEmployeListAdapter
import com.mydia.restaurantsmartqr.adapter.ItemSectionListAdapter
import com.mydia.restaurantsmartqr.adapter.ItemTableListAadapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityLiveOrderBinding
import com.mydia.restaurantsmartqr.databinding.ActivityTabelListBinding
import com.mydia.restaurantsmartqr.model.EmployeItem
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionList
import com.mydia.restaurantsmartqr.model.tableLIst.TableList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.viewModel.VMLiveOrder
import com.mydia.restaurantsmartqr.viewModel.VMTableList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TabelListActivity :  BaseActivity<ActivityTabelListBinding, VMTableList>(){
    lateinit var itemEmployeListAdapter: ItemEmployeListAdapter
    lateinit var incomingOrderAdapter: ItemSectionListAdapter
    lateinit var itemTableListAadapter: ItemTableListAadapter
    var employeId:String="0"
    override fun getViewBinding() = ActivityTabelListBinding.inflate(layoutInflater)
    override fun observeViewModel() {

        viewModel.employeList.observe(this){
            if (it.success == 1){
                employeId = it.result.get(0)?.nEmpId.toString()
                setEmployeListAdapter(it.result)
               // viewModel.tableListApiCAll(it.result.get(0).nSectionId.toString())
                //setSectionAdapter(getItemList())
            }
        }
        viewModel.sectionList.observe(this){
            if (it.status == 1){
                setSectionAdapter(it.result)
                viewModel.tableListApiCAll(it.result.get(0).nSectionId.toString())
                //setSectionAdapter(getItemList())
            }
        }
        viewModel.tableList.observe(this){
            if (it.status == 1){
                setTableListAdapter(it.result)
                //setSectionAdapter(getItemList())
            }
        }

       // setSectionAdapter(getItemList())
      //  setTableListAdapter(getSectionWiseTableList())
    }

    override fun onActivityCreated() {
        binding.vm = viewModel
        viewModel.getUserData()
        viewModel.sectionListApiCall()
        viewModel.employeListApiCall()
        binding.ivCreateOrder.setOnClickListener {
            startActivity(Intent(this@TabelListActivity,DirectOrderActivity::class.java))
        }
        binding.ivLogout.setOnClickListener {
            showLogoutDialog()
        }
    }
    override val viewModel: VMTableList by viewModels()
    fun setEmployeListAdapter(list:List<EmployeItem>){
        //Log.e(TAG," incoming ${list.size}")
        itemEmployeListAdapter = ItemEmployeListAdapter(this,list)
        binding.rvSectionList.adapter = itemEmployeListAdapter

        itemEmployeListAdapter.setOnItemCLickListener(object : ItemEmployeListAdapter.OnItemClickListener{
            override fun onItemClick(datum: EmployeItem?, pos: Int) {
                employeId = datum?.nEmpId.toString()
                //viewModel.tableListApiCAll(datum?.nSectionId.toString())
            }


        })
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
    fun setSectionAdapter(list:List<SectionList>){
        //Log.e(TAG," incoming ${list.size}")
        incomingOrderAdapter = ItemSectionListAdapter(this,list)
        binding.rvSectionList.adapter = incomingOrderAdapter

        incomingOrderAdapter.setOnItemCLickListener(object : ItemSectionListAdapter.OnItemClickListener{
            override fun onItemClick(datum: SectionList?, pos: Int) {
                viewModel.tableListApiCAll(datum?.nSectionId.toString())
            }


        })
    }
    fun setTableListAdapter(list:List<TableList>){
        //Log.e(TAG," incoming ${list.size}")
        itemTableListAadapter = ItemTableListAadapter(this,list)
        binding.rvTableList.adapter = itemTableListAadapter

        itemTableListAadapter.setOnItemCLickListener(object : ItemTableListAadapter.OnItemClickListener{
            override fun onItemClick(datum: TableList?, pos: Int) {
                startActivity(Intent(this@TabelListActivity,ProductMenuActivity::class.java).putExtra("nTableId",datum?.nRestaurantTableId.toString()).putExtra("nSectionId",datum?.nSectionId.toString()).putExtra("employeId",employeId.toString()))
            }

        })
    }

}