package  com.mydia.restaurantsmartqr.base


import android.app.Service
import android.content.Intent
import android.os.IBinder


abstract class BaseService  : Service() {



    override fun onBind(intent: Intent?): IBinder? {
     return null
    }


    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }


    override fun onDestroy() {
        super.onDestroy()

    }




}
