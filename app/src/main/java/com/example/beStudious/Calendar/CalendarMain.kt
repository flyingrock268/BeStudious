package com.example.beStudious.Calendar

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beStudious.SettingsActivity
import com.example.beStudious.TaskList.taskMain
import com.example.beStudious.Timer.TimerActivity
import com.example.beStudious.databinding.CalendarMainBinding
import java.time.LocalDate
import com.example.beStudious.Calendar.CalendarAdapter.OnItemListener
import com.example.beStudious.Login
import com.example.beStudious.feed.feedMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class CalendarMain : AppCompatActivity(), OnItemListener {
    lateinit var dateTV: TextView
    lateinit var calendarRecyclerView: RecyclerView
    lateinit var binding: CalendarMainBinding
    private var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalendarMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish();
        }

        initWidgets()
        CalendarUtils.selectedDate = LocalDate.now()
        setMonthView()
        setupNavBar()
    }

    /*
       Binds calendarRecyclerView to "@+id/calendarRecyclerView" in calendar_main.xml
       Binds dateTV to "@+id/dateTV" in calendar_main.xml
     */
    private fun initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView
        dateTV = binding.dateTV
    }

    /*
        Sets the monthly view
     */
    private fun setMonthView() {
        binding.dateTV.text = CalendarUtils.monthYearFromDate(CalendarUtils.selectedDate)
        val daysInMonth = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate)
        val calendarAdapter = CalendarAdapter(daysInMonth, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
    }

    /*
        Function to view previous month
     */
    fun previousMonthAction(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
        setMonthView()
    }

    /*
        Function to view next month
     */
    fun nextMonthAction(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
        setMonthView()
    }

    /*
        If date != null, then it sets the date to CalendarUtil's selectedDate &
        sets the monthly view
     */
    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {
            CalendarUtils.selectedDate = date
            setMonthView()
        }
    }

    /*
        Function to start WeekViewActivity if "Weekly" button is selected
     */
    fun weeklyAction(view: View) {
        val intent = Intent(this, WeekViewActivity::class.java)
        startActivity(intent)
    }

    /*
        Function to setup nav bar
     */
    fun setupNavBar(){
        //moves navBar up into place and fades it in
        ObjectAnimator.ofFloat(binding.navBar,"translationY", 250f,0f).apply{
            duration = 1000
            start()
        }

        ObjectAnimator.ofFloat(binding.navBar,"alpha", 0f,1f).apply{
            duration = 2000
            start()
        }

        binding.tasksButton.setOnClickListener{
            val intent = Intent(this, taskMain::class.java)
            startActivity(intent)
        }

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