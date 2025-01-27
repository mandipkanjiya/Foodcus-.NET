package com.mydia.restaurantsmartqr.fcm



import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.mydia.restaurantsmartqr.prefrences.PrefKey
import com.mydia.restaurantsmartqr.prefrences.PreferencesServices
import com.mydia.restaurantsmartqr.util.NewCrashHandlerContentProvider
import kotlinx.coroutines.*


class FirebaseDataReferenceNew(tvId: String, val prefs: PreferencesServices)  {

    val TAG = FirebaseDataReferenceNew::class.java.simpleName

    private val fDBUpdateCount = ObservableInt(0)
    private val fDBRestartCount = ObservableInt(0)
    private val fDBRebootCount = ObservableInt(0)
    private val fDBQueueCount = ObservableInt(0)
    private val fDBClearCacheCount = ObservableInt(0)
    private val fDBPosOrderCount = ObservableInt(0)
    private val isInitializedCounts = ObservableBoolean(false)


    val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val firebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference? = null

    var firebaseEventListener: FirebaseEventListener? = null

    init {
        Log.e(TAG,"init called")

        firebaseDatabase.reference.keepSynced(true)
        firebaseDatabase.reference.child(tvId).child("tvId").setValue(tvId)
        firebaseDatabase.reference.child(tvId).child("version").setValue(BuildConfig.VERSION_NAME)
      //  firebaseDatabase.reference.child(tvId).child("update_count").setValue(0)
        databaseReference = firebaseDatabase.reference.child(tvId)

      FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
      Firebase.crashlytics.setUserId(tvId)
      NewCrashHandlerContentProvider.initializeAfterFirebaseContentProvider()



     }

    fun getFirebaseTVRef() : DatabaseReference? = databaseReference

    private  val postEventListener = object : ValueEventListener{
        override  fun onDataChange(dataSnapshotObject: DataSnapshot) {
            Log.e("firebase --","update"+dataSnapshotObject.child("update_count").getValue(Int::class.java))
            updatePreference(dataSnapshotObject)

        }

        override  fun onCancelled(error: DatabaseError) {
            Log.e("firebase error",error.message)
        }

    }

    fun setValue(key:String,value:String){
        databaseReference?.child(key)?.setValue(value)
    }

    val firebaseDataEventListener = firebaseDatabase.reference.child(tvId).addValueEventListener(postEventListener)

