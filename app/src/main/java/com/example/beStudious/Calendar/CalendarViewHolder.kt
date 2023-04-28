package com.example.beStudious.Calendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import com.example.beStudious.Calendar.CalendarAdapter.OnItemListener
import com.example.beStudious.R

class CalendarViewHolder(
    itemView: View,
    onItemListener: OnItemListener,
    days: ArrayList<LocalDate?>

    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val days: ArrayList<LocalDate?>
    @JvmField
    val parentView: View
    @JvmField
    val dayOfMonth: TextView
    private val onItemListener: OnItemListener

    /*
        Sets parentView to "@+id/parentView" in calendar_cell.xml
        Sets dayOfMonth view to "@+id/cellDayText" in calendar_cell.xml
        Sets onItemListener, itemView.setOnClickListener, & days arrayList to "this"
     */
    init {
        parentView = itemView.findViewById(R.id.parentView)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
        this.days = days
    }

    /*
        Connects onItemListener to Calendar Adapter's interface OnItemListener
        onItemClick obtains calendar adapter's position in days & the associated date
     */
    override fun onClick(view: View) {
        onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }
}

