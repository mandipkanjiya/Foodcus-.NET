package com.mydia.restaurantsmartqr.api


import com.mydia.restaurantsmartqr.model.AppUpdateResponse
import com.mydia.restaurantsmartqr.model.BaseResponse
import com.mydia.restaurantsmartqr.model.CreateOrderModel
import com.mydia.restaurantsmartqr.model.CustomerResponse
import com.mydia.restaurantsmartqr.model.OrderStatusResponse
import com.mydia.restaurantsmartqr.model.login.LoginResponse
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.model.product.CategoryListResponse
import com.mydia.restaurantsmartqr.model.product.ProductListResponse
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionListResponse

import com.mydia.restaurantsmartqr.model.tableLIst.TableListResponse
import com.mydia.restaurantsmartqr.util.Constants.ADD_POINT
import com.mydia.restaurantsmartqr.util.Constants.BRANCH_LINK
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_STATUS_URL
import com.mydia.restaurantsmartqr.util.Constants.REDEEM_POINT
import com.mydia.restaurantsmartqr.util.Constants.REVIEW_LINK
import com.mydia.restaurantsmartqr.util.Constants.SECTION_LIST
import com.mydia.restaurantsmartqr.util.Constants.TABLE_LIST

import retrofit2.http.*

interface Api {


    @FormUrlEncoded
    @POST
    suspend fun login(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): LoginResponse?

    @FormUrlEncoded
    @POST
    suspend fun reviewLinkApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun branchLinkApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun redeemPointApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun addPointApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): BaseResponse?

    @FormUrlEncoded
    @POST
    suspend fun orderList(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): OrderListResponse?



    @FormUrlEncoded
    @POST
    suspend fun orderStatus(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): OrderStatusResponse?

    @GET
    suspend fun tableList(@Url url:String,@QueryMap requestFieldMap: HashMap<String,String>): TableListResponse?

    @FormUrlEncoded
    @POST
    suspend fun sectionList(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): SectionListResponse?

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
    suspend fun placeOrderApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CreateOrderModel?

    @FormUrlEncoded
    @POST
    suspend fun addCustomerApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CustomerResponse?

    @FormUrlEncoded
    @POST
    suspend fun customerListApi(@Url url:String,@FieldMap requestFieldMap: HashMap<String,String>): CustomerResponse?

}
