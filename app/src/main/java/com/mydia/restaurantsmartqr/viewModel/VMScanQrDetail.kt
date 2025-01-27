package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.login.LoginModel
import com.mydia.restaurantsmartqr.model.orderList.OrderListResponse
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.AddPointRequest
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.OrderListRequest
import com.mydia.restaurantsmartqr.repository.OrderStatusRequest
import com.mydia.restaurantsmartqr.repository.RedeemPointRequest
import com.mydia.restaurantsmartqr.repository.ReviewAndBranchLinkRequest
import com.mydia.restaurantsmartqr.util.Constants.ADD_POINT
import com.mydia.restaurantsmartqr.util.Constants.APP_VERSIONS
import com.mydia.restaurantsmartqr.util.Constants.BRANCH_LINK
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_LIST_URL
import com.mydia.restaurantsmartqr.util.Constants.ORDER_STATUS_URL
import com.mydia.restaurantsmartqr.util.Constants.REDEEM_POINT
import com.mydia.restaurantsmartqr.util.Constants.REVIEW_LINK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMScanQrDetail @javax.inject.Inject constructor(private val prefs: PreferencesServices) : BaseViewModel(){
   init {

   }
}