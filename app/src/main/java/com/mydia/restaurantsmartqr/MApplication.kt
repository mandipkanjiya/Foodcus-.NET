package com.mydia.restaurantsmartqr




import android.app.Application
import com.google.firebase.FirebaseApp

import com.mydia.restaurantsmartqr.prefrences.PreferencesServices


import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MApplication : Application() {
    @Inject
    lateinit var prefs: PreferencesServices
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)


    }


    companion object {
        private lateinit var instance: MApplication

        fun getInstance(): MApplication = instance

    }
}