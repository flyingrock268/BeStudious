package com.example.beStudious.Calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.beStudious.R

class EventAdapter(
        context: Context,
        events: List<Event?>?

): ArrayAdapter<Event?>(context,0, events!!) {

    /*
        Sets up the event list view
     */
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val event = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        }

        val eventNameTV =  convertView!!.findViewById<TextView>(R.id.eventNameTV)
        val eventName = event!!.name
        eventNameTV.text = eventName

        val eventDescriptionTV = convertView!!.findViewById<TextView>(R.id.eventDescriptionTV)
        val eventDescription = event!!.desc
        eventDescriptionTV.text = eventDescription

        val eventDateTV = convertView!!.findViewById<TextView>(R.id.eventDateTV)
        val eventDate = event!!.date.toString()
        eventDateTV.text = eventDate

        val eventTimeTV = convertView!!.findViewById<TextView>(R.id.eventTimeTV)
        val eventTime = if (event.time != null) event.time.toString() else ""
        eventTimeTV.text = eventTime

        return convertView
    }
}