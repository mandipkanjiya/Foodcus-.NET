package com.mydia.restaurantsmartqr.model.orderList

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderList(
    @SerializedName("nid"               ) var nid             : String?          = null,
    @SerializedName("order_ref"         ) var orderRef        : String?          = null,
    @SerializedName("customer_id"       ) var customerId      : String?          = null,
    @SerializedName("nbranch_id"        ) var nbranchId       : String?          = null,
    @SerializedName("ntable_id"         ) var ntableId        : String?          = null,
    @SerializedName("custom_module_id"  ) var customModuleId  : String?          = null,
    @SerializedName("nbrowser_id"       ) var nbrowserId      : String?          = null,
    @SerializedName("norder_status"     ) var norderStatus    : String?          = null,
    @SerializedName("payment_method"    ) var paymentMethod   : String?          = null,
    @SerializedName("order_type"        ) var orderType       : String?          = null,
    @SerializedName("pickup_time"       ) var pickupTime      : String?          = null,
    @SerializedName("pickup_date"       ) var pickupDate      : String?          = null,

    @SerializedName("corder_total"      ) var corderTotal     : String?          = null,
    @SerializedName("corder_note"       ) var corderNote      : String?          = null,
    @SerializedName("send_culary"       ) var sendCulary      : String?          = null,
    @SerializedName("accepted_on"       ) var acceptedOn      : String?          = null,
    @SerializedName("estimated_time"    ) var estimatedTime   : String?          = null,
    @SerializedName("rating"            ) var rating          : String?          = null,
    @SerializedName("has_rated"         ) var hasRated        : String?          = null,
    @SerializedName("ncreated_on"       ) var ncreatedOn      : String?          = null,
    @SerializedName("cutomer_name"      ) var cutomerName     : String?          = null,
    @SerializedName("table_name"        ) var tableName       : String?          = null,
    @SerializedName("created_date"      ) var createdDate     : String?          = null,
    @SerializedName("order_status_name" ) var orderStatusName : String?          = null,
    @SerializedName("section_name"       ) var section_name      : String?          = null,
    @SerializedName("order_type_name" ) var order_type_name : String?          = null,
    @SerializedName("tip" ) var tip : String?          = null,
    @SerializedName("items"             ) var items           : ArrayList<Items> = arrayListOf(),
    @SerializedName("cutomer"           ) var cutomer         : Cutomer?         = Cutomer()


):Parcelable
