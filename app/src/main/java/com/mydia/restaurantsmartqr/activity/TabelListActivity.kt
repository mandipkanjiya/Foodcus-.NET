package com.mydia.restaurantsmartqr.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.IncomingOrderAdapter
import com.mydia.restaurantsmartqr.adapter.ItemSectionListAdapter
import com.mydia.restaurantsmartqr.adapter.ItemTableListAadapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityLiveOrderBinding
import com.mydia.restaurantsmartqr.databinding.ActivityTabelListBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionList
import com.mydia.restaurantsmartqr.model.tableLIst.TableList
import com.mydia.restaurantsmartqr.viewModel.VMLiveOrder
import com.mydia.restaurantsmartqr.viewModel.VMTableList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TabelListActivity :  BaseActivity<ActivityTabelListBinding, VMTableList>(){
    lateinit var incomingOrderAdapter: ItemSectionListAdapter
    lateinit var itemTableListAadapter: ItemTableListAadapter
    override fun getViewBinding() = ActivityTabelListBinding.inflate(layoutInflater)
    override fun observeViewModel() {
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
        binding.ivCreateOrder.setOnClickListener {
            startActivity(Intent(this@TabelListActivity,DirectOrderActivity::class.java))
        }

    }
    override val viewModel: VMTableList by viewModels()
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
                startActivity(Intent(this@TabelListActivity,ProductMenuActivity::class.java).putExtra("nTableId",datum?.nRestaurantTableId.toString()).putExtra("nSectionId",datum?.nSectionId.toString()))
            }

        })
    }

}