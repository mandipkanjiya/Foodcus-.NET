package com.mydia.restaurantsmartqr.adapter


import android.content.Context

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.databinding.ItemOrderItemsBinding
import com.mydia.restaurantsmartqr.model.orderList.Items
import java.lang.StringBuilder


class IncomingOrderItemsAdapter(context: Context, private var itemList:List<Items>) :
    RecyclerView.Adapter<IncomingOrderItemsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemOrderItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order_items, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = itemList[position]
        holder.binding.tvName.text = itemData.nqty+" X "+itemData.cname
        val itemName : StringBuilder = StringBuilder()

        for( i in itemData.attributes){
            if(i.attribute_title.isNullOrEmpty()){
                itemName.append("")
            }else{
                itemName.append(i.attribute_title.toString()).append(" : ")
            }
         //  itemName.append(i.attribute_title.toString()).append(" : ")
            for(k in i.attributes){
                itemName.append(k.quantity).append("x").append(k.itemName).append(",")
            }
        }
        holder.binding.tvdesc.text = itemName.toString()
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(binding: ItemOrderItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemOrderItemsBinding = binding
    }



}
