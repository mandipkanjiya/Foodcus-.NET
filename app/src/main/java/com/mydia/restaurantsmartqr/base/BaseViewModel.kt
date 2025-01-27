package  com.mydia.restaurantsmartqr.base


import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import kotlinx.coroutines.channels.Channel

import kotlinx.coroutines.launch



open class BaseViewModel  : ViewModel() {

    private val loadingDetection by lazy { Channel<Boolean>() }
    private val showMessage by lazy { Channel<String>() }
    private val _toastLiveData = MutableLiveData<String>()
    val toastLiveData: LiveData<String> = _toastLiveData

    var isLoading = ObservableBoolean(false)
    init {

        Log.e("baseviewmodel","initcalled")
     }

    fun triggerLoadingDetection(status: Boolean){
        viewModelScope.launch {
            loadingDetection.send(status)
        }
    }
    fun triggerShowMessage(message: String?){
        message?.let {
            viewModelScope.launch {
                showMessage.send(it)
            }

        } ?: run {
            viewModelScope.launch {
                showMessage.send("Something went wrong")
            }
        }
    }

    fun showToast(msg: String) {
        _toastLiveData.value = msg
    }

}
