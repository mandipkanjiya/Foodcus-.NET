package com.mydia.restaurantsmartqr.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemCategoryAdapterBinding
import com.mydia.restaurantsmartqr.databinding.ItemIncomingOrderBinding
import com.mydia.restaurantsmartqr.databinding.ItemSectionListBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.product.CategoryModel


class ItemCategoryAdapter(context: Context,private var incomingOrderList:List<CategoryModel>) :  RecyclerView.Adapter<ItemCategoryAdapter.ViewHolder>() {
    var context: Context
    var selectedPostion=0

    var onItemClickListener: OnItemClickListener? = null



    interface OnItemClickListener {
        fun onItemClick(datum: CategoryModel?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCategoryAdapterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category_adapter, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incomingData = incomingOrderList[position]
        holder.binding.tvProductName.text = incomingData.cCategoryName
        holder.binding.tvProductPrice.text = incomingData.cCategoryDesc
        Glide.with(context).load(incomingOrderList[position].cCategoryImage).error(R.mipmap.ic_launcher_foreground)
            .placeholder(R.mipmap.ic_launcher_foreground)
            .circleCrop()
            .into(holder.binding.ivProduct)
        if(selectedPostion == position){
            //dinin
            holder.binding.llMain.background=context.getDrawable(R.drawable.category_selection_bg)

        }else{
            //pickup
            holder.binding.llMain.background=context.getDrawable(R.drawable.category_unselected_bg)
        }


        holder.binding.llMain.setOnClickListener {
            selectedPostion = position
            onItemClickListener?.onItemClick(incomingOrderList[position],position)
            notifyDataSetChanged()
        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return incomingOrderList.size
    }

    inner class ViewHolder(binding: ItemCategoryAdapterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCategoryAdapterBinding = binding
    }

    init {
        this.context = context
    }


}
