package com.mydia.restaurantsmartqr.fcm


import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import kotlinx.coroutines.CoroutineScope
import java.lang.ref.WeakReference

object FirebaseDataManager {

    private val TAG = FirebaseDataManager::class.java.simpleName

    private lateinit var tvId : WeakReference<String>
    private lateinit var pref : WeakReference<PreferencesServices>
    private lateinit var scope : WeakReference<CoroutineScope>

    init {
        Log.e(TAG,"init called")
    }

    fun initializeWihTVID(tvId: String,preferencesServices: PreferencesServices){
        this.tvId = WeakReference<String>(tvId)
        this.pref = WeakReference<PreferencesServices>(preferencesServices)

    }
   // this.scope = WeakReference<CoroutineScope>(scope)

    val firebaseDataReference by lazy {
        FirebaseDataReferenceNew(
        tvId.get() ?: error("Tv id not found"),
        pref.get() ?: error("preferencesServices not initialized")
        )
    }

    fun setValue(key:String,value:String){
        firebaseDataReference.setValue(key,value)
    }

    fun getFirebaseTVRef() :DatabaseReference?= firebaseDataReference.getFirebaseTVRef()
}