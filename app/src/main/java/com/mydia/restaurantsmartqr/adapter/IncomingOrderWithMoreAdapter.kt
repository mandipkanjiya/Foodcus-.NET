package com.mydia.restaurantsmartqr.adapter


import android.content.Context

import android.view.*

import androidx.recyclerview.widget.RecyclerView
import com.mydia.restaurantsmartqr.databinding.ItemFilterMoreItemBinding
import com.mydia.restaurantsmartqr.databinding.ItemIncomingOrderBinding
import com.mydia.restaurantsmartqr.model.orderList.OrderList


class IncomingOrderWithMoreAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>{

    lateinit var orderList: ArrayList<OrderList>
    var visibleOrderList: ArrayList<OrderList> = arrayListOf()
    lateinit var context:Context
    constructor()

    constructor(
        context: Context, orderList: ArrayList<OrderList>){
        this.orderList = orderList

        maxVisibleItem = if (orderList.size > 3) {
            3
        } else {
            orderList.size
        }
        this.context = context
        onLessView()
    }

    private val TYPE_MORE = 0
    private val TYPE_ITEM = 1
    var maxVisibleItem = 0

    var isVisibleAllItem: Boolean = false

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemAcceptClick(datum: OrderList?, pos: Int)
        fun onItemRejectClick(datum: OrderList?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_ITEM) {
            val view = ItemIncomingOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AllTypeViewHolder(view)
        } else {
            val view = ItemFilterMoreItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            AddMoreViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_ITEM) {

            (holder as AllTypeViewHolder).bind(visibleOrderList[position])

                notifyItemChanged(position)

        } else {
            (holder as AddMoreViewHolder).bind(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    onLessView()
                }
            }, isVisibleAllItem)
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return if (orderList.size > 3) {
            visibleOrderList.size + 1
        } else {
            visibleOrderList.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (visibleOrderList.size + 1 == (position + 1)) TYPE_MORE else TYPE_ITEM
    }

    fun onLessView() {
        isVisibleAllItem = !isVisibleAllItem

        if (isVisibleAllItem) {
            val ff = orderList.subList(0, maxVisibleItem).toList()
            visibleOrderList .clear()
            visibleOrderList.addAll(ff)

            notifyDataSetChanged()
        } else {
            visibleOrderList.clear()
            visibleOrderList.addAll(orderList)
            notifyDataSetChanged()

        }
    }



    class AllTypeViewHolder(var binding: ItemIncomingOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        lateinit var incomingOrderItemsAdapter: IncomingOrderItemsAdapter
        fun bind(incomingData: OrderList) {
            if(incomingData.cOrderType.equals("2")){
                //dinin
             // binding.tvOrderId.text = incomingData.tableName
               binding.tvName.visibility = View.GONE
                binding.btnAccept.visibility = View.GONE
               binding.btnReject.visibility = View.GONE

            }else if(incomingData.cOrderType.equals("1")){
                //pickup
               binding.tvOrderId.text = "#${incomingData.cOrderCode}"
               binding.tvName.visibility = View.VISIBLE
                binding.tvName.text = incomingData.cCustomerName
               binding.btnAccept.visibility = View.VISIBLE
                binding.btnReject.visibility = View.VISIBLE
            }
         //   binding.tvTime.text = incomingData.estimatedTime
           binding.tvOrderType.text = incomingData.cOrderType



        }
    }

    class AddMoreViewHolder(var footerBinding: ItemFilterMoreItemBinding) :
        RecyclerView.ViewHolder(footerBinding.root) {
        fun bind(f: View.OnClickListener, isMoreVisible: Boolean) {
            if (!isMoreVisible) {
                footerBinding.tvFilterColorName.text = "- Hide"
            } else {
                footerBinding.tvFilterColorName.text = "+ More"
            }

            footerBinding.tvFilterColorName.setOnClickListener(f)
        }
    }
}