    fun updatePreference(dataSnapshotObject: DataSnapshot){



        scope.launch {
            var updateCount = 0
            var queueCount = 0
            var restartCount = 0
            var rebootCount = 0
            var cacheCount = 0
            var posOrderCount = 0

            if (dataSnapshotObject.hasChild("update_count")){

                updateCount = dataSnapshotObject.child("update_count").getValue(Int::class.java)!!

            }
            if (dataSnapshotObject.hasChild("queue_count")){
                queueCount = dataSnapshotObject.child("queue_count").getValue(Int::class.java)!!

            }
            if (dataSnapshotObject.hasChild("is_restart")){
                restartCount = dataSnapshotObject.child("is_restart").getValue(Int::class.java)!!

            }
            if (dataSnapshotObject.hasChild("is_reboot")){
                rebootCount = dataSnapshotObject.child("is_reboot").getValue(Int::class.java)!!

            }
            if (dataSnapshotObject.hasChild("clear_catch")){
                cacheCount = dataSnapshotObject.child("clear_catch").getValue(Int::class.java)!!

            }
            if (dataSnapshotObject.hasChild("pos_order_count")){
                posOrderCount = dataSnapshotObject.child("pos_order_count").getValue(Int::class.java)!!

            }
            Log.e("FirebaseEvent","updateCount $updateCount queueCount $queueCount " +
                    "restartCount $restartCount rebootCount $rebootCount cacheCount $cacheCount posOrder $posOrderCount")

            loadPreferenceCounts(updateCount,queueCount,restartCount,rebootCount,cacheCount,posOrderCount)



            var eventDetect = 0
            Log.e("FirebaseEvent","pref updateCount ${fDBUpdateCount.get()} queueCount ${fDBQueueCount.get()} " +
                    "restartCount ${fDBRestartCount.get()} rebootCount ${fDBRebootCount.get()} cacheCount ${fDBClearCacheCount.get()} posOrderCount ${fDBPosOrderCount.get()}")

            if (fDBUpdateCount.get() != updateCount){
                eventDetect=1
                fDBUpdateCount.set(updateCount)
                prefs.putInt(PrefKey.UPDATE_COUNT, updateCount)
            }
            if (fDBQueueCount.get() != queueCount){
                eventDetect=2
                fDBQueueCount.set(queueCount)
                prefs.putInt(PrefKey.QUEUE_COUNT, queueCount)
            }
            if (fDBRestartCount.get() != restartCount){
                eventDetect=3
                fDBRestartCount.set(restartCount)

                prefs.putInt(PrefKey.RESTART_COUNT, restartCount)
            }
            if (fDBRebootCount.get() != rebootCount){
                eventDetect=4
                fDBRebootCount.set(rebootCount)

                prefs.putInt(PrefKey.REBOOT_COUNT, rebootCount)
            }
            if (fDBClearCacheCount.get() != cacheCount){
                eventDetect=5
                fDBClearCacheCount.set(cacheCount)
                prefs.putInt(PrefKey.CLEAR_CATCH_COUNT, cacheCount)
            }
            if (fDBPosOrderCount.get() != posOrderCount){
                eventDetect=6
                fDBPosOrderCount.set(posOrderCount)
                prefs.putInt(PrefKey.POS_ORDER_COUNT, posOrderCount)
            }

            Log.e("firebase event",eventDetect.toString())
            if (eventDetect != 0){
                when(eventDetect){
                    1 -> firebaseEventListener?.onContentUpdateCommand()
                    2 -> firebaseEventListener?.onQueueUpdateCommand()
                    3 -> firebaseEventListener?.onRestartCommand()
                    4 -> firebaseEventListener?.onRebootCommand()
                    5 -> firebaseEventListener?.onClearCatchCommand()
                    6 -> firebaseEventListener?.onPOSOrderCountCommand()
                }
            }
        }
    }
    private suspend fun loadPreferenceCounts(
        updateCount: Int,
        queueCount: Int,
        restartCount: Int,
        rebootCount: Int,
        cacheCount: Int,
        posOrderCount: Int
    ) =
        withContext(scope.coroutineContext) {
            if (isInitializedCounts.get()) {
                return@withContext
            }

            setPreferenceCounts(updateCount,queueCount,restartCount,rebootCount,cacheCount,posOrderCount)

            fDBUpdateCount.set(updateCount)
            fDBQueueCount.set(queueCount)
            fDBRestartCount.set(restartCount)
            fDBRebootCount.set(rebootCount)
            fDBClearCacheCount.set(cacheCount)
            fDBPosOrderCount.set(posOrderCount)
        }

    private suspend fun setPreferenceCounts(
        updateCount: Int,
        queueCount: Int,
        restartCount: Int,
        rebootCount: Int,
        cacheCount: Int,
        posOrderCount: Int
    ) {
        scope.launch {
            prefs.putInt(PrefKey.UPDATE_COUNT, updateCount)
            prefs.putInt(PrefKey.QUEUE_COUNT, queueCount)
            prefs.putInt(PrefKey.RESTART_COUNT, restartCount)
            prefs.putInt(PrefKey.REBOOT_COUNT, rebootCount)
            prefs.putInt(PrefKey.CLEAR_CATCH_COUNT, cacheCount)
            prefs.putInt(PrefKey.POS_ORDER_COUNT, posOrderCount)

            isInitializedCounts.set(true)
        }

    }

}