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
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class AcceptedOrderAdapter(context: Context,private var acceptedOrderList:List<OrderList>) :
    RecyclerView.Adapter<AcceptedOrderAdapter.ViewHolder>() {
    var context: Context
    lateinit var acceptedOrderItemsAdapter: AcceptedOrderItemsAdapter
    lateinit var incomingOrderItemsAdapter: IncomingOrderItemsAdapter

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemAcceptClick(datum: OrderList?, pos: Int,orderType:String)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemAcceptedOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_accepted_order, parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val acceptedData = acceptedOrderList[position]

        if(acceptedData.cOrderType.equals("DineIn")){
            //dinin
            if(acceptedData.cTableName!!.isNotEmpty()){
                holder.binding.tvOrderId.text = acceptedData.cTableName
            }else{
                holder.binding.tvOrderId.text = "# "+acceptedData.cOrderCode
            }
         //   holder.binding.tvOrderId.text = "# "+acceptedData.cOrderCode
            holder.binding.tvCustomerName.visibility = View.GONE
            holder.binding.llPickUp.visibility = View.GONE
            holder.binding.tvTableNAme.visibility = View.GONE
            holder.binding.tvPaymentType.visibility = View.GONE
            holder.binding.tvOrderNote.visibility = View.VISIBLE
            if(acceptedData.cOrderNote!!.isNotEmpty()){
                holder.binding.tvOrderNote.text = "Order Note: "+ acceptedData.cOrderNote
            }


            holder.binding.btnReadyTocollect.text = "Punched On POS"
          //  holder.binding.btnReadyTocollect.supportBackgroundTintList = context.applicationContext.getColorStateList(R.color.darkGreen)
            holder.binding.btnReadyTocollect.visibility = View.VISIBLE

            holder.binding.relMain.background=context.getDrawable(R.drawable.bg_dinein_order)
        }
        else if(acceptedData.cOrderType.equals("Pickup")){
            //pickup
            holder.binding.tvOrderId.text = "#${acceptedData.cOrderCode}"
            holder.binding.tvCustomerName.visibility = View.VISIBLE
            holder.binding.btnReadyTocollect.visibility = View.VISIBLE

            holder.binding.tvOrderNote.visibility = View.GONE
            holder.binding.tvTableNAme.visibility = View.GONE
          //  holder.binding.tvPaymentType.visibility = View.VISIBLE
           /* if(acceptedData.tableName.toString().length > 0) {
                holder.binding.tvTableNAme.text = "Table Name: " + acceptedData.tableName
            }*/
            holder.binding.tvPaymentType.text = "Payment Type: "+acceptedData.cPaymentTerms
            holder.binding.tvRemainingTime.text = acceptedData.cDeliveryTime.toString()
            holder.binding.tvDate.text = acceptedData.dtDeliveryDate.toString()
            holder.binding.llPickUp.visibility = View.VISIBLE
            holder.binding.tvCustomerName.text = acceptedData.cCustomerName
            holder.binding.relMain.background= context.getDrawable(R.drawable.bg_pickup_order)

        }
        //holder.binding.tvSection.text = acceptedData.section_name
        if(acceptedData.Item.size > 3){
            holder.binding.tvViewMore.visibility = View.VISIBLE
        }else{
            holder.binding.tvViewMore.visibility = View.GONE
        }

        holder.binding.tvOrderType.text = acceptedData.cOrderType

        incomingOrderItemsAdapter = IncomingOrderItemsAdapter(context, acceptedData.Item)
        holder.binding.rvAcceptedItems.adapter = incomingOrderItemsAdapter
      /*  acceptedOrderItemsAdapter = AcceptedOrderItemsAdapter(context, acceptedData.items)
        holder.binding.rvAcceptedItems.adapter = acceptedOrderItemsAdapter*/
        holder.binding.btnReadyTocollect.setOnClickListener {
            onItemClickListener!!.onItemAcceptClick(acceptedData,position,acceptedData.cOrderType.toString())
        }
        holder.binding.rvMain.setOnClickListener {
            context.startActivity(Intent(context, OrderDetailActivity::class.java).putExtra("orderDetail",acceptedOrderList[position]))
        }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return acceptedOrderList.size
    }

    inner class ViewHolder(binding: ItemAcceptedOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemAcceptedOrderBinding = binding
    }

    init {
        this.context = context
    }


}
