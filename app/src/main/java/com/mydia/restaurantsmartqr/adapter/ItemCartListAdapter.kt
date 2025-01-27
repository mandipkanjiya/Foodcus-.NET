package com.mydia.restaurantsmartqr.adapter


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Paint
import android.util.Log

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemCartBinding
import com.mydia.restaurantsmartqr.databinding.ItemCategoryAdapterBinding
import com.mydia.restaurantsmartqr.databinding.ItemIncomingOrderBinding
import com.mydia.restaurantsmartqr.databinding.ItemSectionListBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.product.ProductModel
import java.util.Locale


class ItemCartListAdapter(context: Context) :  RecyclerView.Adapter<ItemCartListAdapter.ViewHolder>() {
    var context: Context
    var selectedPostion=0
    var incomingOrderList:ArrayList<ProductModel> = ArrayList<ProductModel>()
    var onItemClickListener: OnItemClickListener? = null



    interface OnItemClickListener {
        fun onItemClick(datum: ProductModel?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    fun updateData(incomingOrderList1:List<ProductModel>) {
        incomingOrderList = incomingOrderList1 as ArrayList<ProductModel>
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCartBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cart, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incomingData = incomingOrderList[position]
       /* holder.binding.tvItemName.text = incomingData
        holder.binding.tvQuantityCount.text = "10"*/
        if (incomingData.cName != null && incomingData.cName.length > 0
        ) {
            val upperString: String = incomingData.cName.substring(0, 1)
                .uppercase() + incomingData.cName.substring(1)
                .lowercase()
            holder.binding.tvProductName.text = "" + upperString
        }

        if (incomingData.fPrice != null && incomingData.fPrice.toString().length > 0) {
            holder.binding.tvProductPrice.text = String.format(Locale.ENGLISH, "%.3f", incomingData.fPrice.toDouble())
        }
        /*  if(selectedPostion == position){
              //dinin
              holder.binding.llMain.background=context.getDrawable(R.drawable.section_selected_bg)

          }else{
              //pickup
              holder.binding.llMain.background=context.getDrawable(R.drawable.section_unselected_bg)
          }*/
        if (incomingData.fSpecialPrice != null && incomingData.fSpecialPrice.toString().length > 0 && !String.format(
                Locale.ENGLISH, "%.3f", incomingData.fSpecialPrice).equals("0.000", ignoreCase = true)
        ) {
            holder.binding.tvSpecialPrice.setVisibility(View.VISIBLE)
            holder.binding.tvProductPrice.paintFlags = holder.binding.tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //  tvSpecPrice.setText(productDetails.get(0).getnSpecialPrice());
            holder.binding.tvProductPrice.textSize = 13f
            holder.binding.tvSpecialPrice.setText(java.lang.String.format(Locale.ENGLISH, "%.3f", incomingData.fSpecialPrice))
        } else {
            holder.binding.tvSpecialPrice.setVisibility(View.GONE)
            holder.binding.tvProductPrice.textSize = 16f
            holder.binding.tvProductPrice.paintFlags = 0
        }
        if (incomingData.cImage != null && incomingData.cImage.length > 0) {
            Glide.with(context)
                .load(incomingData.cImage)
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(35)))
                .into(holder.binding.ivProduct)
        } else {
            Glide.with(context)
                .load(R.drawable.app_icon)
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(35)))
                .into(holder.binding.ivProduct)
        }
        //holder.binding.tv.text = "10"
     /*   Glide.with(context).load(cartlist[position].image).error(R.mipmap.ic_launcher_foreground)
            .placeholder(R.mipmap.ic_launcher_foreground)
            .into(holder.binding.imgUser)*/
      /*  if(selectedPostion == position){
            //dinin
            holder.binding.llMain.background=context.getDrawable(R.drawable.category_selection_bg)

        }else{
            //pickup
            holder.binding.llMain.background=context.getDrawable(R.drawable.category_unselected_bg)
        }*/


        /*holder.binding.llMain.setOnClickListener {
            selectedPostion = position
            notifyDataSetChanged()
        }*/

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return incomingOrderList.size
    }

    inner class ViewHolder(binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCartBinding = binding
    }

    init {
        this.context = context
    }


}
