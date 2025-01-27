package com.mydia.restaurantsmartqr.model.login

import com.google.gson.annotations.SerializedName

data class LoginModel(@SerializedName("id"                      ) var id                    : String? = null,
                      @SerializedName("branch_id"               ) var branchId              : String? = null,
                      @SerializedName("name"                    ) var name                  : String? = null,
                      @SerializedName("email"                   ) var email                 : String? = null,
                      @SerializedName("branch_name"             ) var branchName            : String? = null,
                      @SerializedName("currency_symbol"         ) var currencySymbol        : String? = null,
                      @SerializedName("currency"                ) var currency              : String? = null,
                      @SerializedName("currency_decimal_points" ) var currencyDecimalPoints : String? = null,
                      @SerializedName("branch_logo"             ) var branchLogo            : String? = null,
                      @SerializedName("vat_number"              ) var vatNumber             : String? = null,
                      @SerializedName("show_ready_section"              ) var show_ready_section             : String? = null,
                      @SerializedName("cash_register_closed_at" ) var cashRegisterClosedAt  : String? = null,
                      @SerializedName("register_status"         ) var registerStatus        : String? = null

)
