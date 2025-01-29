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
import com.mydia.restaurantsmartqr.databinding.ItemAllAlertBinding
import com.mydia.restaurantsmartqr.model.alertList.AlertList
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class AllAlertAdapter(private val context: Context, private var alertList:List<AlertList>) :
    RecyclerView.Adapter<AllAlertAdapter.ViewHolder>() {



    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemAcceptClick(datum: AlertList?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAllAlertBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_all_alert, parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val alertData = alertList[position]
        holder.binding.tvType.text = alertData.cRequestType
        holder.binding.tvTableName.text = alertData.cTableName
        holder.binding.tvSectionName.text = alertData.cSectionName

        if(alertData.nMarkCompleted == 1){
            holder.binding.btnStatusChange.text ="Request Addressed"
            holder.binding.btnStatusChange.background = context.getDrawable(R.drawable.rounded_button_small_lightgreen)
        }else{
            holder.binding.btnStatusChange.text ="ok"
            holder.binding.btnStatusChange.background = context.getDrawable(R.drawable.rounded_button_small_sky)
            holder.binding.btnStatusChange.setOnClickListener {
                onItemClickListener!!.onItemAcceptClick(alertData,position)
            }
        }




        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return alertList.size
    }

    inner class ViewHolder(binding: ItemAllAlertBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemAllAlertBinding = binding
    }



}
