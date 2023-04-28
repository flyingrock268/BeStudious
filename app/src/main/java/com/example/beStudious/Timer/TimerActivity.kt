package com.example.beStudious.Timer

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beStudious.Calendar.CalendarMain
import com.example.beStudious.Login
import com.example.beStudious.SettingsActivity
import com.example.beStudious.TaskList.taskMain
import com.example.beStudious.databinding.TimerMainBinding
import com.example.beStudious.feed.feedMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class TimerActivity : AppCompatActivity() {

//    private lateinit var timerTextView: TextView
//    private lateinit var timeInput: EditText
//    private lateinit var startButton: Button
//    private lateinit var tasksButton: Button
    private lateinit var countdownTimer: CountDownTimer
    private lateinit var binding: TimerMainBinding
    private var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TimerMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        timerTextView = findViewById(R.id.timer_text_view)
//        timeInput = findViewById(R.id.time_input)
//        startButton = findViewById(R.id.start_button)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish();
        }

        binding.startButton.setOnClickListener {

            if (::countdownTimer.isInitialized) {
                countdownTimer.cancel()
            }

            var timeInMilliseconds: Long = 0

            if(binding.timeInputSeconds.text.toString() != ""){

                if(binding.timeInputSeconds.text.toString().toLong() > 60){

                    timeInMilliseconds += 60000

                }

                else{

                    timeInMilliseconds += binding.timeInputSeconds.text.toString().toLong() *1000

                }

            }

            if(binding.timeInputMinutes.text.toString() != ""){

                if(binding.timeInputMinutes.text.toString().toLong() > 60){

                    timeInMilliseconds += 60000 * 60

                }

                else{

                    timeInMilliseconds += binding.timeInputMinutes.text.toString().toLong() *1000 * 60

                }

            }

            if(binding.timeInputHours.text.toString() != ""){

                timeInMilliseconds += binding.timeInputHours.text.toString().toLong() *1000 * 60 * 60

            }

            if(timeInMilliseconds == 0L){

                val toast = Toast.makeText(applicationContext, "Timer needs to have an input", Toast.LENGTH_SHORT)
                toast.show()

            }

            else{

                startTimer(timeInMilliseconds)

            }

        }



        setupNavBar()
    }

    private fun startTimer(timeInMilliseconds: Long) {
        countdownTimer = object : CountDownTimer(timeInMilliseconds, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                val HoursLeft = millisUntilFinished /(1000 * 60 * 60)
                val minutesLeft = (millisUntilFinished - (HoursLeft * 1000 * 60 * 60))/(1000* 60)
                val secondsLeft = (millisUntilFinished- (HoursLeft * 1000 * 60 * 60) - (minutesLeft * 1000 * 60)) / 1000
                binding.timerTextView.text = "Time remaining\n $HoursLeft hours, $minutesLeft minutes, $secondsLeft seconds"
            }

            override fun onFinish() {
                binding.timerTextView.text = "Timer finished"
            }
        }

        countdownTimer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::countdownTimer.isInitialized) {
            countdownTimer.cancel()
        }
    }

    private fun setupNavBar(){
        //moves navBar up into place and fades it in

        ObjectAnimator.ofFloat(binding.navBar,"translationY", 250f,0f).apply{
            duration = 1000
            start()
        }
        ObjectAnimator.ofFloat(binding.navBar,"alpha", 0f,1f).apply{
            duration = 2000
            start()
        }

        //changes the activity to the taskList
        binding.tasksButton.setOnClickListener{
            val intent = Intent(this, taskMain::class.java)
            startActivity(intent)
        }

        //changes the activity to the Calendar
        binding.CalendarButton.setOnClickListener{
            val intent = Intent(this, CalendarMain::class.java)
            startActivity(intent)
        }

        binding.HomeButton.setOnClickListener{
            val intent = Intent(this, feedMain::class.java)
            startActivity(intent)
        }

        binding.TimeButton.setOnClickListener{
            val intent = Intent(this, TimerActivity::class.java)
            startActivity(intent)
        }

        binding.settingsButton.setOnClickListener{

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)

        }

    }
}