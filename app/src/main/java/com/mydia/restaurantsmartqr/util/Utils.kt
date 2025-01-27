package com.mydia.restaurantsmartqr.util

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Calendar
import java.util.Date


object Utils {

    const val dateTimeFormat = "yyyy-MM-dd HH:mm:ss"
    const val dateTimeFormat2 = "yyyy-MM-dd HH:mm"
    const val TimeFormat= "HH:mm:ss"
    const val hourTimeFormat = "HH:mm"
    fun getDateTimeInMillis(dateTime:String): Long {
        val date = getParseDateTimeWithDate(dateTime)
        date?.let {

            return it.time
        } ?: run {
            return 0
        }
    }

    fun getDateTimeInMillis2(dateTime:String): Long {
        val date = getParseDateTimeWithDate2(dateTime)
        date?.let {

            return it.time
        } ?: run {
            return 0
        }
    }
    fun getTimeInMillis(dateTime:String): Long {

        val calendar = Calendar.getInstance()

        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = dateTime.split(":")[0].toInt()
        calendar[Calendar.MINUTE] = dateTime.split(":")[1].toInt()
        calendar[Calendar.SECOND] = dateTime.split(":")[2].toInt()

        println("Current Date = " + calendar.time )
        return  calendar.timeInMillis

    }
    fun getTimeInMillis2(dateTime:String): Long {

        val calendar = Calendar.getInstance()

        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = dateTime.split(":")[0].toInt()
        calendar[Calendar.MINUTE] = dateTime.split(":")[1].toInt()
        calendar[Calendar.SECOND] = 0

        println("Current Date = " + calendar.time )
        return  calendar.timeInMillis

    }

    fun getMinuteTimeInMillis(dateTime:String): Long {
        val now = Calendar.getInstance()

        val tmp = now.clone() as Calendar
        tmp.add(Calendar.MINUTE, dateTime.split(":")[0].toInt())
        tmp.add(Calendar.SECOND, dateTime.split(":")[1].toInt())
        val newTime = tmp
       /* val calendar = Calendar.getInstance()

        calendar.timeInMillis = System.currentTimeMillis()

        calendar[Calendar.MINUTE] = dateTime.split(":")[0].toInt()
        calendar[Calendar.SECOND] =dateTime.split(":")[1].toInt()
*/
        println("Update time = " + newTime.time )
        return  newTime.timeInMillis

    }

    fun convertminuteorsecondtomilli(dateTime: String): Long {
        val minute = dateTime.split(":")[0].toInt()
        val sec = dateTime.split(":")[1].toInt()
        val millisecond = (minute * 60000).toLong() + (sec * 1000).toLong()
        println("Update sec =$millisecond")

        return millisecond
    }
    fun getCorrespondingDate( time:String ) : String {

        val hour = time.split(":")[0]
        val minute = time.split(":")[1]

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,hour.toInt())
        calendar.set(Calendar.MINUTE,minute.toInt())
        val currentMillis = calendar.timeInMillis
        if (currentMillis < System.currentTimeMillis()){
            calendar.add(Calendar.DATE,1)
        }
        return SimpleDateFormat("yyyy-MM-dd").format(Date(calendar.timeInMillis))
    }
    fun getCurrentTimeInMillis(): Int {

        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm:ss")
        val current = formatter.format(time)
        val millis = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val localTime = LocalTime.parse(current)
            localTime.toSecondOfDay() * 1000

        } else {
            0
        }
        return  millis


    }
    fun getParseDateTimeWithDate(dateTime:String,inputFormat: String = dateTimeFormat) : Date? {
        var time = ""
        var date : Date? = null;

        val input = SimpleDateFormat(inputFormat)

        try {
            date = input.parse(dateTime)
        }catch (e :Exception){
            e.printStackTrace()
        }
        return date
    }

    fun getParseDateTimeWithDate2(dateTime:String,inputFormat: String = dateTimeFormat2) : Date? {
        var time = ""
        var date : Date? = null;

        val input = SimpleDateFormat(inputFormat)

        try {
            date = input.parse(dateTime)
        }catch (e :Exception){
            e.printStackTrace()
        }
        return date
    }



     fun FadeOut(deltaTime:Float,mediaPlayer: MediaPlayer)
    {
        var volume = 1f
        val speed = 0.05f

        Log.e("music", "fade ,$volume")
        mediaPlayer.setVolume(volume, volume)
        volume -= speed* deltaTime

    }
/*    public fun FadeIn(deltaTime:Float,mediaPlayer: MediaPlayer)
    {
        mediaPlayer.setVolume(volume, volume);
        volume += speed* deltaTime

    }*/
fun trimCathe(context : Context){
    try {
       // val dir = context.cacheDir
        val dir = context.filesDir
        if (dir != null && dir.isDirectory) {
            deleteDir(dir)
        }
    } catch (e: java.lang.Exception) {
        // TODO: handle exception
    }
}
    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }

        // The directory is now empty so delete it
        return dir!!.delete()
    }
    fun restartApp(context: Context) {

        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val componentName = intent?.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        context.startActivity(mainIntent)

    }
    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        return sdf.format(Date())
    }
    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("HH:mm:ss")
        return sdf.format(Date())
    }

    fun getTimeWithDate(endTimeMilli: Long): String? {


        val date: Date = Date(endTimeMilli)

        val formatter = SimpleDateFormat("HH:mm:ss")

        val formatted = formatter.format(date)

        return formatted
    }
     fun getTimeString(millis: Long): String? {
        val buf = StringBuffer()
        val hours = (millis / (1000 * 60 * 60)).toInt()
        val minutes = (millis % (1000 * 60 * 60) / (1000 * 60)).toInt()
        val seconds = (millis % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        buf
            .append(String.format("%02d", hours))
            .append(":")
            .append(String.format("%02d", minutes))
            .append(":")
            .append(String.format("%02d", seconds))
        return buf.toString()
    }

    fun isValidEmail(target: CharSequence?): Boolean {

        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()

    }
}