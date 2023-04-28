package com.example.beStudious.Calendar

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.TextView
import androidx.activity.viewModels
import com.example.beStudious.BeStudious
import java.time.LocalDate
import com.example.beStudious.Calendar.CalendarAdapter.OnItemListener
import com.example.beStudious.Calendar.CalendarUtils.daysInWeekArray
import com.example.beStudious.Calendar.CalendarUtils.monthYearFromDate
import com.example.beStudious.Login
import com.example.beStudious.SettingsActivity
import com.example.beStudious.TaskList.TaskItemModelFactory
import com.example.beStudious.TaskList.TaskViewModel
import com.example.beStudious.TaskList.taskMain
import com.example.beStudious.Timer.TimerActivity
import com.example.beStudious.databinding.ActivityWeekViewBinding
import com.example.beStudious.feed.feedMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class WeekViewActivity : AppCompatActivity(), OnItemListener {
    lateinit var dateTV: TextView
    lateinit var calendarRecyclerView: RecyclerView
    lateinit var binding: ActivityWeekViewBinding
    private var eventListView: ListView? = null
    private var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth

    /*
        Connects to TaskViewModel.kt class to obtain task items
     */
    private val taskViewModel: TaskViewModel by viewModels{
        TaskItemModelFactory((application as BeStudious).respository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish();
        }

        // Task items with dates are added to eventList as an Event
        Event.eventsList.clear()
        setEventList()
        initWidgets()
        setWeekView()
        setupNavBar()
    }

    /*
       Binds calendarRecyclerView to "@+id/calendarRecyclerView" in activity_week_view.xml
       Binds dateTV to "@+id/dateTV" in activity_week_view.xml
       Binds eventListView to "@+id/eventListView" in activity_week_view.xml
     */
    private fun initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView
        dateTV = binding.dateTV
        eventListView = binding.eventListView
    }

    private fun setEventList(){
        taskViewModel.TaskItems.observe(this){
            for(task in it){
                if(task.date() == ""){
                    continue
                }
                var newDate =
                    task.date()?.substringBefore("/")?.toInt()
                        ?.let { it1 -> task.date()?.substringAfter("/")?.substringBefore("/")?.toInt()
                            ?.let { it2 ->
                                LocalDate.of(
                                    task.date()?.substringAfter("/")?.substringAfter("/")?.toInt()!!, it1, it2
                                )
                            } }
                val event = Event(task.name, task.desc, newDate , task.dueTime())
                var check = false
                for(obj in Event.eventsList){
                    if(event.name == obj.name && event.date == obj.date){
                        check = true
                        break
                    }
                }
                if(!check){
                    Event.eventsList.add(event)
                }
            }
            val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
            val eventAdapter = EventAdapter(applicationContext, dailyEvents)
            eventListView!!.adapter = eventAdapter
        }
    }

    /*
        Sets the weekly view
     */
    private fun setWeekView() {
        binding.dateTV.text = monthYearFromDate(CalendarUtils.selectedDate!!)
        val days = daysInWeekArray(CalendarUtils.selectedDate!!)
        val calendarAdapter = CalendarAdapter(days, this)
        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = calendarAdapter
        setEventAdapter()
    }

    /*
        Function to view previous week
     */
    fun previousWeekAction(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusWeeks(1)
        setWeekView()
    }

    /*
        Function to view next week
     */
    fun nextWeekAction(view: View) {
        CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusWeeks(1)
        setWeekView()
    }

    /*
        Sets the date to CalendarUtil's selectedDate &
        Sets the weekly view
     */
    override fun onItemClick(position: Int, date: LocalDate?) {
        CalendarUtils.selectedDate = date
        setWeekView()
    }

    /*
        When WeekViewActivity becomes visible it calls setEventAdapter() function
     */
    override fun onResume() {
        super.onResume()
        setEventAdapter()
    }

    /*
        Obtains the list of events for the selected date from Event.kt class
        Connects to EventAdapter.kt to display list of events
     */
    private fun setEventAdapter() {
        val dailyEvents = Event.eventsForDate(CalendarUtils.selectedDate)
        val eventAdapter = EventAdapter(applicationContext, dailyEvents)
        eventListView!!.adapter = eventAdapter
    }

    /*
        Function to start CalendarMain if "Monthly" button is selected
     */
    fun monthlyAction(view: View) {
        val intent = Intent(this, CalendarMain::class.java)
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

