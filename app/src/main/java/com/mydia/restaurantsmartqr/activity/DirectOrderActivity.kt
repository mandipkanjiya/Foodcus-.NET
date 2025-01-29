package com.mydia.restaurantsmartqr.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.ItemCustomerListAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityDirectOrderBinding
import com.mydia.restaurantsmartqr.databinding.ActivityProductMenuBinding
import com.mydia.restaurantsmartqr.model.CustomerItem
import com.mydia.restaurantsmartqr.model.login.NewLoginModel
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.util.Utils
import com.mydia.restaurantsmartqr.viewModel.VMDirectOrder
import com.mydia.restaurantsmartqr.viewModel.VMProductMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class DirectOrderActivity : BaseActivity<ActivityDirectOrderBinding, VMDirectOrder>(){

    var customerItem:CustomerItem? = null
    var customerList:List<CustomerItem>?=null
    override fun observeViewModel() {
        viewModel.placeorder.observe(this){
            if (it.success == 1){
                showToast("Order placed successfully")
                finish()
            }
        }
        viewModel.customerList.observe(this){
            if (it.success == 1){
                customerList = it.result

                //finish()
            }
        }
        viewModel.addCustomer.observe(this){
            if (it.success == 1){
                showToast("Add Customer successfully")
                // viewModel.cMobileNo.set(it.)
                customerItem = it.result?.get(0)
                binding.etCustomer.setText(customerItem?.cCustomerName)
                viewModel.nCustomerId.set(customerItem?.nCustomerId.toString())
                viewModel.emailCustomer.set(customerItem?.cEmail)
                viewModel.customerName.set(customerItem?.cCustomerName)
                viewModel.cMobileNo.set(customerItem?.cContactNo)
                //finish()
            }
        }

    }

    override fun onActivityCreated() {
        binding.vm = viewModel
        viewModel.getUserData()
        viewModel.customerListApiCall()
        binding.ivAddCustomer.setOnClickListener {
            dialogAddCustomer()
        }

        binding.etCustomer.setOnClickListener {
            dialogCustomerList()
        }
        binding.imgMenu.setOnClickListener {
           finish()
        }
        binding.btnCreateOrder.setOnClickListener {
            viewModel.fTotal.set(binding.etAmount.text.toString())
           // viewModel.fTotal.set(binding.etAmount.text.toString())
            lifecycleScope.launch {
                if (viewModel.fTotal.get() == "") {
                    Log.e("error", "Please enter email")
                    showToast("Please calculate Total")
                    return@launch
                }
                if (viewModel.customerName.get() == "") {
                    showToast("Please Select customer")
                    return@launch
                }
                viewModel.cOrderType.set("3")
                viewModel.placeOrderApiCall()
            }
        }
    }
    override fun getViewBinding() = ActivityDirectOrderBinding.inflate(layoutInflater)

    override val viewModel: VMDirectOrder by viewModels()

    fun dialogAddCustomer() {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        val dialogRenameDoc =
            Dialog(this@DirectOrderActivity)
        dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRenameDoc.setContentView(R.layout.dialog_add_customer)
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
        val cvSubmit = dialogRenameDoc.findViewById<AppCompatButton>(R.id.btnSubmit)

        val etName = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etName)
        val etPhoneNumber = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etPhoneNumber)
        val etEmail = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etEmail)
        //val etMobileNumber = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etMobileNumber)


        val ivCancel = dialogRenameDoc.findViewById<ImageView>(R.id.ivBack)


        ivCancel!!.setOnClickListener {
            dialogRenameDoc.dismiss()

        }

        cvSubmit!!.setOnClickListener {
            lifecycleScope.launch {

                if(etName.text.toString().length == 0){
                    viewModel.showToast("Please enter Name.")
                    return@launch
                }

                if(etName.text.toString().length == 0){
                    viewModel.showToast("Please enter Name.")
                    return@launch
                }
                if (etEmail.text.toString().length == 0 && etPhoneNumber.text.toString().length == 0) {
                    //Log.e("error","Please enter email")
                  //  showToast("Please enter email")
                    Log.e("error","Please enter Phone Number")
                    showToast("Please enter Email Or Phone Number")

                    //   return@launch
                }
                if (etEmail.text.toString().length > 0) {
                    if (!Utils.isValidEmail(etEmail.text.toString())) {
                        showToast("Please enter valid email")
                        return@launch
                    }
                }
                viewModel.customerName.set(etName.text.toString())
                viewModel.emailCustomer.set(etEmail.text.toString())
                viewModel.cMobileNo.set(etPhoneNumber.text.toString())
               // viewModel.cMobileNo.set(etPhoneNumber.text.toString())
              viewModel.addCustomerApiCall()
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
    fun dialogCustomerList() {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        val dialogRenameDoc =
            Dialog(this@DirectOrderActivity)
        dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogRenameDoc.setContentView(R.layout.dialog_customerlist)
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
        val ivCancel = dialogRenameDoc.findViewById<ImageView>(R.id.ivCancel)
        val rvBreakType = dialogRenameDoc.findViewById<RecyclerView>(R.id.rvBreakType)
        tvTitle?.setText("Select Customer")
        if(customerList!=null){
        val adapter = ItemCustomerListAdapter(this@DirectOrderActivity, customerList!!)
        rvBreakType?.adapter = adapter
        adapter?.setOnItemCLickListener(object : ItemCustomerListAdapter.OnItemClickListener {
            override fun onItemClick(bannerModel: CustomerItem?, position: Int) {
                //getViewModel().getOtherProfileApi(bannerModel.nEmployeeId.toString())
                /* statesList = null*/
                binding.etCustomer.setText(bannerModel?.cCustomerName)
                viewModel.nCustomerId.set(bannerModel?.nCustomerId.toString())
                viewModel.customerName.set(bannerModel?.cCustomerName)
                viewModel.cMobileNo.set(bannerModel?.cContactNo)
                viewModel.emailCustomer.set(bannerModel?.cEmail)
                //   viewModel.pack.set(bannerModel?.name)
                //    viewModel.statesListApiCall()
                dialogRenameDoc.dismiss()
            }

        })
        }

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