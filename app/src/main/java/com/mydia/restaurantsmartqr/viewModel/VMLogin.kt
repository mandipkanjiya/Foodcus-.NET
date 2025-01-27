package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.fcm.FirebaseDataManager
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.repository.LoginRepository
import com.mydia.restaurantsmartqr.repository.LoginRequest
import com.mydia.restaurantsmartqr.util.Constants.DEVICE_TYPE
import com.mydia.restaurantsmartqr.util.Constants.LOGIN_URL
import com.mydia.restaurantsmartqr.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@HiltViewModel
class VMLogin @javax.inject.Inject constructor(private val prefs: PreferencesServices,
                                               private  val loginRepository: LoginRepository) : BaseViewModel(){

    private val TAG = VMLogin::class.java.simpleName

/*    var userName = ObservableField("bhakti@reastaurant.com")
    var password = ObservableField("123456")*/
    var userName = ObservableField("")
    var password = ObservableField("")
    var isShowReady = ObservableField("")

    private val eventChannel = Channel<Int>()
    val eventFlow = eventChannel.receiveAsFlow()

    fun loginApi(loginRequest: LoginRequest)=viewModelScope.launch{
            triggerLoadingDetection(true)
             isLoading.set(true)
            loginRepository.doLogin(
                scope = viewModelScope,
                onSuccess = {
                    triggerLoadingDetection(false)
                    isLoading.set(false)
                    if(it!!.status == 1){
                        viewModelScope.launch {
                            prefs.put(PrefKey.SAVE_LOGIN,it.result)
                            prefs.putBoolean(PrefKey.IS_LOGIN,true)
                            prefs.putString(PrefKey.CURRENCY, it.result!!.nDefaultCurrency)
                           // prefs.putString(PrefKey.BRANCH_ID, it.result!!.branchId)
                           //isShowReady.set(it.result!!.show_ready_section)

                            FirebaseDataManager.initializeWihTVID(it.result!!.nUserId.toString(),prefs)
                            FirebaseDataManager.firebaseDataReference
                            //eventChannel.send(1)
                            eventChannel.send(2)
                        }

                      //  Log.e("deviceId", it.result!!.branchId.toString())

                     }
                    else{
                        showToast(it.message.toString())
                    }

                }, onErrorAction = {
                    triggerLoadingDetection(false)
                    isLoading.set(false)
                    triggerShowMessage(it)
                    showToast(it.toString())
                    Log.e("error",it.toString())
                }, loginRequest.toFieldMap(),prefs.getBaseUrl(PrefKey.BASE_URL).toString()+LOGIN_URL
            )
        }

    fun onLoginClick(){
        Log.e("error","click")
        if (userName.get() == "") {
            Log.e("error","Please enter email")
            showToast("Please enter email")
            return
        }
        if (!Utils.isValidEmail(userName.get())) {
            showToast("Please enter valid email")
            return
        }
        if (password.get() == "") {
            showToast("Please enter password")
            return
        }
        viewModelScope.launch {
            val fcm = prefs.getString(PrefKey.FCM_TOKEN)
            Log.e(TAG,"fcm, $fcm")
            val loginRequest = LoginRequest(userName.get().toString() , password.get().toString(),prefs.getString(PrefKey.FCM_TOKEN),DEVICE_TYPE)
            loginApi(loginRequest)
        }

    }
}