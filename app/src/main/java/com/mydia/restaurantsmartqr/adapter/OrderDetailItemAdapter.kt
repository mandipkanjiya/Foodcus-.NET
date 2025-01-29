package com.mydia.restaurantsmartqr.adapter


import android.content.Context
import android.util.Log

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.databinding.ItemOrderDetailrBinding
import com.mydia.restaurantsmartqr.model.orderList.Items
import java.lang.StringBuilder
import kotlin.math.roundToInt


class OrderDetailItemAdapter(context: Context, private var itemList:ArrayList<Items>, private var currency:String) :
    RecyclerView.Adapter<OrderDetailItemAdapter.ViewHolder>() {
    var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemOrderDetailrBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order_detailr, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemListData = itemList[position]
        holder.binding.tvQnty.text = itemListData.fQuantity.toString()
        holder.binding.tvItemName.text = itemListData.cProductName
        val itemName :StringBuilder = StringBuilder()
/*
        for( i in itemListData.attributes){
            if(i.attribute_title.isNullOrEmpty()){
                itemName.append("")
            }else{
                itemName.append(i.attribute_title.toString()).append(" : ")
            }
           // itemName.append(i.attribute_title.toString()).append(" : ")
          for(k in i.attributes){
              itemName.append(k.quantity).append("x").append(k.itemName).append(",")
          }
        }*/


        holder.binding.tvItemDesc.text = itemName.toString()


      val price =  (itemListData.fProductPrice!!.toDouble() * 10.0).roundToInt() / 10.0
        holder.binding.tvItemPrice.text =  currency + price

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(binding: ItemOrderDetailrBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemOrderDetailrBinding = binding
    }

    init {
        this.context = context
    }


}
