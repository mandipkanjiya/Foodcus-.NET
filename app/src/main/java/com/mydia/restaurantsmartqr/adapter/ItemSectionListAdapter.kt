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
import com.mydia.restaurantsmartqr.databinding.ItemSectionListBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionList


class ItemSectionListAdapter(context: Context,private var incomingOrderList:List<SectionList>) :  RecyclerView.Adapter<ItemSectionListAdapter.ViewHolder>() {
    var context: Context
    var selectedPostion=0

    var onItemClickListener: OnItemClickListener? = null



    interface OnItemClickListener {
        fun onItemClick(datum: SectionList?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemSectionListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_section_list, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incomingData = incomingOrderList[position]
        holder.binding.tvOrderId.text = incomingData.cName

        if(selectedPostion == position){
            //dinin
            holder.binding.llMain.background=context.getDrawable(R.drawable.section_selected_bg)
            holder.binding.tvOrderId.setTextColor(context.getColor(R.color.color2e71f9))
        }else{
            //pickup
            holder.binding.llMain.background=context.getDrawable(R.drawable.section_unselected_bg)
            holder.binding.tvOrderId.setTextColor(context.getColor(R.color.gray))
        }


        holder.binding.llMain.setOnClickListener {
            selectedPostion = position
            onItemClickListener?.onItemClick(incomingOrderList[position],position)
           notifyDataSetChanged()
        }

        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return incomingOrderList.size
    }

    inner class ViewHolder(binding: ItemSectionListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemSectionListBinding = binding
    }

    init {
        this.context = context
    }


}
