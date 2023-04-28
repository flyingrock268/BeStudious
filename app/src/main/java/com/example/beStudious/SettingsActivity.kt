package com.example.beStudious

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.beStudious.Calendar.CalendarMain
import com.example.beStudious.TaskList.taskMain
import com.example.beStudious.Timer.TimerActivity
import com.example.beStudious.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.example.beStudious.feed.feedMain

class SettingsActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySettingsBinding
    lateinit var auth: FirebaseAuth
    lateinit var button: Button
    private var user: FirebaseUser? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //auth = FirebaseAuth.getInstance()

        button = findViewById(R.id.logout)
        // textView = findViewById(R.id.textView)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null) {
            val intent = Intent(applicationContext,Login::class.java)
            startActivity(intent)
            finish();
        }
        else {

            binding.email.text = "Email:\n" + user!!.email

        }

        binding.email.setOnClickListener{

            val toast = Toast.makeText(applicationContext, "Cannot change email", Toast.LENGTH_SHORT)
            toast.show()

        }

        button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()

        }
        setupNavBar()
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
        binding.tasksButton.setOnClickListener(){

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
