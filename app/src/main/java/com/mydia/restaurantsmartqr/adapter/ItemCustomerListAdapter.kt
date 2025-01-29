package com.mydia.restaurantsmartqr.adapter

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.util.TypedValue

import android.view.*

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.OrderDetailActivity
import com.mydia.restaurantsmartqr.databinding.ItemCustomerListBinding
import com.mydia.restaurantsmartqr.databinding.ItemIncomingOrderBinding
import com.mydia.restaurantsmartqr.databinding.ItemSectionListBinding
import com.mydia.restaurantsmartqr.databinding.ItemTableListBinding
import com.mydia.restaurantsmartqr.model.CustomerItem
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.tableLIst.TableList


class ItemCustomerListAdapter(context: Context,private var incomingOrderList:List<CustomerItem>) :  RecyclerView.Adapter<ItemCustomerListAdapter.ViewHolder>() {
    var context: Context
    var selectedPostion=-1

    var onItemClickListener: OnItemClickListener? = null



    interface OnItemClickListener {
        fun onItemClick(datum: CustomerItem?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemCustomerListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_customer_list, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*  val marginInDp = 8 // Margin between items in dp

          // Convert margin to pixels
          val marginInPx = TypedValue.applyDimension(
              TypedValue.COMPLEX_UNIT_DIP,
              marginInDp.toFloat(),
              context.resources.displayMetrics
          ).toInt()
          val totalWidth = context.resources.displayMetrics.widthPixels
          // Calculate total margins (margins between items)
          val totalMargins = marginInPx * (5 + 1) // Margin between items + margins at edges

          // Calculate item width
          val itemWidth1 = (totalWidth - totalMargins) / 5
          //  val spaceID="kwYvEmrLarD3qdQyyWv4"
          var itemWidth =itemWidth1
          val layoutParams = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
          layoutParams.rightMargin=totalMargins/2
          layoutParams.leftMargin=totalMargins/2
          layoutParams.width = itemWidth
          layoutParams.height = itemWidth1
          layoutParams.topMargin=totalMargins/2
          layoutParams.bottomMargin=0
          //layoutParams.setMargins(0 , margin , margin, 0) // Apply margins
          holder.itemView.layoutParams = layoutParams*/
        val incomingData = incomingOrderList[position]
        holder.binding.tvDescription.text = incomingData.cCustomerName




        holder.binding.llMain.setOnClickListener {
            onItemClickListener?.onItemClick(incomingData,position)
            //context.startActivity(Intent(context, OrderDetailActivity::class.java).putExtra("orderDetail",incomingOrderList[position]))
        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return incomingOrderList.size
    }

    inner class ViewHolder(binding: ItemCustomerListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemCustomerListBinding = binding
    }

    init {
        this.context = context
    }


}
