package com.mydia.restaurantsmartqr.activity

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.Observable
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import com.mydia.restaurantsmartqr.base.BaseActivity
import com.mydia.restaurantsmartqr.databinding.ActivitySplashBinding
import com.mydia.restaurantsmartqr.fcm.FirebaseDataManager
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.viewModel.VMSplash
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashActivity: BaseActivity<ActivitySplashBinding, VMSplash>()  {

    private val TAG = SplashActivity::class.java.simpleName

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun observeViewModel() {

        viewModel.fcm.addOnPropertyChangedCallback(object :
        Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(viewModel.fcm.get()!!.isNotEmpty()){
                    Log.e(TAG, "fcmtoken:- ${viewModel.fcm.get()}")

                    lifecycleScope.launch {
                        prefs.putString(PrefKey.FCM_TOKEN, viewModel.fcm.get())
                        if(prefs.getBoolean(PrefKey.IS_LOGIN)){
                            FirebaseDataManager.initializeWihTVID(prefs.getString(PrefKey.BRANCH_ID),prefs)
                            FirebaseDataManager.firebaseDataReference
                            Handler(Looper.getMainLooper()).postDelayed({
                                lifecycleScope.launch {
                                    openTableOrdertActivity()
                                }
                            },5000)

                        }else{
                            Handler(Looper.getMainLooper()).postDelayed({
                                lifecycleScope.launch {
                                    openLoginActivity()
                                }
                            },5000)
                         //   openLoginActivity()
                        }

                    }
                }
            }

        })


    }

    override fun onActivityCreated() {
        binding.vm = viewModel

        returnMeFCMtoken()

     /*   lifecycleScope.launch {
            delay(100)
            prefs.putString(PrefKey.FCM_TOKEN, fcm)
            if(prefs.getBoolean(PrefKey.IS_LOGIN)){
                FirebaseDataManager.initializeWihTVID(prefs.getString(PrefKey.BRANCH_ID),prefs)
                FirebaseDataManager.firebaseDataReference
                openNextActivity()
            }else{
                openLoginActivity()
            }

        }*/


     /*   Handler(Looper.getMainLooper()).postDelayed({


        }, 2000)*/

    }
    fun returnMeFCMtoken(): String? {
        val token = arrayOf("")
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isComplete) {
                token[0] = task.result
                Log.e("AppConstants", "onComplete: new Token got: " + token[0])
                viewModel.fcm.set(token[0])
            }
        }
        return token[0]
    }
    override val viewModel: VMSplash by viewModels()
    private fun openNextActivity(){
        startActivity(Intent(this,LiveOrderActivity::class.java))
        finish()
    }
    private fun openTableOrdertActivity(){
        startActivity(Intent(this,TabelListActivity::class.java))
        finish()
    }
    private fun openLoginActivity(){
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}