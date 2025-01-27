package com.mydia.restaurantsmartqr.adapter


import android.content.Context

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.databinding.ItemAcceptedOrderItemsBinding
import com.mydia.restaurantsmartqr.model.orderList.Items


class AcceptedOrderItemsAdapter(context: Context, private var itemList:List<Items>) :
    RecyclerView.Adapter<AcceptedOrderItemsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAcceptedOrderItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_accepted_order_items, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ViewHolder(binding: ItemAcceptedOrderItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemAcceptedOrderItemsBinding = binding
    }



}
