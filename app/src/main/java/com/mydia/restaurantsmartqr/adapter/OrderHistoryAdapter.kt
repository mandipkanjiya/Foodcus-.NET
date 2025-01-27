package com.mydia.restaurantsmartqr.adapter


import android.content.Context
import android.content.Intent
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemCompletedOrderBinding
import com.mydia.restaurantsmartqr.databinding.ItemOrderHistoryBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.prefrences.PrefKey


class OrderHistoryAdapter(context: Context,private var currency:String) :
    RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>() {
    var context: Context

    private var orderList:ArrayList<OrderList> = ArrayList<OrderList>()

    fun setFilter(filterEvents: List<OrderList>) {
        orderList.addAll(filterEvents)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemOrderHistoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order_history, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val orderListData = orderList!![position]


        if(orderListData.orderRef!!.isNotEmpty()){
            holder.binding.tvOrderId.text = "#"+orderListData.orderRef
        }else{
            holder.binding.tvOrderId.text = "-"
        }

        if(orderListData.section_name!!.isNotEmpty()){
            holder.binding.tvSectionName.text = orderListData.section_name
        }else{
            holder.binding.tvSectionName.text = "-"
        }
        if(orderListData.order_type_name!!.isNotEmpty()){
            holder.binding.tvOrdertype.text = orderListData.order_type_name
        }else{
            holder.binding.tvOrdertype.text = "-"
        }

        if(orderListData.tableName!!.isNotEmpty()){
            holder.binding.tvTableName.text = orderListData.tableName
        }else{
            holder.binding.tvTableName.text = "-"
        }

        if(orderListData.orderType.equals("2")){
            //dinin
            holder.binding.llMain.background=context.getDrawable(R.drawable.bg_dinein_order)

        }else if(orderListData.orderType.equals("1")){
            //pickup
            holder.binding.llMain.background=context.getDrawable(R.drawable.bg_pickup_order)
        }
        if(orderListData.corderTotal!!.isNotEmpty()){
            holder.binding.tvTotal.text =  currency+" "+orderListData.corderTotal
        }else{
            holder.binding.tvTotal.text = "-"
        }
        var payment=""
        payment = when (orderListData.paymentMethod) {
            "1" -> {
                "Cash"
            }
            "2" -> {
                "Card on pickup"
            }
            else -> {
                "Credit Card"
            }
        }
        if(orderListData.paymentMethod!!.isNotEmpty()){
            holder.binding.tvPaymentType.text = payment
        }else{
            holder.binding.tvPaymentType.text = "-"
        }


        holder.binding.tvOrderTime.text = orderListData.createdDate


        holder.binding.llMain.setOnClickListener {
            context.startActivity(Intent(context,OrderDetailActivity::class.java).putExtra("orderDetail",orderList[position]))
        }




        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return orderList!!.size
    }

    inner class ViewHolder(binding: ItemOrderHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemOrderHistoryBinding = binding
    }


    init {
        this.context = context
    }


}
