
package com.mydia.restaurantsmartqr.fcm


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mydia.restaurantsmartqr.R
import com.mydia.restaurantsmartqr.activity.LiveOrderActivity
import org.json.JSONObject
import java.io.BufferedInputStream
import java.net.HttpURLConnection
import java.net.URL


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseMessaging"
    var message: String? = null
    var click_action: String? = null
    var intent: Intent? = null
    var context: Context? = null
    private var pendingIntent: PendingIntent? = null
    override fun onNewToken(firebaseId: String) {


        Log.e("-->JSON DATA FCM TOKEN",firebaseId)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        context = this
        //    Log.d(TAG, "From: " + remoteMessage);
        //     Log.d(TAG, "From: " + remoteMessage.getNotification().getBody());
        //     Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        if (remoteMessage.notification != null) {
            Log.d(TAG, "Notification: " + remoteMessage.notification!!.body);
        }
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Data: " + remoteMessage.getData());
        }
        Log.e(TAG, "Firebase message received " + remoteMessage.data)
        checkBackgroundNotification(remoteMessage)
    }

    private fun checkBackgroundNotification(remoteMessage: RemoteMessage) {
        try {
            //     String pageId = remoteMessage.getData().get("Tag").toString();
            val alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context!!.packageName +"/" + R.raw.notification)
          //  val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            // Implement this feature without material design
            val notificationBuilder = NotificationCompat.Builder(this)
            notificationBuilder.setSmallIcon( R.mipmap.ic_launcher)
            notificationBuilder.setContentTitle(remoteMessage.notification!!.title)
           // playSound()
            notificationBuilder.setWhen(System.currentTimeMillis())
            notificationBuilder.setContentText(remoteMessage.notification!!.body)
            //notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("message")));
            //notificationBuilder.setDefaults(Notification.DEFAULT_SOUND);
            notificationBuilder.setAutoCancel(true)
            Log.d(TAG, "From: " + remoteMessage.from)
            if (remoteMessage.data.size > 0) {
                Log.e(TAG, "Notification  data 1" + remoteMessage.data)
            }
            Log.e(TAG, "Notification  data" + remoteMessage.data)
            Log.e(TAG, "Notification data--- " + remoteMessage.notification)
            Log.e(TAG, "Notification body " + remoteMessage.notification!!.body)
            Log.e(TAG, "Notification title " + remoteMessage.notification!!.title)

            var maindata_object = JSONObject(remoteMessage.data as Map<String, String>)
            var pageid = maindata_object.getString("pageid")
            var notificationId = maindata_object.getString("id")
            Log.e(TAG, "Notification id " + notificationId)
            notiTextClick(pageid,notificationId)
            val iMsg = Intent(this, LiveOrderActivity::class.java)

            iMsg.putExtra("orderId",notificationId)
            iMsg.putExtra("pageid",notificationId)
            iMsg.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
            pendingIntent = PendingIntent.getActivity(this, 0, iMsg, PendingIntent.FLAG_IMMUTABLE)
            notificationBuilder.setContentIntent(pendingIntent)
            val mNotificationId = System.currentTimeMillis().toInt()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val CHANNEL_ID = getString(R.string.channelid)
                val importance = NotificationManager.IMPORTANCE_HIGH
                val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()

                val notificationChannel =
                    NotificationChannel(CHANNEL_ID, getString(R.string.app_name), importance)
                notificationBuilder.setChannelId(CHANNEL_ID)
                notificationChannel.setSound(alarmSound,attributes)
                if (notificationManager != null) {
                    val channelList: List<NotificationChannel> =
                        notificationManager.getNotificationChannels()
                    var i = 0
                    while (channelList != null && i < channelList.size) {
                        notificationManager.deleteNotificationChannel(channelList[i].id)
                        i++
                    }
                }
                notificationManager.createNotificationChannel(notificationChannel)
            }
            notificationManager.notify(mNotificationId, notificationBuilder.build())


        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
    @Throws(java.lang.Exception::class)
    private fun getBitmapFromURL(src: String): Bitmap? {
        val connection = URL(src).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.useCaches = false // Android BUG
        connection.connect()
        return BitmapFactory.decodeStream(BufferedInputStream(connection.inputStream))
    }
    fun playSound() {
        val alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName +"/" + R.raw.notification)
        /*  var resId = getResources().getIdentifier(
              R.raw.soundring.toString(),
              "raw", packageName)*/

        val mediaPlayer = MediaPlayer.create(context, alarmSound)
        mediaPlayer.start()
    }

    /*
        override fun onMessageReceived(remoteMessage: RemoteMessage) {


            Log.e(TAG, "Firebse message received " + remoteMessage.data)
            var data = remoteMessage.data
          //  var notificationId = Random().nextInt(60000)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                showArrivedNotificationAPI26(data, notificationId)

            } else {

                showArrivedNotification(data, notificationId)
            }
            if (remoteMessage.data.isNotEmpty()) {
                var data = remoteMessage.data
                var maindata_object = JSONObject(data as Map<String, String>)
                var notificationId = maindata_object.getString("order_id").toInt()
                Log.e("-->JSON DATA", maindata_object.toString())
                Log.e("Noti DATA", data.toString())




            }
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        private fun showArrivedNotificationAPI26(body: String?, notificationId: Int) {
            try {
               // val iMsg = Intent(this, LiveOrderActivity::class.java)
                val contentIntent = PendingIntent.getActivity(
                    baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                            or PendingIntent.FLAG_ONE_SHOT
                )
                //val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.notification)
                val notificationHelper = NotificationHelper(baseContext)
                val builder = notificationHelper.getNotification(
                    getString(R.string.app_name),
                    body,
                    contentIntent,
                    soundUri = soundUri
                    soundUri = soundUri
                )
                notificationHelper.manger!!.notify(notificationId, builder.build())
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        private fun showArrivedNotification(body: String?, notificationId: Int) {
            val iMsg = Intent(this, LiveOrderActivity::class.java)
            val contentIntent = PendingIntent.getActivity(
                baseContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
                        or PendingIntent.FLAG_ONE_SHOT
            )
            val builder = NotificationCompat.Builder(this)
           // val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            val soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + applicationContext.packageName + "/" + R.raw.notification)
            builder.setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_mydia)
                .setAutoCancel(true)
                .setVibrate(longArrayOf(500, 500))
                .setSound(soundUri)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(body)
                .setContentIntent(contentIntent)
            val manager = baseContext.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            manager.notify(notificationId, builder.build())
        }*/

    fun notiTextClick(pageid: String?,orderId: String?) {

        val intent = Intent("NOTIFICATION_DATA")
        intent.putExtra("orderId", orderId)
        intent.putExtra("pageid", pageid)

        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

    }
}


