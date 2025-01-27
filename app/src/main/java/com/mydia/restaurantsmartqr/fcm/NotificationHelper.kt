package com.mydia.restaurantsmartqr.fcm

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

import com.mydia.restaurantsmartqr.R


@SuppressLint("ObsoleteSdkInt")
class NotificationHelper(base: Context?) : ContextWrapper(base) {
    private var manager: NotificationManager? = null

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param ctx The application context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun createChannels() {
        //val soundUri =Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.notification_ring)
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        var notifyChannels = NotificationChannel(
            CHANEL_ID, CHANEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        val attr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        notifyChannels.enableLights(true)
        notifyChannels.enableVibration(true)
        notifyChannels.setSound(soundUri, attr)
        notifyChannels.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        manger!!.createNotificationChannel(notifyChannels)
    }

    /**
     * Get the notification manager.
     *
     *
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    val manger: NotificationManager?
        get() {
            if (manager == null) manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            return manager
        }

    /**
     * Get a notification of type 1
     *
     *
     * Provide the builder rather than the notification it's self as useful for making notification
     * changes.
     *
     * @param title the title of the notification
     * @param body  the body text for the notification
     * @return the builder as it keeps a reference to the notification (since API 24)
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun getNotification(
        title: String?, content: String?, contentIntent: PendingIntent?,
        soundUri: Uri?
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, CHANEL_ID)
            .setContentText(content)
            .setContentTitle(title)
            .setAutoCancel(true)
            .setDefaults(Notification.DEFAULT_ALL)
            .setSound(soundUri)
            .setWhen(System.currentTimeMillis())
            .setVibrate(longArrayOf(500, 500))
            .setContentIntent(contentIntent)
            .setSmallIcon(smallIcon)
    }

    /**
     * Get the small icon for this app
     *
     * @return The small icon resource id
     */
    private val smallIcon: Int
        get() = R.drawable.ic_mydia

    private val largeIcon: Bitmap
        get() = BitmapFactory.decodeResource(resources,R.drawable.ic_mydia)

    companion object {
        private const val CHANEL_ID = "com.foodcus.order.management"
        private const val CHANEL_NAME = "Foodcus Order Management"
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createChannels()
    }
}