package com.example.beStudious

import android.app.Application
import com.example.beStudious.TaskList.TaskItemRespository
import com.example.beStudious.TaskList.taskItemDatabase

class BeStudious: Application() {

    private val database by lazy { taskItemDatabase.getDatabase(this) }
    val respository by lazy{ TaskItemRespository(database.taskItemDao(), database.FeedItemDao()) }

}