package com.example.beStudious.Calendar

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarUtils {
    @JvmField
    var selectedDate: LocalDate ?= null


    /*
        Returns the month & year from date
     */
    @JvmStatic
    fun monthYearFromDate(date: LocalDate?): String{
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date!!.format(formatter)
    }

    /*
        Returns the time in "hh:mm:ss a" format
     */
    @JvmStatic
     fun formattedTime(time: LocalTime?): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        return time!!.format(formatter)
    }

    /*
        Returns the date in "dd MMMM yyyy" format
     */
    @JvmStatic
    fun formattedDate(date: LocalDate?): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date!!.format(formatter)
    }

    /*
        Returns an array of the number of days in a month
     */
    fun daysInMonthArray(date: LocalDate?): ArrayList<LocalDate?> {
        val daysInMonthArray = ArrayList<LocalDate?>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add(null)
            } else {
                daysInMonthArray.add(
                    LocalDate.of(
                        selectedDate!!.year,
                        selectedDate!!.month,
                        i - dayOfWeek
                    )
                )
            }
        }
        return daysInMonthArray
    }

    /*
        Returns an array of the number of days in a week
     */
    @JvmStatic
    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = sundayForDate(selectedDate)
        val endDate = current!!.plusWeeks(1)

        while (current!!.isBefore(endDate)) {
                days.add(current)
                current = current.plusDays(1)
        }
        return days
    }

    /*
        Gets the date for the Sunday of the week
     */
    private fun sundayForDate(current: LocalDate): LocalDate? {
        var current = current
        val oneWeekAgo = current.minusWeeks(1)

        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.SUNDAY)
                return current
            current = current.minusDays(1)
        }
        return null
    }
 }