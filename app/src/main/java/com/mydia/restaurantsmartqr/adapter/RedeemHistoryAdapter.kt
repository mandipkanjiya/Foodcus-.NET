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
import com.mydia.restaurantsmartqr.databinding.ItemRedeemPointBinding
import com.mydia.restaurantsmartqr.model.WalletHistoryDetailsItem
import com.mydia.restaurantsmartqr.model.alertList.AlertList
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.util.Utils


class RedeemHistoryAdapter(private val context: Context, private var alertList:List<WalletHistoryDetailsItem>) :
    RecyclerView.Adapter<RedeemHistoryAdapter.ViewHolder>() {



    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(datum: WalletHistoryDetailsItem, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemRedeemPointBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_redeem_point, parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val alertData = alertList[position]

        holder.binding.tvDate.text = Utils.convertWalleteHistoryDate(alertData.dtOrderDate)
        holder.binding.tvReasons.text = alertData.cReasons.toString()
        holder.binding.tvRedeemPoint.text = alertData.fOrderEarnedPoints.toString()+" Earned"
        holder.binding.tvOrderAmount.text = "$"+" "+alertData.fOrderAmount.toString()
        holder.binding.tvOrderSaving.text = "$"+" "+alertData.fOrderSavings.toString()+" Saving"

        holder.binding.llMain.setOnClickListener {
            onItemClickListener?.onItemClick(alertList[position],position)
         //   notifyDataSetChanged()
        }



        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return alertList.size
    }

    inner class ViewHolder(binding: ItemRedeemPointBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemRedeemPointBinding = binding
    }



}
