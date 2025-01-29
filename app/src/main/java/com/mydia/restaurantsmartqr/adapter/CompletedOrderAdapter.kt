package com.mydia.restaurantsmartqr.adapter


import android.content.Context
import android.content.Intent
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemCompletedOrderBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class CompletedOrderAdapter(context: Context) :
    RecyclerView.Adapter<CompletedOrderAdapter.ViewHolder>() {
    var context: Context
    private var orderList:ArrayList<OrderList> = ArrayList<OrderList>()

    fun setFilter(filterEvents: List<OrderList>) {
        orderList.addAll(filterEvents)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCompletedOrderBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_completed_order, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val orderListData = orderList!![position]
       /* if(orderListData.tableName!!.isNotEmpty()){
            holder.binding.tvOrderId.text = "#"+orderListData.tableName
        }else{
            holder.binding.tvOrderId.text = "#"+orderListData.orderRef
        }*/
        holder.binding.tvOrderId.text = "#"+orderListData.cOrderCode
        holder.binding.tvCustomerName.text =orderListData.cCustomerName
       // holder.binding.tvSectionName.text = orderListData.section_name

        val time  = orderListData.dtGeneratedDate?.split(" ")
        holder.binding.tvOrderDate.text = time!![0]
        holder.binding.tvOrderTime.text = time[1]

        holder.binding.llMain.setOnClickListener {
            context.startActivity(Intent(context,OrderDetailActivity::class.java).putExtra("orderDetail",orderList!![position]))
        }




        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return orderList!!.size
    }

    inner class ViewHolder(binding: ItemCompletedOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCompletedOrderBinding = binding
    }


    init {
        this.context = context
    }


}
