package com.androrubin.reminderapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.androrubin.reminderapp.util.NotificationUtil
import com.androrubin.reminderapp.util.PrefUtil

class TimerNotificationActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when(intent.action){
            AppConstants.ACTION_STOP->{
                PomodoroFragment.removeAlarm(context)
                PrefUtil.setTimerState(PomodoroFragment.TimerState.Stopped,context)
                NotificationUtil.hideTimerNotification(context)
            }
            AppConstants.ACTION_PAUSE-> {
                var secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val alarmSetTime = PrefUtil.getAlarmSetTime(context)
                val nowSeconds = PomodoroFragment.nowSeconds

                secondsRemaining -= nowSeconds - alarmSetTime
                PrefUtil.setSecondsRemaining(secondsRemaining,context)

                PomodoroFragment.removeAlarm(context)
                PrefUtil.setTimerState(PomodoroFragment.TimerState.Paused,context)
                NotificationUtil.showTimerPaused(context)
            }
            AppConstants.ACTION_RESUME->{
                val secondsRemaining = PrefUtil.getSecondsRemaining(context)
                val wakeUpTime = PomodoroFragment.setAlarm(context,PomodoroFragment.nowSeconds,secondsRemaining)
                PrefUtil.setTimerState(PomodoroFragment.TimerState.Running,context)
                NotificationUtil.showTimerRunning(context,wakeUpTime)
            }
            AppConstants.ACTION_START->{
                val minutesRemaining = PrefUtil.getTimerLength(context)
                val secondsRemaining = minutesRemaining * 60L
                val wakeUpTime = PomodoroFragment.setAlarm(context,PomodoroFragment.nowSeconds,secondsRemaining)
                PrefUtil.setTimerState(PomodoroFragment.TimerState.Running,context)
                PrefUtil.setSecondsRemaining(secondsRemaining,context)
                NotificationUtil.showTimerRunning(context,wakeUpTime)
            }
        }
    }
}