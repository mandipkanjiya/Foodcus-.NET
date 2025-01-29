package com.mydia.restaurantsmartqr.adapter


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemIncomingOrderBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class IncomingOrderAdapter(context: Context,private var incomingOrderList:List<OrderList>) :  RecyclerView.Adapter<IncomingOrderAdapter.ViewHolder>() {
    var context: Context
    lateinit var incomingOrderItemsAdapter: IncomingOrderItemsAdapter

    var onItemClickListener: OnItemClickListener? = null



    interface OnItemClickListener {
        fun onItemAcceptClick(datum: OrderList?, pos: Int)
        fun onItemRejectClick(datum: OrderList?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemIncomingOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_incoming_order, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incomingData = incomingOrderList[position]

        Log.e("adptre",incomingData.cOrderType.toString())

        if(incomingData.cOrderType.equals("DineIn")){
            //dinin
            if(incomingData.cTableName!!.isNotEmpty()){
                holder.binding.tvOrderId.text = incomingData.cTableName
            }else{
                holder.binding.tvOrderId.text = "# "+incomingData.cOrderCode
            }

            holder.binding.llPickUp.visibility = View.GONE
            holder.binding.tvName.visibility = View.GONE
            holder.binding.btnAccept.visibility = View.VISIBLE
            holder.binding.btnReject.visibility = View.VISIBLE
            holder.binding.relMain.background=context.getDrawable(R.drawable.bg_dinein_order)

        }else if(incomingData.cOrderType.equals("Pickup")){
            //pickup
            holder.binding.tvOrderId.text = "#${incomingData.cOrderCode}"
            holder.binding.tvName.visibility = View.VISIBLE
            holder.binding.tvName.text = incomingData.cCustomerName
            holder.binding.btnAccept.visibility = View.VISIBLE
            holder.binding.btnReject.visibility = View.VISIBLE
            holder.binding.tvRemainingTime.text = incomingData.dtDeliveryDate.toString()
            holder.binding.tvDate.text = incomingData.dtGeneratedDate.toString()
            holder.binding.llPickUp.visibility = View.VISIBLE
            holder.binding.relMain.background=context.getDrawable(R.drawable.bg_pickup_order)
        }

        holder.binding.tvTime.text = incomingData.cDeliveryTime
      /*  if(incomingData.co!!.isNotEmpty()){
            holder.binding.tvOrderNote.text = "Order Note: "+incomingData.corderNote
        }*/

       holder.binding.tvSection.text = incomingData.cSectionName
        holder.binding.tvOrderType.text = incomingData.cOrderType
        if(incomingData.Item.size > 3){
            holder.binding.tvViewMore.visibility = View.VISIBLE
        }else{
            holder.binding.tvViewMore.visibility = View.GONE
        }
        incomingOrderItemsAdapter = IncomingOrderItemsAdapter(context, incomingData.Item)
        holder.binding.rvItems.adapter = incomingOrderItemsAdapter

        holder.binding.rvMain.setOnClickListener {
            context.startActivity(Intent(context, OrderDetailActivity::class.java).putExtra("orderDetail",incomingOrderList[position]))
        }

        holder.binding.btnAccept.setOnClickListener {
           onItemClickListener!!.onItemAcceptClick(incomingOrderList[position],position)
        }
        holder.binding.btnReject.setOnClickListener {
           onItemClickListener!!.onItemRejectClick(incomingOrderList[position],position)
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return incomingOrderList.size
    }

    inner class ViewHolder(binding: ItemIncomingOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemIncomingOrderBinding = binding
    }

    init {
        this.context = context
    }


}
