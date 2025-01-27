package com.mydia.restaurantsmartqr.viewModel

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.model.login.LoginModel
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMSplash @javax.inject.Inject constructor(private val prefs: PreferencesServices) : BaseViewModel(){

    private val TAG = VMSplash::class.java.simpleName

    var userName = ObservableField("")
    var email = ObservableField("")
    var fcm = ObservableField("")

    fun getUserData(){
        viewModelScope.launch {
            val user = prefs.get(PrefKey.SAVE_LOGIN,LoginModel::class.java)
            Log.e("vm",user!!.name.toString())
            userName.set(user!!.name)
            email.set(user!!.email)
        }

    }

    fun clearData(){
        viewModelScope.launch {
            prefs.clearData()
        }

    }
}