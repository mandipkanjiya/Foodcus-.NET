package com.mydia.restaurantsmartqr.activity

import android.app.Dialog
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.text.format.DateUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.adapter.ItemCartListAdapter
import com.mydia.restaurantsmartqr.adapter.ItemCategoryAdapter
import com.mydia.restaurantsmartqr.adapter.ItemProductAdapter
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivityProductMenuBinding
import com.mydia.restaurantsmartqr.model.CartItem
import com.mydia.restaurantsmartqr.model.CartItemNew
import com.mydia.restaurantsmartqr.model.ValuesItem
import com.mydia.restaurantsmartqr.model.product.CategoryModel
import com.mydia.restaurantsmartqr.model.product.ProductModel
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.util.Utils
import com.mydia.restaurantsmartqr.viewModel.VMProductMenu
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale

@AndroidEntryPoint
class ProductMenuActivity :  BaseActivity<ActivityProductMenuBinding, VMProductMenu>(){
    lateinit var itemCategoryAdapter: ItemCategoryAdapter
    lateinit var itemProductAdapter: ItemProductAdapter
    lateinit var itemCartListAdapter: ItemCartListAdapter
    var cartList = ArrayList<ProductModel>()
    var grandTotalnew: Double = 0.0
    var valuesItemNew = ArrayList<ValuesItem>()
    var cartListNew = ArrayList<CartItemNew>()
    override fun getViewBinding() = ActivityProductMenuBinding.inflate(layoutInflater)
    override fun observeViewModel() {
        viewModel.getUserData()
        viewModel.categoryListApiCAll()
        viewModel.categoryList.observe(this){
            if (it.status == 1){
                setCategoryAdapter(it.result)
                viewModel.productListApiCall(it.result.get(0).nCategoryId.toString())
                //setSectionAdapter(getItemList())
            }
        }

        viewModel.productList.observe(this){
            if (it.status == 1){
                setProcuctListAdapter(it.result)
            }
        }
        viewModel.placeorder.observe(this){
            if (it.success == 1){
                showToast("Order placed successfully")
                finish()
            }
        }
binding.llCharge.setOnClickListener{

        if (cartList != null && cartList.size > 0) {
            viewModel.jsonString.set(getJsonArray(cartList))
            viewModel.placeOrderApiCall()
        }else{
            showToast("Please add item in cart")
        }


}
        //viewModel.productListApiCall()
      //  setSectionAdapter(getItemList())
       // setTableListAdapter(getSectionWiseTableList())
       // setCartListAdapter(getSectionWiseTableList())
    }

