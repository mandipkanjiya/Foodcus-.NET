package com.mydia.restaurantsmartqr.repository



import com.mydia.restaurantsmartqr.api.Api
import com.mydia.restaurantsmartqr.base.BaseRepository
import com.mydia.restaurantsmartqr.model.AppUpdateResponse
import com.mydia.restaurantsmartqr.model.BaseResponse
import com.mydia.restaurantsmartqr.model.CreateOrderModel
import com.mydia.restaurantsmartqr.model.OrderStatusResponse
import com.mydia.restaurantsmartqr.model.login.LoginResponse
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.model.product.CategoryListResponse
import com.mydia.restaurantsmartqr.model.product.ProductListResponse
import com.mydia.restaurantsmartqr.model.sectionLIst.SectionListResponse

import com.mydia.restaurantsmartqr.model.tableLIst.TableListResponse
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private var api: Api,

    ) :
    BaseRepository() {




    /*Login API call*/
    suspend fun doLogin(
        scope: CoroutineScope,
        onSuccess: (LoginResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        loginRequest: HashMap<String,String>,
        url:String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.login(url,loginRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    /*orderlist  API call*/
    suspend fun orderList(
        scope: CoroutineScope,
        onSuccess: (OrderListResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        orderListRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.orderList(url,orderListRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    /*orderstatus  API call*/
    suspend fun orderStatus(
        scope: CoroutineScope,
        onSuccess: (OrderStatusResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        orderstatusRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.orderStatus(url,orderstatusRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    suspend fun tableList(
        scope: CoroutineScope,
        onSuccess: (TableListResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        tableListRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.tableList(url,tableListRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    suspend fun sectionList(
        scope: CoroutineScope,
        onSuccess: (SectionListResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        tableListRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.sectionList(url,tableListRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    suspend fun reviewLink(
        scope: CoroutineScope,
        onSuccess: (BaseResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        loginRequest: HashMap<String, String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.reviewLinkApi(url,loginRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )
    suspend fun branchLink(
        scope: CoroutineScope,
        onSuccess: (BaseResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        loginRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.branchLinkApi(url,loginRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    suspend fun addPointApi(
        scope: CoroutineScope,
        onSuccess: (BaseResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        loginRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.addPointApi(url,loginRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )
    suspend fun redeemPointApi(
        scope: CoroutineScope,
        onSuccess: (BaseResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        loginRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.redeemPointApi(url,loginRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )
    suspend fun appUpdateApi(
        scope: CoroutineScope,
        onSuccess: (AppUpdateResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        loginRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.appUpdateApi(url,loginRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )
    suspend fun productList(
        scope: CoroutineScope,
        onSuccess: (ProductListResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        tableListRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.productList(url,tableListRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )

    suspend fun categoryList(
        scope: CoroutineScope,
        onSuccess: (CategoryListResponse?) -> Unit,
        onErrorAction: (String?) -> Unit,
        tableListRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.categoryList(url,tableListRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )
    suspend fun placeOrderApi(
        scope: CoroutineScope,
        onSuccess: (CreateOrderModel?) -> Unit,
        onErrorAction: (String?) -> Unit,
        tableListRequest: HashMap<String,String>,
        url: String
    ) =
        sendRequest(
            scope = scope,
            client = {
                api.placeOrderApi(url,tableListRequest)
            },
            onSuccess = {
                onSuccess(it)
            },
            onErrorAction = {
                onErrorAction(it)
            }
        )
}
