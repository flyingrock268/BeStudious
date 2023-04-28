package com.example.beStudious.TaskList

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.beStudious.Calendar.Event
import com.example.beStudious.R
import com.example.beStudious.databinding.FragmentNewTaskSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDate.*
import java.time.LocalTime
import java.util.*

class newTaskSheet(var taskItem: TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var cal = Calendar.getInstance()
    private var dueTime: LocalTime? = null
    private var date: String? = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if(taskItem != null){

            binding.TaskTitle.text = "Edit Task"

            val editable = Editable.Factory.getInstance()
            binding.name.text = editable.newEditable(taskItem!!.name)
            binding.desc.text = editable.newEditable(taskItem!!.desc)

            if(taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()
                updateTimeButtonText()
            }

            if(taskItem!!.date() != null){
                date = taskItem!!.dateString
                updateDateButtonText()
            }
        }
        else{


            binding.TaskTitle.text = "New Task"


        }

        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]

        binding.SaveButtonTask.setOnClickListener{
            saveAction()
        }

        binding.timePickerButton.setOnClickListener{
            openTimePicker()
        }

        binding.datePickerButton.setOnClickListener{
            openDatePicker()
        }

        binding.DeleteButton.setOnClickListener {
            if(taskItem != null){
                val temp = taskItem!!
                taskViewModel.deleteTaskItem(temp)
            }
            dismiss()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if(dueTime == null){
            dueTime = LocalTime.now()
        }
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateTimeButtonText()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()

    }


    private fun openDatePicker(){
        if (date == null || date == "") {
            date = ""
        }
        val dateSetListener = DatePickerDialog.OnDateSetListener{ _, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = " "
            updateDateButtonText()
        }
        val dialog = DatePickerDialog(requireActivity(), dateSetListener,
            cal.get(Calendar.YEAR) ,cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))
        dialog.setTitle("Task Date")
        dialog.show()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d", dueTime!!.hour, dueTime!!.minute)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateDateButtonText(){
        if (date == ""){
            binding.datePickerButton.text = "Select Date"
        }
        else {
            val format = "MM/dd/yyyy"
            val sdf = SimpleDateFormat(format, Locale.US)
            binding.datePickerButton.text = sdf.format(cal.time)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun convertToLocalDate(): LocalDate? {
        val format = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(format, Locale.US)
        val date = sdf.format(cal.time)
        val convertedDate : LocalDate? = parse(date)
        return convertedDate
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveAction() {
        val name= binding.name.text.toString()
        val desc= binding.desc.text.toString()
        val dueTimeString = if(dueTime == null) null else TaskItem.timeFormater.format(dueTime)
        val dateString = if(binding.datePickerButton.text.toString() == "Select Date") "" else binding.datePickerButton.text.toString()

        // Adds task item to eventList or updates existing event with new information
        if(dateString != ""){
            val event = Event(name, desc, convertToLocalDate() , dueTime)
            if(taskItem == null) {
                Event.eventsList.add(event)
                Toast.makeText(activity,"Task added to Calendar",Toast.LENGTH_SHORT).show()
            }
            else{
                val index = Event.eventsList.indexOfFirst { it.name == taskItem!!.name }
                if(index >= 0){
                    Event.eventsList[index] = event
                    Toast.makeText(activity,"Task updated in Calendar",Toast.LENGTH_SHORT).show()
                }
            }
        }

        if(taskItem == null){

            if(name == ""){

                val toast = Toast.makeText(this.context, "Task needs a name", Toast.LENGTH_SHORT)
                toast.show()
                dismiss()

            }

            else {

                val newTask = TaskItem(name, desc, dueTimeString, null, dateString)

                taskViewModel.addTaskItem(newTask)
            }
        }

        else{
            taskItem!!.name = name
            taskItem!!.desc = desc
            taskItem!!.dueTimeString = dueTimeString
            taskItem!!.dateString = dateString
            taskViewModel.updateTaskItem(taskItem!!)
        }

        binding.name.setText("")
        binding.desc.setText("")
        dismiss()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewTaskSheetBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return binding.root
    }

}