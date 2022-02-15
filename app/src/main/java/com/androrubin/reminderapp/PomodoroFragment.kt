package com.androrubin.reminderapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.*
import androidx.fragment.app.Fragment
import androidx.navigation.ui.AppBarConfiguration
import com.androrubin.reminderapp.databinding.FragmentPomodoroBinding
import com.androrubin.reminderapp.util.NotificationUtil
import com.androrubin.reminderapp.util.PrefUtil
import kotlinx.android.synthetic.main.fragment_pomodoro.*
import kotlinx.android.synthetic.main.pomo_content.*
import java.util.*


class PomodoroFragment : Fragment() {

    companion object {



        fun setAlarm(context: Context, nowSeconds: Long, secondsRemaining: Long): Long{
            val wakeUpTime = (nowSeconds + secondsRemaining) * 1000
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
            PrefUtil.setAlarmSetTime(nowSeconds,context)
            return wakeUpTime
        }

        fun removeAlarm(context: Context){
            val intent = Intent(context,TimerExpiredReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(context,0,intent,0)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            PrefUtil.setAlarmSetTime(0,context)
        }


        val nowSeconds: Long
        get() = Calendar.getInstance().timeInMillis / 1000
    }




    enum class TimerState{
        Stopped, Paused, Running
    }




    private lateinit var timer: CountDownTimer
    private var timerLengthSeconds = 0L
    private var timerState = TimerState.Stopped
    private var secondsRemaining = 0L



    lateinit var binding: FragmentPomodoroBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

    ): View? {

        val view = inflater.inflate(R.layout.fragment_pomodoro, container, false)

        return view

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPomodoroBinding.bind(view)
        binding.fabStop
        binding.fabPause
        binding.fabPlay

        fab_play.setOnClickListener {
            startTimer()
            timerState = TimerState.Running
            updateButtons()
        }

        fab_pause.setOnClickListener {
            timer.cancel()
            timerState = TimerState.Paused
            updateButtons()
        }

        fab_stop.setOnClickListener {
            if(timerState==TimerState.Running){
                timer.cancel()
            }
            onTimerFinished()
            timerState=TimerState.Stopped
        }




    }
    override fun onResume(){

        super.onResume()

        initTimer()
        removeAlarm(requireContext())
        NotificationUtil.hideTimerNotification(requireContext())

    }

    override fun onPause() {
        super.onPause()

        if(timerState == TimerState.Running){
            timer.cancel()
            val wakeUpTime = setAlarm(requireContext(), nowSeconds, secondsRemaining)
            NotificationUtil.showTimerRunning(requireContext(),wakeUpTime)
        }
        else if(timerState == TimerState.Paused){
            NotificationUtil.showTimerPaused(requireContext())
        }

        PrefUtil.setPreviousTimerLengthSeconds(timerLengthSeconds,requireContext())
        PrefUtil.setSecondsRemaining(secondsRemaining,requireContext())
        PrefUtil.setTimerState(timerState,requireContext())

    }

    private fun initTimer(){

        timerState = PrefUtil.getTimerState(requireContext())
        // if the Length was changed in settings while it was backgrounded
        if(timerState == TimerState.Stopped)
            setNewTimerLength()
        else
            setPreviousTimerLength()

            secondsRemaining = if(timerState == TimerState.Running || timerState == TimerState.Paused)
            PrefUtil.getSecondsRemaining(requireContext())
        else
            timerLengthSeconds

        val alarmSetTime = PrefUtil.getAlarmSetTime(requireContext())
        if(alarmSetTime>0)
            secondsRemaining -= nowSeconds - alarmSetTime

        if(secondsRemaining <= 0)
            onTimerFinished()
        else if(timerState== TimerState.Running)
            startTimer()


        updateButtons()
        updateCountdownUI()
    }


    private fun onTimerFinished(){

        timerState = TimerState.Stopped

        setNewTimerLength()

        progress_countdown.progress = 0

        PrefUtil.setSecondsRemaining(timerLengthSeconds,requireContext())
        secondsRemaining = timerLengthSeconds


        updateButtons()
        updateCountdownUI()

    }

    private fun startTimer(){
        timerState = TimerState.Running

        timer = object : CountDownTimer(secondsRemaining * 1000,1000){
            override fun onFinish() = onTimerFinished()

            override fun onTick(millisUntilFinished: Long) {
                secondsRemaining  = millisUntilFinished /1000
                updateCountdownUI()

            }
        }.start()
    }

    private  fun setNewTimerLength(){

        val lengthInMinutes = PrefUtil.getTimerLength(requireContext())
        timerLengthSeconds = (lengthInMinutes * 60L)
        progress_countdown.max = timerLengthSeconds.toInt()

    }

    private fun setPreviousTimerLength(){

        timerLengthSeconds = PrefUtil.getPreviousTimerLengthSeconds(requireContext())
        progress_countdown.max= timerLengthSeconds.toInt()

    }

    private fun updateCountdownUI(){

        val minutesUntilFinished = secondsRemaining / 60
        val secondsInMinutesUntilFinished = secondsRemaining - minutesUntilFinished * 60
        val secondsStr = secondsInMinutesUntilFinished.toString()
        time_counter.text = "$minutesUntilFinished:${
            if(secondsStr.length == 2) secondsStr
            else "0" + secondsStr
        }"
        progress_countdown.progress = (timerLengthSeconds - secondsRemaining).toInt()

    }

    private fun updateButtons(){
        when(timerState){

            TimerState.Running-> {
                fab_play.isEnabled = false
                fab_pause.isEnabled = true
                fab_stop.isEnabled = true
            }
            TimerState.Stopped->{
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = false
            }
            TimerState.Paused->{
                fab_play.isEnabled = true
                fab_pause.isEnabled = false
                fab_stop.isEnabled = true

            }
        }
    }




}