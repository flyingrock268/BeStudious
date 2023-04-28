package com.example.beStudious.feed

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

import androidx.core.content.ContextCompat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.beStudious.R
import kotlinx.coroutines.NonCancellable.isCompleted
import java.time.LocalTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "task_item_table")
class TaskItemfeed (

    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "desc") var desc: String,
    @PrimaryKey(autoGenerate = true) var id: Int = 0

        ){



    @RequiresApi(Build.VERSION_CODES.O)
    fun imageResource(): Int = if(isCompleted) R.drawable.checked_24 else R.drawable.unchecked_24
    @RequiresApi(Build.VERSION_CODES.O)
    fun imageColor(context: Context): Int = if(isCompleted) purple(context) else black(context)

    private fun purple(context: Context) = ContextCompat.getColor(context, R.color.purple_500)
    private fun black(context: Context) = ContextCompat.getColor(context, R.color.black)

    companion object{

        @RequiresApi(Build.VERSION_CODES.O)
        val timeFormater: DateTimeFormatter = DateTimeFormatter.ISO_TIME
        @RequiresApi(Build.VERSION_CODES.O)
        val dateFormater: DateTimeFormatter = DateTimeFormatter.ISO_DATE

    }

}