package com.globalspace.miljonsales.firebaseservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.globalspace.miljonsales.R
import com.globalspace.miljonsales.activity.Dashboard
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private var sPref: SharedPreferences? = null
    private var edit: SharedPreferences.Editor? = null

    override fun onMessageReceived(remotemessage: RemoteMessage) {
        if(remotemessage.data.size > 0){
          //  flag = remotemessage.data.get("sender_id")
            Log.d("infolog","onMessageReceived")
            generateNotification("title","message")
        }

        Log.d("infolog","flag = ${flag}")
        if(remotemessage.notification!= null){
            generateNotification(remotemessage.notification!!.title!!,remotemessage.notification!!.body!!)
        }
        super.onMessageReceived(remotemessage)
    }

    fun getRemoteView(title : String,message : String) : RemoteViews {
        Log.d("infolog","remoteview")
        val remoteview = RemoteViews(channelName, R.layout.notification)
        remoteview.setTextViewText(R.id.title,title)
        remoteview.setTextViewText(R.id.description,message)
        //remoteview.setImageViewResource(R.id.applogo,R.drawable.ic_launcher_background)
        return remoteview
    }
    fun generateNotification(title : String,message : String){
        val intent = Intent(this, Dashboard::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_ONE_SHOT)
        var builder : NotificationCompat.Builder = NotificationCompat.Builder(applicationContext,
            channelID).setSmallIcon(R.drawable.ic_launcher_background)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title,message))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            val notificationChannel = NotificationChannel(channelID, channelName,
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        Log.d("infolog","generate notification")
        notificationManager.notify(0,builder.build())
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        sPref = getSharedPreferences(resources.getString(R.string.app_name), MODE_PRIVATE)
        edit = sPref!!.edit()
        edit!!.putString("token",token)
        edit!!.commit()
        Log.d("infolog","onNewToken called $token")
    }

    companion object {
        val channelID = "notification_channel"
        val channelName = "com.globalspace.miljonsales"
        var flag : String ?= null
    }
}