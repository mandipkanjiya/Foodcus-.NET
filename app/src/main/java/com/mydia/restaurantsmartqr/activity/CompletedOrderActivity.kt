package com.mydia.restaurantsmartqr.activity


import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker

import com.mydia.restaurantsmartqr.adapter.CompletedOrderAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityCompletedOrderBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList

import com.mydia.restaurantsmartqr.model.tableLIst.TableList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.TableListRequest
import com.mydia.restaurantsmartqr.util.Utils
import com.mydia.restaurantsmartqr.viewModel.VMCompletedOrder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@AndroidEntryPoint
class CompletedOrderActivity : BaseActivity<ActivityCompletedOrderBinding, VMCompletedOrder>(){

    private val TAG = CompletedOrderActivity::class.java.simpleName

    lateinit var completedOrderAdapter: CompletedOrderAdapter
    var spnSelectedTableName = ""
    var spnSelectedOrder = "1"
    var selectedTableId = ""
    var selectedStartDate = ""
    var selectedEndDate = ""
    var orderList = ArrayList<OrderList>()
    override fun getViewBinding() = ActivityCompletedOrderBinding.inflate(layoutInflater)


    var filter = arrayOf("Today Order", "All Order")
    var tableList = arrayListOf<String?>()

    override fun onActivityCreated() {
        binding.vm = viewModel
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, filter)


        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnFilter.adapter = ad
        binding.spnFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                lifecycleScope.launch {
                    if(position == 0){
                        spnSelectedOrder = "1"
                        val orderListRequest = OrderListRequest(nUserId = prefs.getString(PrefKey.USER_ID), nCustomerId = "19", nFromId = "0", nToId = "1000", cSectionId = 0, cTableId = 0, nOrderType = "3")
                        viewModel.completedOrderListApi(orderListRequest)

                        // setTodayFilter()
                    }else{
                        spnSelectedOrder = "0"
                        val orderListRequest = OrderListRequest(nUserId = prefs.getString(PrefKey.USER_ID), nCustomerId = "19", nFromId = "0", nToId = "1000", cSectionId = 0, cTableId = 0, nOrderType = "3")
                        viewModel.completedOrderListApi(orderListRequest)

                        // setAdapter()
                    }
                }
            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
        lifecycleScope.launch {

            Log.e(TAG,prefs.getString(PrefKey.BRANCH_ID))

            val orderListRequest = OrderListRequest(nUserId = prefs.getString(PrefKey.USER_ID), nCustomerId = "19", nFromId = "0", nToId = "1000", cSectionId =0, cTableId = 0, nOrderType = "3")
            viewModel.completedOrderListApi(orderListRequest)

            val tableLIstRequest = TableListRequest(nUserId = prefs.getString(PrefKey.BRANCH_ID))
            viewModel.tableListApi(tableLIstRequest)


        }

        binding.rvDate.setOnClickListener {
            datePickerDialog()
        }

