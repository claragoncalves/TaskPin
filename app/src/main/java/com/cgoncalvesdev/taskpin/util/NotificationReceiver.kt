package com.cgoncalvesdev.taskpin.util

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationCompat.VISIBILITY_PRIVATE
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast
import com.cgoncalvesdev.taskpin.R
import com.cgoncalvesdev.taskpin.controller.TaskController
import com.cgoncalvesdev.taskpin.model.pojo.Task
import com.cgoncalvesdev.taskpin.view.activities.MainActivity

class NotificationReceiver: BroadcastReceiver() {

    companion object{
        const val CHANNEL_ID = "taskPinChannelId"
        const val NOTIFICATION_ID = "notificationId"
        const val ACTION_TAP = "actionTap"
        const val ACTION_DONE = "actionDone"
        const val SCHEDULED_NOTIFICATION = "scheduledNotification"
    }

    override fun onReceive(p0: Context?, p1: Intent?) {
        val notificationManager = p0?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val bundle = p1?.extras
        if (bundle!=null){
            val notificationId = bundle.getInt(NOTIFICATION_ID)
            val action = p1.action
            if (action!=null) {
                if (p1.action.equals(ACTION_DONE + notificationId)) {
                    TaskController.saveIsTaskDone(p0, notificationId.toLong(), true)
                    notificationManager.cancel(notificationId)
                }
            } else {
                val notification: Notification = p1.getParcelableExtra(SCHEDULED_NOTIFICATION)
                notificationManager.notify(notificationId,notification)
            }
        }
    }

    fun setNotification(context: Context, task: Task){
        createNotificationChannel(context)
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(task.id.toInt(), buildNotification(context,task))
    }

    fun buildNotification(context: Context, task: Task) : Notification{
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_none_black_24dp)
                .setContentTitle(task.title)
                .setContentText(task.content.description)
                .setContentIntent(buildTapAction(context,task.id.toInt()))
                .setVisibility(VISIBILITY_PRIVATE)
                .setOngoing(true)
                .addAction(R.drawable.ic_check_black_24dp, context.getString(R.string.notification_done_action_text), buildDoneAction(context,task.id.toInt()))
                .setStyle(NotificationCompat.BigTextStyle().bigText(task.content.description))
                .setColor(task.priority.color)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    private fun buildTapAction(context: Context, taskId: Int) : PendingIntent{
        val tapIntent = Intent(context, MainActivity::class.java)
        val tapBundle = Bundle()
        tapBundle.putInt(NOTIFICATION_ID, taskId)
        tapIntent.putExtras(tapBundle)
        tapIntent.setAction(ACTION_TAP + taskId)
        return PendingIntent.getActivity(context, 0, tapIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildDoneAction(context: Context, taskId: Int) : PendingIntent{
        val doneIntent = Intent(context, NotificationReceiver::class.java)
        val doneBundle = Bundle()
        doneBundle.putInt(NOTIFICATION_ID, taskId)
        doneIntent.putExtras(doneBundle)
        doneIntent.setAction(ACTION_DONE + taskId)
        return PendingIntent.getBroadcast(context, 0, doneIntent, PendingIntent.FLAG_ONE_SHOT)
    }

    fun cancelNotification(context: Context, taskId: Int){
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.cancel(taskId)
    }

    fun scheduleNotification(context: Context, task: Task){
        val notificationIntent = Intent(context, NotificationReceiver::class.java)
        val bundle = Bundle()
        bundle.putInt(NOTIFICATION_ID, task.id.toInt())
        bundle.putParcelable(SCHEDULED_NOTIFICATION, buildNotification(context, task))
        notificationIntent.putExtras(bundle)
        val scheduledPendingIntent = PendingIntent.getBroadcast(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val futureInMillis = task.scheduledAt.time - System.currentTimeMillis()
        Toast.makeText(context, futureInMillis.toString(), Toast.LENGTH_SHORT).show()
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + futureInMillis, scheduledPendingIntent)
    }


    private fun createNotificationChannel(context: Context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = context.getString(R.string.channel_name)
            val description = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance)
            channel.description = description
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

}