package com.mydia.restaurantsmartqr.activity


import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.util.Pair
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.OrderHistoryAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityOrderHistoryBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionList
import com.mydia.restaurantsmartqr.model.tableLIst.TableList
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.viewModel.VMOrderHistory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class OrderHistoryActivity : BaseActivity<ActivityOrderHistoryBinding, VMOrderHistory>() {
    private val TAG = OrderHistoryActivity::class.java.simpleName
    var filter = arrayOf("All Orders","Pending","Accepted","Completed","Rejected")

    var selectedStatus = ""
    var selectedSection : SectionList? = null
    var selectedTable : TableList? = null
    var selectedStartDate = ""
    var selectedEndDate = ""
    var tableSpnAdapter: ArrayAdapter<*>? = null
    var selectionDates: Pair<Long,Long>? = null
    override fun getViewBinding() = ActivityOrderHistoryBinding.inflate(layoutInflater)


    override val viewModel:  VMOrderHistory by viewModels()
    override fun observeViewModel() {
        viewModel.getUserData()
        viewModel.completedOrderList.observe(this){
            if (it.Success == "1"){
                setAdapter(it.result)
            }

        }
        viewModel.tableList.observe(this){
            if (it.status == 1){
                setTableFilter(it.result)
            }
        }
        viewModel.sectionList.observe(this){
            if (it.status == 1){
                setSectionFilter(it.result)
            }
        }
    }

    override fun onActivityCreated() {
        binding.vm = viewModel

        viewModel.sectionListApiCall()
        viewModel.orderListUsingFilter("",0,0,"","")
        setOrderStatusFilter()
        setDateSpinner()

      // setTableAdapter()
      //  setTableFilter(arrayListOf())

        binding.imgBack.setOnClickListener{
            finish()
        }
        binding.imgHome.setOnClickListener{
            startActivity(Intent(this,LiveOrderActivity::class.java))
        }


    }

    fun setAdapter(orderList:List<OrderList>){
        Log.e(TAG, "order${orderList.size}")
        lifecycleScope.launch{
            val currency = prefs.getString(PrefKey.CURRENCY)
            val orderHistoryAdapter = OrderHistoryAdapter(this@OrderHistoryActivity,currency)
            binding.rvCompletedOrder.adapter = orderHistoryAdapter
            orderHistoryAdapter.setFilter(orderList)
        }


    }

    private fun setOrderStatusFilter() {
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.spinner_item, filter)
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnFilter.adapter = ad
        binding.spnFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    val spnSelectedStatus = parent.getItemAtPosition(position).toString()
                    if(position == 0){
                        selectedStatus=""
                    }else if(position == 1){
                        selectedStatus ="1"
                    }else if( position ==2){
                        selectedStatus = "2"
                    }else if( position ==3){
                        selectedStatus = "3"
                    }else if( position ==4){
                        selectedStatus = "4"
                    }
                    Log.e(TAG, "spn $spnSelectedStatus")

                    viewModel.orderListUsingFilter(selectedStatus,
                        selectedTable?.nRestaurantTableId?: kotlin.run { 0 },
                        selectedSection?.nSectionId ?: kotlin.run {0 },
                        selectedStartDate,
                        selectedEndDate)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
    }


    fun setSectionFilter(list:ArrayList<SectionList>){
        list.add(0,SectionList(0, cName = "Select Section",0,0,"","",""))

        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(this, R.layout.spinner_item, list as List<SectionList?>)
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnTableSection.adapter = ad

        binding.spnTableSection.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    val spnSelectedSection = parent.getItemAtPosition(position) as SectionList

                    if (position == 0) {
                        selectedTable = null
                        selectedSection = null
                        binding.spnTableFilter.setSelection(0)
                        setTableAdapter()

                    }else{
                        selectedSection = spnSelectedSection
                        viewModel.tableListApiCAll(sectionId = selectedSection?.nSectionId!!.toString())
                    }

                    viewModel.orderListUsingFilter(selectedStatus,
                        selectedTable?.nRestaurantTableId ?: kotlin.run { 0 },
                        selectedSection?.nSectionId ?: kotlin.run { 0 },
                        selectedStartDate,
                        selectedEndDate)
                    Log.e(TAG, "spn $spnSelectedSection")

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        setTableAdapter()

    }
    fun setTableAdapter(){
        val tableList = arrayListOf<TableList>()

        tableList.add(0, TableList(0,0,0,"Select Table","","",0,false,"","",""))
        tableSpnAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_item, tableList as List<TableList?>)
        tableSpnAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnTableFilter.adapter = tableSpnAdapter
    }

    fun setTableFilter(list:ArrayList<TableList>){

        list.add(0,TableList(0,0,0,"Select Table","","",0,false,"","",""))
        tableSpnAdapter = ArrayAdapter<Any?>(this, R.layout.spinner_item, list as List<TableList?>)
        tableSpnAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spnTableFilter.adapter = tableSpnAdapter



        binding.spnTableFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val spnSelectedTableName = parent.getItemAtPosition(position) as TableList
                if (position != 0){
                    selectedTable = spnSelectedTableName
                }else{
                    selectedTable = null
                }
                viewModel.orderListUsingFilter(selectedStatus,
                    selectedTable?.nRestaurantTableId ?: kotlin.run { 0 },
                    selectedSection?.nSectionId ?: kotlin.run { 0 },
                    selectedStartDate,
                    selectedEndDate)

            } // to close the onItemSelected

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }




    }
    fun setDateSpinner(){
        binding.rvDate.setOnClickListener {
            datePickerDialog()
        }
    }

    private fun datePickerDialog() {
        // Creating a MaterialDatePicker builder for selecting a date range


       // val datePicker = MaterialDatePicker.Builder.dateRangePicker().build()
        val datePickerBuilder = MaterialDatePicker.Builder.dateRangePicker()
        datePickerBuilder.setTheme(R.style.MaterialCalendarTheme_RangeFill)

        if(selectionDates!=null){
            datePickerBuilder.setSelection(selectionDates)

        }




        val datePicker = datePickerBuilder.build()

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
            selectionDates = it
            // Creating the date range string
            val selectedDateRange = "$startDateString - $endDateString"
            binding.tvStartDate.text = selectedDateRange

            viewModel.orderListUsingFilter(selectedStatus,
                selectedTable?.nRestaurantTableId ?: kotlin.run { 0 },
                selectedSection?.nSectionId ?: kotlin.run {0 },
                selectedStartDate,
                selectedEndDate)

           // Toast.makeText(this, "${datePicker.headerText} is selected", Toast.LENGTH_LONG).show()
        }

        // Setting up the event for when cancelled is clicked
        datePicker.addOnNegativeButtonClickListener {
            selectedStartDate = ""
            selectedEndDate = ""
            selectionDates = null
            binding.tvStartDate.text = "Select Date Range"
            viewModel.orderListUsingFilter(selectedStatus,
                selectedTable?.nRestaurantTableId ?: kotlin.run { 0 },
                selectedSection?.nSectionId?: kotlin.run {0 },
                selectedStartDate,
                selectedEndDate)
          //  Toast.makeText(this, "${datePicker.headerText} is cancelled", Toast.LENGTH_LONG).show()
        }

        // Setting up the event for when back button is pressed
        datePicker.addOnCancelListener {
            viewModel.orderListUsingFilter(selectedStatus,
                selectedTable?.nRestaurantTableId ?: kotlin.run { 0 },
                selectedSection?.nSectionId ?: kotlin.run { 0 },
                selectedStartDate,
                selectedEndDate)
           // Toast.makeText(this, "Date Picker Cancelled", Toast.LENGTH_LONG).show()
        }
    }

}