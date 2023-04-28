package com.example.beStudious.Calendar

import java.time.LocalDate
import java.time.LocalTime

class Event(
    var name: String,
    var desc: String,
    var date: LocalDate?,
    var time: LocalTime?
    ){

    companion object {
        var eventsList = ArrayList<Event>()


        /*
           Returns list of events for the day selected
         */
        fun eventsForDate(date: LocalDate?): ArrayList<Event> {
            val events = ArrayList<Event>()
            for (event in eventsList) {
                if (event.date == date)
                    events.add(event)
            }
            return events
        }
    }
}