        binding.imgBack.setOnClickListener{
            finish()
        }

    }

    override val viewModel:  VMCompletedOrder by viewModels()
    override fun observeViewModel() {
        viewModel.completedOrderList.observe(this){
            if (it.Success == "1"){
                orderList = it.result
               setAdapter()
            }

        }
        viewModel.tableList.observe(this){
            tableList.add("Select Table")
            if (it.status == 1){
                for(i in it.result){
                    tableList.add(i.cTableName)
                }

            }
            setTableFilter(tableList,it.result)

        }
    }
    fun setTableFilter(tableList:ArrayList<String?>,allList:ArrayList<TableList>){
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, tableList as List<String?>)
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnTableFilter.adapter = ad
        binding.spnTableFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val spnSelectedTableName = parent.getItemAtPosition(position).toString()
                Log.e(TAG,"spn $spnSelectedTableName $spnSelectedOrder")
                lifecycleScope.launch {

                    if(spnSelectedTableName != "Select Table"){
                      for(i in allList){
                          if(i.cTableName == spnSelectedTableName){
                              selectedTableId = i.nRestaurantTableId.toString()
                          }
                      }

                        val orderListRequest = OrderListRequest(nUserId = prefs.getString(PrefKey.USER_ID), nCustomerId = "19", nFromId = "0", nToId = "1000", cSectionId = 0, cTableId = 0, nOrderType = "")
                        viewModel.completedOrderListApi(orderListRequest)
                    }
                }


            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }
    }

    fun setAdapter(){
        Log.e(TAG, "order${orderList.size}")
      /*  if(spnSelectedTableName != "Select Table"){
            orderList.filter { it -> it.tableName.equals(spnSelectedTableName) }
        }*/
        val linearLayoutManager = GridLayoutManager(this,3)
        binding.rvCompletedOrder.layoutManager=linearLayoutManager
        completedOrderAdapter = CompletedOrderAdapter(this)
        binding.rvCompletedOrder.adapter = completedOrderAdapter


        if(orderList.size > 0){
            viewModel.isNoData.set(false)
        }else{
            viewModel.isNoData.set(true)
        }
        completedOrderAdapter.setFilter(orderList)
    }

    fun setTodayFilter(){

        completedOrderAdapter = CompletedOrderAdapter(this)
        binding.rvCompletedOrder.adapter = completedOrderAdapter
        val todayOrder = ArrayList<OrderList>()

        for (order in orderList) {
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = formatter.parse(Utils.getCurrentDate())
            var date: Date? = null
           // val createdDate = ""
            val createdDate = order.dtGeneratedDate?.split(" ")
          //  Log.e(TAG, createdDate!![0].toString()+","+order.createdDate)
            try {
                date = formatter.parse(createdDate!![0].toString())
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            Log.e(TAG, "$date,$currentDate")
            if (currentDate == date) {
                todayOrder.add(order)
            }
            /*  if (currentDate.before(date)) {
                  futureEvents.add(events)
              }*/
        }
        if(todayOrder.size > 0){
            viewModel.isNoData.set(false)
        }else{
            viewModel.isNoData.set(true)
        }
        Log.e(TAG,todayOrder.size.toString())
        completedOrderAdapter.setFilter(todayOrder)


    }
   /* fun setDateFilter(){
        binding.rvDate.setOnClickListener {
            val c = Calendar.getInstance()

            // on below line we are getting
            // our day, month and year.
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            // on below line we are creating a
            // variable for date picker dialog.
            val datePickerDialog = DatePickerDialog(
                // on below line we are passing context.
                this,
                { view, year, monthOfYear, dayOfMonth ->
                    // on below line we are setting
                    // date to our text view.
                    binding.tvStartDate.text =
                        (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                // on below line we are passing year, month
                // and day for the selected date in our date picker.
                year,
                month,
                day
            )
            // at last we are calling show
            // to display our date picker dialog.
            datePickerDialog.show()
        }
    }*/

    private fun datePickerDialog() {
        // Creating a MaterialDatePicker builder for selecting a date range

        val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()

        datePicker.show(supportFragmentManager, "DatePicker")
        datePicker.addOnPositiveButtonClickListener {
            it->
            val startDate = it.first
            val endDate = it.second

            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val startDateString = sdf.format(Date(startDate))
            val endDateString = sdf.format(Date(endDate))
            selectedStartDate = startDateString
            selectedEndDate = endDateString
            // Creating the date range string
            val selectedDateRange = "$startDateString - $endDateString"
            binding.tvStartDate.text = selectedDateRange
            apiCallForDateFilter(startDateString,endDateString)
            Toast.makeText(this, "${datePicker.headerText} is selected", Toast.LENGTH_LONG).show()
        }

        // Setting up the event for when cancelled is clicked
        datePicker.addOnNegativeButtonClickListener {
            selectedStartDate = ""
            selectedEndDate = ""
            Toast.makeText(this, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
        }

        // Setting up the event for when back button is pressed
        datePicker.addOnCancelListener {
            Toast.makeText(this, "Date Picker Cancelled", Toast.LENGTH_LONG).show()
        }
    }
    fun apiCallForDateFilter(startDate:String,endDate:String){
        lifecycleScope.launch{
            val orderListRequest = OrderListRequest(nUserId = "21", nCustomerId = "19", nFromId = "0", nToId = "1000", cSectionId =0, cTableId = 0, nOrderType = "")
            viewModel.completedOrderListApi(orderListRequest)
        }
    }
    /*  override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        lifecycleScope.launch {
            if(p2 == 0){
                val orderListRequest = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "4", offset = "0", limit = "1000", today_orders ="1")
                viewModel.completedOrderListApi(orderListRequest)

                // setTodayFilter()
            }else{
                val orderListRequest = OrderListRequest(branch_id = prefs.getString(PrefKey.BRANCH_ID), status = "4", offset = "0", limit = "1000")
                viewModel.completedOrderListApi(orderListRequest)

                // setAdapter()
            }
        }


    }


    override fun onNothingSelected(p0: AdapterView<*>?) {

    }*/

}