package com.mydia.restaurantsmartqr.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemAcceptedOrderBinding
import com.mydia.restaurantsmartqr.databinding.ItemTableAlertsBinding
import com.mydia.restaurantsmartqr.model.alertList.AlertList
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class TableAlertAdapter(private val context: Context, private var alertList:List<AlertList>) :
    RecyclerView.Adapter<TableAlertAdapter.ViewHolder>() {


    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemAcceptClick(datum: OrderList?, pos: Int,orderType:String)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemTableAlertsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_table_alerts, parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val alertData = alertList[position]

        holder.binding.tvTableName.text ="qcvb"


      /*  holder.binding.btnReadyTocollect.setOnClickListener {
            onItemClickListener!!.onItemAcceptClick(alertData,position,acceptedData.orderType.toString())
        }*/

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return alertList.size
    }

    inner class ViewHolder(binding: ItemTableAlertsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemTableAlertsBinding = binding
    }

    init {

    }


}
