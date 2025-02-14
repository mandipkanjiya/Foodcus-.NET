package com.mydia.restaurantsmartqr.api


import com.mydia.restaurantsmartqr.model.AppUpdateResponse
import com.mydia.restaurantsmartqr.model.BaseResponse
import com.mydia.restaurantsmartqr.model.CreateOrderModel
import com.mydia.restaurantsmartqr.model.CustomerResponse
import com.mydia.restaurantsmartqr.model.CustomerWalletHistoryDetailResponse
import com.mydia.restaurantsmartqr.model.DottnetBaseResponse
import com.mydia.restaurantsmartqr.model.EmployeListResponse
import com.mydia.restaurantsmartqr.model.OrderStatusResponse
import com.mydia.restaurantsmartqr.model.alertList.AlertListResponse
import com.mydia.restaurantsmartqr.model.alertList.UpdateAlertResponse
import com.mydia.restaurantsmartqr.model.login.LoginResponse
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.model.product.CategoryListResponse
import com.mydia.restaurantsmartqr.model.product.ProductListResponse
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionListResponse

import com.mydia.restaurantsmartqr.model.tableLIst.TableListResponse



import retrofit2.http.*

interface Api {


    @FormUrlEncoded
    @POST
    suspend fun login(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): LoginResponse?

    @FormUrlEncoded
    @POST
    suspend fun reviewLinkApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): DottnetBaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun branchLinkApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun redeemPointApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun addPointApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?


    @GET
    suspend fun orderList(@Url url:String,@QueryMap requestFieldMap: HashMap<String,String>): OrderListResponse?


    @GET
    suspend fun tableList(@Url url:String,@QueryMap requestFieldMap: HashMap<String,String>): TableListResponse?

    @FormUrlEncoded
    @POST
    suspend fun sectionList(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): SectionListResponse?

    @FormUrlEncoded
    @POST
    suspend fun employeList(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): EmployeListResponse?



    @FormUrlEncoded
    @POST
    suspend fun appUpdateApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): AppUpdateResponse?

    @FormUrlEncoded
    @POST
    suspend fun productList(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): ProductListResponse?

    @FormUrlEncoded
    @POST
    suspend fun categoryList(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CategoryListResponse?

    @FormUrlEncoded
    @POST
    suspend fun placeOrderApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,Any>): CreateOrderModel?


    @FormUrlEncoded
    @POST
    suspend fun alertListApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): AlertListResponse?
    @FormUrlEncoded
    @POST
    suspend fun updateAlertApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): UpdateAlertResponse?

    @GET
    suspend fun orderListApi(@Url url:String,@QueryMap requestFieldMap: HashMap<String,String>): OrderListResponse?

    @GET
    suspend fun orderStatusApi(@Url url:String,@QueryMap requestFieldMap: HashMap<String,String>): OrderStatusResponse?

    @FormUrlEncoded
    @POST
    suspend fun addCustomerApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CustomerResponse?

    @FormUrlEncoded
    @POST
    suspend fun customerListApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CustomerResponse?

    @FormUrlEncoded
    @POST
    suspend fun getCustomerWalletHistoryApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CustomerWalletHistoryDetailResponse?

}