    override fun onActivityCreated() {
        binding.vm = viewModel
        viewModel.nTableId.set(intent.getStringExtra("nTableId").toString())
        viewModel.nSectionId.set(intent.getStringExtra("nSectionId").toString())

        try {
            val pInfo: PackageInfo = packageManager.getPackageInfo(packageName,0)
            viewModel.versionName.set(pInfo.versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        setCartListAdapter(cartList)
       // binding.
    }
    override val viewModel: VMProductMenu by viewModels()
    fun setCategoryAdapter(list:List<CategoryModel>){
        //Log.e(TAG," incoming ${list.size}")
        itemCategoryAdapter = ItemCategoryAdapter(this,list)
        binding.rvCategory.adapter = itemCategoryAdapter

        itemCategoryAdapter.setOnItemCLickListener(object : ItemCategoryAdapter.OnItemClickListener{
            override fun onItemClick(datum: CategoryModel?, pos: Int) {
                viewModel.productListApiCall(datum?.nCategoryId.toString())
            }


        })
    }
    fun setProcuctListAdapter(list:List<ProductModel>){
        //Log.e(TAG," incoming ${list.size}")
        itemProductAdapter = ItemProductAdapter(this,list)
        binding.rvProduct.adapter = itemProductAdapter

        itemProductAdapter.setOnItemCLickListener(object : ItemProductAdapter.OnItemClickListener{
            override fun onItemClick(datum: ProductModel?, pos: Int) {
                if(datum!=null) {
                    dilogAddProductToCart(datum)
                }

            }


        })
    }
    fun setCartListAdapter(list:List<ProductModel>){
        //Log.e(TAG," incoming ${list.size}")
        itemCartListAdapter = ItemCartListAdapter(this)
        binding.rvCartList.adapter = itemCartListAdapter

        itemCartListAdapter.setOnItemCLickListener(object : ItemCartListAdapter.OnItemClickListener{
            override fun onItemClick(datum: ProductModel?, pos: Int) {
                //startActivity(Intent(this@ProductMenuActivity,ProductMenuActivity::class.java))
            }

        })
    }
 fun dilogAddProductToCart(productData:ProductModel) {
     /*  getViewModel().startDate.set("")
       getViewModel().endDate.set("")

       getViewModel().cReason.set("")*/
     //   var bannerModel: BreakTypeModel?=null
     var quantity = 1
     val dialogRenameDoc =
         Dialog(this@ProductMenuActivity)
     dialogRenameDoc.requestWindowFeature(Window.FEATURE_NO_TITLE)
     dialogRenameDoc.setContentView(R.layout.dialog_product_quantity)
     dialogRenameDoc.setCancelable(true)
     dialogRenameDoc.setCanceledOnTouchOutside(true)
     //dialogRenameDoc.window?.setGravity(Gravity.BOTTOM)
     //dialog.behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
     dialogRenameDoc.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
     dialogRenameDoc.setOnDismissListener {
         //  getViewModel().breakTypeId.set("")
         //getViewModel().breakTypeName.set("")
     }
     val tvProductTitle = dialogRenameDoc.findViewById<TextView>(R.id.tvProductTitle)
     val tvProductName = dialogRenameDoc.findViewById<TextView>(R.id.tvProductName)
     val tvProductPrice = dialogRenameDoc.findViewById<TextView>(R.id.tvProductPrice)
     val tvSpecialPrice = dialogRenameDoc.findViewById<TextView>(R.id.tvSpecialPrice)
     val tvDescription = dialogRenameDoc.findViewById<TextView>(R.id.tvDescription)
     val tvCategoryName = dialogRenameDoc.findViewById<TextView>(R.id.tvCategoryName)
     val ivProduct = dialogRenameDoc.findViewById<ImageView>(R.id.ivProduct)
     val llMinus = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.llMinus)
     val llPlus = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.llPlus)
     val etProductCount = dialogRenameDoc.findViewById<EditText>(R.id.etProductCount)
     //  val linAddItem = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linAddItem)
     //     val linStartDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linStartDate)
     //   val linEndDate = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linEndDate)
     val cvSubmit = dialogRenameDoc.findViewById<LinearLayoutCompat>(R.id.linAdd)
     val ivCancel = dialogRenameDoc.findViewById<ImageView>(R.id.ivCancel)
    // val etBaseUrl = dialogRenameDoc.findViewById<AppCompatEditText>(R.id.etBaseUrl)
     if (productData.cName != null && productData.cName.length > 0
     ) {
         val upperString: String = productData.cName.substring(0, 1)
             .uppercase() + productData.cName.substring(1)
             .lowercase()
         tvProductName.text = "" + upperString
     }

     if (productData.fPrice != null && productData.fPrice.toString().length > 0) {
         tvProductPrice.text = String.format(Locale.ENGLISH, "%.3f", productData.fPrice.toDouble())
     }
     /*  if(selectedPostion == position){
           //dinin
           holder.binding.llMain.background=context.getDrawable(R.drawable.section_selected_bg)

       }else{
           //pickup
           holder.binding.llMain.background=context.getDrawable(R.drawable.section_unselected_bg)
       }*/
     if (productData.fSpecialPrice != null && productData.fSpecialPrice.toString().length > 0 && !String.format(
             Locale.ENGLISH, "%.3f", productData.fSpecialPrice).equals("0.000", ignoreCase = true)
     ) {
         tvSpecialPrice.setVisibility(View.VISIBLE)
         tvProductPrice.paintFlags = tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
         tvProductPrice.textSize = 13f
         tvSpecialPrice.setText(java.lang.String.format(Locale.ENGLISH, "%.3f", productData.fSpecialPrice))
     } else {
         tvSpecialPrice.setVisibility(View.GONE)
         tvProductPrice.textSize = 16f
         tvProductPrice.paintFlags = 0
     }
     if (productData.cImage != null && productData.cImage.length > 0) {
         Glide.with(this@ProductMenuActivity)
             .load(productData.cImage)
             .placeholder(R.drawable.app_icon)
             .error(R.drawable.app_icon)
             .apply(RequestOptions.bitmapTransform(RoundedCorners(35)))
             .into(ivProduct)
     } else {
         Glide.with(this@ProductMenuActivity)
             .load(R.drawable.app_icon)
             .placeholder(R.drawable.app_icon)
             .error(R.drawable.app_icon)
             .apply(RequestOptions.bitmapTransform(RoundedCorners(35)))
             .into(ivProduct)
     }


     etProductCount.setText(quantity.toString())
     ivCancel!!.setOnClickListener {
         dialogRenameDoc.dismiss()

     }
     llPlus.setOnClickListener {
         if (productData.fStock?.toInt()!! > quantity) {
             quantity += 1
             etProductCount.setText(quantity.toString())
           //  totalProduct = quantity * itemPrice
             //totalWithAttribute = totalProduct + totalAttribute
           //  etSetCount.setText(SharedPref.getInstance(requireActivity())?.getStoreDetail()?.currencySymbol.toString()+" "+String.format("%.2f", totalWithAttribute))
         }
     }

     llMinus.setOnClickListener {
         if (quantity > 1) {
             quantity -= 1

             etProductCount.setText(quantity.toString())
             // val priceQuantity = quantity * product.price.toDouble()
            // totalProduct = quantity * itemPrice
            // totalWithAttribute = totalProduct + totalAttribute
             //etSetCount.setText(SharedPref.getInstance(requireActivity())?.getStoreDetail()?.currencySymbol.toString()+" "+String.format("%.2f", totalWithAttribute))
         }
     }
     cvSubmit!!.setOnClickListener {
         if(quantity==0){
             showToast("Please add at least one quantity")
             return@setOnClickListener
         }else {
             productData.quantity = etProductCount.text.toString()
             cartList.add(productData)
             itemCartListAdapter.updateData(cartList)
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
    private fun getJsonArray(cartItemList: List<ProductModel>?): String {
        val jAray = JSONArray()
        try {
            var object1= JSONObject()
            grandTotalnew = 0.0
            if (cartItemList != null && cartItemList.size > 0) {
                for (i in cartItemList.indices) {
                   // fTotal=

                    object1 = JSONObject()
                    object1.put("nProductId", cartItemList[i].nProductId)
                    object1.put(
                        "fMRP", String.format(
                            Locale.ENGLISH, "%.3f", java.lang.String.valueOf(
                                cartItemList[i].fPrice
                            ).toDouble()
                        )
                    )
                    // if (String.valueOf((int) Double.parseDouble(cartItemList.get(i).getfSpecialPrice())).equalsIgnoreCase("0") || String.valueOf((int) Double.parseDouble(cartItemList.get(i).getfSpecialPrice())).equals("")) {
                    if (cartItemList[i].fSpecialPrice != null && java.lang.String.format(
                            Locale.ENGLISH,
                            "%.3f",
                            cartItemList[i].fSpecialPrice
                        ).equals("0.000", ignoreCase = true)
                    ) {
                        object1.put(
                            "fMRP", String.format(
                                Locale.ENGLISH, "%.3f", java.lang.String.valueOf(
                                    cartItemList[i].fPrice
                                ).toDouble()
                            )
                        )
                        val totalPrice = cartItemList[i].fPrice!!.toDouble()* cartItemList[i].quantity!!.toDouble()
                        grandTotalnew+= totalPrice
                    } else {
                        object1.put(
                            "fMRP", String.format(
                                Locale.ENGLISH, "%.3f", java.lang.String.valueOf(
                                    cartItemList[i].fSpecialPrice
                                ).toDouble()
                            )
                        )
                        val totalPrice = cartItemList[i].fSpecialPrice!!.toDouble()* cartItemList[i].quantity!!.toDouble()
                        grandTotalnew+= totalPrice
                    }
                    object1.put("dtCartDate", Utils.getCurrentDate()) /*selDate*/
                    object1.put("nWarrentyDuration", cartItemList[i].nWarrentyDuration)
                   // if (fromCart == 1) {
                        object1.put("cDescription", cartItemList[i].cName)
                   /* } else {
                        object1.put("cDescription", cartItemList[i].getcProductName())
                    }*/

                    object1.put(
                        "nQuantity",
                        java.lang.String.valueOf(cartItemList[i].quantity)
                    )
                    if (cartItemList[i].cWarrantyType != null && cartItemList[i].cWarrantyType.toString().length > 0
                    ) {
                        object1.put("cWarrantyType", cartItemList[i].cWarrantyType)
                    } else {
                        object1.put("cWarrantyType", "")
                    }

                 //   val jArayChild = JSONArray()
                    object1.put("ChildData", "")

                    jAray.put(object1)
                }
            }
            Log.d("jsonString", jAray.toString())
            viewModel.fTotal.set(grandTotalnew.toString())
            return jAray.toString()
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return jAray.toString()
    }

    fun dialogAddCustomer() {
        /*  getViewModel().startDate.set("")
          getViewModel().endDate.set("")

          getViewModel().cReason.set("")*/
        //   var bannerModel: BreakTypeModel?=null
        val dialogRenameDoc =
            Dialog(this@ProductMenuActivity)
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
                if (etEmail.text.toString().length == 0) {
                    Log.e("error","Please enter email")
                    showToast("Please enter email")
                    if (etPhoneNumber.text.toString().length == 0) {
                        Log.e("error","Please enter Phone Number")
                        showToast("Please enter email")
                        return@launch
                    }
                 //   return@launch
                }
                if (etEmail.text.toString().length > 0) {
                    if (!Utils.isValidEmail(etEmail.text.toString())) {
                        showToast("Please enter valid email")
                        return@launch
                    }
                }
                if (etPhoneNumber.text.toString().length == 0) {
                    Log.e("error","Please enter Phone Number")
                    showToast("Please enter email")
                    return@launch
                }
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