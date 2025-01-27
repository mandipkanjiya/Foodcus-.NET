package com.mydia.restaurantsmartqr.base



import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import androidx.viewbinding.ViewBinding
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.util.NetworkConnectivity
import com.mydia.restaurantsmartqr.util.NetworkStatus
import com.mydia.restaurantsmartqr.util.NetworkStatusHelper


import java.util.Timer
import java.util.TimerTask

import javax.inject.Inject


abstract class BaseActivity<BindingType : ViewBinding, ViewModelType : BaseViewModel> :
    AppCompatActivity() {

    lateinit var binding: BindingType
    abstract fun getViewBinding(): BindingType
    abstract fun observeViewModel()
    abstract fun onActivityCreated()

    open fun onTickTick(){}
    open fun onNetworkAvailable(){}
    open fun onNetworkUnavailable(){}


    protected abstract val viewModel: ViewModelType

    @Inject
    lateinit var networkConnectivity: NetworkConnectivity

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    @Inject
    lateinit var prefs: PreferencesServices





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        binding = getViewBinding()
        setContentView(binding.root)

        viewModel.toastLiveData.observe(this, Observer {
            showToast(it)
        })
        onActivityCreated()
        observeViewModel()

        observers()

    }
    fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }


    private fun observers(){
        NetworkStatusHelper(this).observe(this) {
            when (it) {
                NetworkStatus.Available -> onNetworkAvailable()
                NetworkStatus.Unavailable -> onNetworkUnavailable()
            }
        }
    }
    fun startTickTick(){
        stopTickTick()
        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                onTickTick()
            }
        }
        timer?.schedule(timerTask, 5000, 1000)
    }

     fun stopTickTick(){

        timer?.cancel()
    }
    override fun onStop() {
        super.onStop()
       // stopTickTick()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTickTick()
    }
    override fun onResume() {
        super.onResume()
        startTickTick()
    }

    fun getItemList(): List<String> {
        val spots = ArrayList<String>()
        spots.add("Floor 1")
        spots.add("Floor 2")
        spots.add("Floor 3")
        spots.add("Floor 4")
        spots.add("Floor 5")
        spots.add("Floor 6")
        spots.add("Floor 7")
        spots.add("Floor 8")
        spots.add("Floor 9")
        spots.add("Floor 10")
        spots.add("Floor 11")
        spots.add("Floor 12")
        spots.add("Floor 13")
        spots.add("Floor 14")
        return spots
    }
    fun getSectionWiseTableList(): List<String> {
        val spots = ArrayList<String>()
        spots.add("Table 1")
        spots.add("Table 2")
        spots.add("Table 3")
        spots.add("Table 4")
        spots.add("Table 5")
        spots.add("Table 6")
        spots.add("Table 7")
        spots.add("Table 8")
        spots.add("Table 9")
        spots.add("Table 10")
        spots.add("Table 11")
        spots.add("Table 12")
        spots.add("Table 13")
        spots.add("Table 14")
        return spots
    }

}
