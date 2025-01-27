package com.mydia.restaurantsmartqr.viewModel
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.mydia.restaurantsmartqr.base.BaseViewModel
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class VMWebview @javax.inject.Inject constructor(private val prefs: PreferencesServices) : BaseViewModel(){

    private val TAG = VMWebview::class.java.simpleName

    var weburl = ObservableField("")
    //var salle = ObservableField("")

    fun getUserData(){
        viewModelScope.launch {
           // val user = prefs.get(PrefKey.SAVE_LOGIN, UserModel::class.java)
            //  Log.e("vm",user!!.name.toString())
            //  userName.set(user!!.name)
            //  email.set(user!!.email)
        }

    }

    fun clearData(){
        viewModelScope.launch {
            prefs.clearData()
        }

    }
}