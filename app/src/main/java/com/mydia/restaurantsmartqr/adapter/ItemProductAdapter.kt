package com.mydia.restaurantsmartqr.adapter

import android.content.Context
import android.graphics.Paint
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.databinding.ItemProductBinding
import com.mydia.restaurantsmartqr.model.product.ProductModel
import java.util.Locale


class ItemProductAdapter(context: Context,private var incomingOrderList:List<ProductModel>) :  RecyclerView.Adapter<ItemProductAdapter.ViewHolder>() {
    var context: Context
    var selectedPostion=0

    var onItemClickListener: OnItemClickListener? = null



    interface OnItemClickListener {
        fun onItemClick(datum: ProductModel?, pos: Int)
    }

    //method of item click
    fun setOnItemCLickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val incomingData = incomingOrderList[position]
        if (incomingData.cName != null && incomingData.cName.length > 0
        ) {
            val upperString: String = incomingData.cName.substring(0, 1)
                .uppercase() + incomingData.cName.substring(1)
                .lowercase()
            holder.binding.tvProductName.text = "" + upperString
        }

        if (incomingData.fPrice != null && incomingData.fPrice.toString().length > 0) {
            holder.binding.tvProductPrice.text = String.format(Locale.ENGLISH, "%.3f", incomingData.fPrice.toDouble())
        }
      /*  if(selectedPostion == position){
            //dinin
            holder.binding.llMain.background=context.getDrawable(R.drawable.section_selected_bg)

        }else{
            //pickup
            holder.binding.llMain.background=context.getDrawable(R.drawable.section_unselected_bg)
        }*/
        if (incomingData.fSpecialPrice != null && incomingData.fSpecialPrice.toString().length > 0 && !String.format(Locale.ENGLISH, "%.3f", incomingData.fSpecialPrice).equals("0.000", ignoreCase = true)
        ) {
            holder.binding.tvSpecialPrice.setVisibility(View.VISIBLE)
            holder.binding.tvProductPrice.paintFlags = holder.binding.tvProductPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            //  tvSpecPrice.setText(productDetails.get(0).getnSpecialPrice());
            holder.binding.tvProductPrice.textSize = 13f
            holder.binding.tvSpecialPrice.setText(java.lang.String.format(Locale.ENGLISH, "%.3f", incomingData.fSpecialPrice))
        } else {
            holder.binding.tvSpecialPrice.setVisibility(View.GONE)
            holder.binding.tvProductPrice.textSize = 16f
            holder.binding.tvProductPrice.paintFlags = 0
        }
        if (incomingData.cImage != null && incomingData.cImage.length > 0) {
            Glide.with(context)
                .load(incomingData.cImage)
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(35)))
                .into(holder.binding.ivProduct)
        } else {
            Glide.with(context)
                .load(R.drawable.app_icon)
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(35)))
                .into(holder.binding.ivProduct)
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

    inner class ViewHolder(binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var binding: ItemProductBinding = binding
    }

    init {
        this.context = context
    }


}
