package com.mydia.restaurantsmartqr.fcm

interface FirebaseEventListener {

    fun onRebootCommand()
    fun onRestartCommand()
    fun onContentUpdateCommand()
    fun onQueueUpdateCommand()
    fun onClearCatchCommand()
    fun onPOSOrderCountCommand()
}