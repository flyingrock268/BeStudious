package com.example.beStudious.TaskList

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beStudious.BeStudious
import com.example.beStudious.Calendar.CalendarMain
import com.example.beStudious.Login
import com.example.beStudious.SettingsActivity
import com.example.beStudious.Timer.TimerActivity
import com.example.beStudious.databinding.TaskMainBinding
import com.example.beStudious.feed.feedMain
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class taskMain : AppCompatActivity() , TaskItemClickListener {

    private lateinit var binding: TaskMainBinding
    private var user: FirebaseUser? = null
    lateinit var auth: FirebaseAuth
    private val taskViewModel: TaskViewModel by viewModels{

        TaskItemModelFactory((application as BeStudious).respository)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TaskMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        //moves newTaskButton into place and fades it in
//        ObjectAnimator.ofFloat(binding.newTaskButton,"translationX", 400f,0f).apply{
//            duration = 1500
//            start()
//        }
//        ObjectAnimator.ofFloat(binding.newTaskButton,"alpha", 0f,1f).apply{
//            duration = 2000
//            start()
//        }

        auth = FirebaseAuth.getInstance()
        user = auth.currentUser
        if(user == null) {
            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish();
        }

        binding.newTaskButton.setOnClickListener{
            newTaskSheet(null).show(supportFragmentManager, "NewTaskTag")
        }

        setRecyclerView()
        setupNavBar()

    }
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

        //changes the activity to the taskList
        binding.tasksButton.setOnClickListener(){
            val intent = Intent(this, taskMain::class.java)
            startActivity(intent)
        }

        //changes the activity to the Calendar
        binding.CalendarButton.setOnClickListener(){
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

    private fun setRecyclerView(){
        val mainActivity = this
        taskViewModel.TaskItems.observe(this){
            binding.todoListRecyclerView.apply {
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = TaskItemAdapter(it, mainActivity)
            }
        }
    }

    override fun editTaskItem(taskItem: TaskItem) {
        newTaskSheet(taskItem).show(supportFragmentManager, "NewTaskTag")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun completeTaskItem(taskItem: TaskItem) {
        taskViewModel.setCompleted(taskItem)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun undoTaskItem(taskItem: TaskItem) {
        taskViewModel.undoCompleted(taskItem)
    }

    override fun removeTaskItem(taskItem: TaskItem) {
        taskViewModel.deleteTaskItem(taskItem)
    }
}