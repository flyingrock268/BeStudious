package com.example.beStudious.Calendar

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beStudious.R
import java.time.LocalDate

class CalendarAdapter(
    private val days: ArrayList<LocalDate?>,
    private val onItemListener: OnItemListener

    ): RecyclerView.Adapter<CalendarViewHolder>() {

    /*
        Creates either the monthly view or weekly view of Calendar
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        // If size of days arrayList is greater than 15 then display month view
        if (days.size > 15) {
            layoutParams.height = (parent.height * 0.166666666).toInt()
        }
        // Else display week view
        else {
            layoutParams.height = parent.height
        }
        return CalendarViewHolder(view, onItemListener, days)
    }

    /*
        Sets the text of the calendar cells &
        Sets the background color of a date selected to light grey
     */
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        if (date == null) {
            holder.dayOfMonth.text = ""
        } else {
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if (date == CalendarUtils.selectedDate) {
                holder.parentView.setBackgroundColor(Color.LTGRAY)
            }
        }
    }

    /*
        Returns size of ArrayList days
     */
    override fun getItemCount(): Int {
        return days.size
    }

    /*
        Interface for Calendar Adapter's OnItemListener
        onItemClick obtains position in days & the associated date
     */
    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate?)
    }
}
