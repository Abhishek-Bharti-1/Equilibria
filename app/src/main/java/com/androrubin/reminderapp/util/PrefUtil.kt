package com.androrubin.reminderapp.util

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.preference.PreferenceManager
import com.androrubin.reminderapp.PomodoroFragment
import java.util.*

class PrefUtil {
    companion object{

        //private const val TIMER_LENGTH_ID="com.example.timer.timer_length"
        fun getTimerLength(context: Context): Int{
            //placeholder
            //val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return 30 //preferences.getInt(TIMER_LENGTH_ID,10)
        }




        private const val PREVIOUS_TIMER_LENGTH_SECONDS_ID = "com.androrubin.reminderapp.previous_timer_length"

        fun getPreviousTimerLengthSeconds(context: Context):Long{

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID,0)
        }


        fun setPreviousTimerLengthSeconds(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(PREVIOUS_TIMER_LENGTH_SECONDS_ID,seconds)
            editor.apply()
        }




        private const val TIMER_STATE_ID = "com.androrubin.reminderapp.timer_state"

        fun getTimerState(context: Context): PomodoroFragment.TimerState{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val ordinal = preferences.getInt(TIMER_STATE_ID,0)
            return PomodoroFragment.TimerState.values()[ordinal]
        }


        fun setTimerState(state: PomodoroFragment.TimerState, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            val ordinal = state.ordinal
            editor.putInt(TIMER_STATE_ID, ordinal)
            editor.apply()

        }




        private const val SECONDS_REMAINING_ID = "com.androrubin.reminderapp.seconds_remaining"

        fun getSecondsRemaining(context: Context):Long{

            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getLong(SECONDS_REMAINING_ID,0)
        }

        fun setSecondsRemaining(seconds: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(SECONDS_REMAINING_ID,seconds)
            editor.apply()
        }




        private  const val ALARM_SET_TIME_ID = "com.androrubin.reminderapp.background_time"

        fun getAlarmSetTime(context: Context):Long{
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return  preferences.getLong(ALARM_SET_TIME_ID,0)
        }


        fun setAlarmSetTime(time: Long, context: Context){
            val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
            editor.putLong(ALARM_SET_TIME_ID,time)
            editor.apply()
        }



    }
}