package com.example.beStudious


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beStudious.Calendar.CalendarMain
import com.example.beStudious.TaskList.taskMain
import com.example.beStudious.databinding.FragmentNavBarBinding


class NavBarFragment : androidx.fragment.app.Fragment() {

    private lateinit var binding: FragmentNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentNavBarBinding.inflate(layoutInflater)

        //changes the activity to the taskList
        binding.tasksButton.setOnClickListener(){

            val intent = Intent(this.context, taskMain::class.java)
            startActivity(intent)

        }

        //changes the activity to the Calendar
        binding.CalendarButton.setOnClickListener(){

            val intent = Intent(this.context, CalendarMain::class.java)
            startActivity(intent)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_bar, container, false)
    }


}