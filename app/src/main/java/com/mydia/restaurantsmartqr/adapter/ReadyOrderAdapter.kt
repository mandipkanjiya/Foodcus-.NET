package com.mydia.restaurantsmartqr.adapter


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemReadyOrderBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class ReadyOrderAdapter(context: Context, private var readyOrderList:List<OrderList>) :
    RecyclerView.Adapter<ReadyOrderAdapter.ViewHolder>() {
    var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemReadyOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_ready_order, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val readyData = readyOrderList[position]
        holder.binding.tvOrderId.text = "#"+readyData.orderRef
        holder.binding.tvCustomerName.text =  readyData.cutomerName
        holder.binding.relMain.background=context.getDrawable(R.drawable.bg_pickup_order)
        holder.binding.tvOrderType.text =  readyData.order_type_name
        holder.binding.tvRemainingTime.text = readyData.pickupTime.toString()
        holder.binding.tvDate.text = readyData.pickupDate.toString()
        holder.binding.llPickUp.visibility = View.VISIBLE
        holder.binding.rvMain.setOnClickListener {
            context.startActivity(Intent(context, OrderDetailActivity::class.java).putExtra("orderDetail",readyOrderList[position]))
        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return readyOrderList.size
    }

    inner class ViewHolder(binding: ItemReadyOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemReadyOrderBinding = binding
    }

    init {
        this.context = context
    }


}
