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

        if(acceptedData.orderType.equals("2")){
            //dinin
            if(acceptedData.tableName!!.isNotEmpty()){
                holder.binding.tvOrderId.text = acceptedData.tableName
            }else{
                holder.binding.tvOrderId.text = "# "+acceptedData.orderRef
            }

            holder.binding.tvCustomerName.visibility = View.GONE
            holder.binding.llPickUp.visibility = View.GONE
            holder.binding.tvTableNAme.visibility = View.GONE
            holder.binding.tvPaymentType.visibility = View.GONE
            holder.binding.tvOrderNote.visibility = View.VISIBLE
            if(acceptedData.corderNote!!.isNotEmpty()){
                holder.binding.tvOrderNote.text = "Order Note: "+ acceptedData.corderNote
            }


            holder.binding.btnReadyTocollect.text = "Punched On POS"
          //  holder.binding.btnReadyTocollect.supportBackgroundTintList = context.applicationContext.getColorStateList(R.color.darkGreen)
            holder.binding.btnReadyTocollect.visibility = View.VISIBLE

            holder.binding.relMain.background=context.getDrawable(R.drawable.bg_dinein_order)
        }
        else if(acceptedData.orderType.equals("1")){
            //pickup
            holder.binding.tvOrderId.text = "#${acceptedData.orderRef}"
            holder.binding.tvCustomerName.visibility = View.VISIBLE
            holder.binding.btnReadyTocollect.visibility = View.VISIBLE

            holder.binding.tvOrderNote.visibility = View.GONE
            holder.binding.tvTableNAme.visibility = View.VISIBLE
          //  holder.binding.tvPaymentType.visibility = View.VISIBLE
            if(acceptedData.tableName.toString().length > 0) {
                holder.binding.tvTableNAme.text = "Table Name: " + acceptedData.tableName
            }
           // holder.binding.tvPaymentType.text = "Payment Type: "+acceptedData.paymentMethod
            holder.binding.tvRemainingTime.text = acceptedData.pickupTime.toString()
            holder.binding.tvDate.text = acceptedData.pickupDate.toString()
            holder.binding.llPickUp.visibility = View.VISIBLE

            holder.binding.tvCustomerName.text = acceptedData.cutomerName
            holder.binding.relMain.background= context.getDrawable(R.drawable.bg_pickup_order)

        }
        holder.binding.tvSection.text = acceptedData.section_name
        if(acceptedData.items.size > 3){
            holder.binding.tvViewMore.visibility = View.VISIBLE
        }else{
            holder.binding.tvViewMore.visibility = View.GONE
        }

        holder.binding.tvOrderType.text = acceptedData.order_type_name

        incomingOrderItemsAdapter = IncomingOrderItemsAdapter(context, acceptedData.items)
        holder.binding.rvAcceptedItems.adapter = incomingOrderItemsAdapter
      /*  acceptedOrderItemsAdapter = AcceptedOrderItemsAdapter(context, acceptedData.items)
        holder.binding.rvAcceptedItems.adapter = acceptedOrderItemsAdapter*/
        holder.binding.btnReadyTocollect.setOnClickListener {
            onItemClickListener!!.onItemAcceptClick(acceptedData,position,acceptedData.orderType.toString())
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
