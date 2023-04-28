package com.example.beStudious.TaskList

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.beStudious.feed.FeedItem
import kotlinx.coroutines.launch
import java.time.LocalDate

class TaskViewModel(private val repository: TaskItemRespository): ViewModel() {

    var TaskItems: LiveData<List<TaskItem>> = repository.allTaskItems.asLiveData()
    var FeedItems: LiveData<List<FeedItem>> = repository.allFeedItems.asLiveData()

    fun addTaskItem(newTask: TaskItem) = viewModelScope.launch {

        repository.insertTaskItem(newTask)

    }

    fun deleteTaskItem(newTask: TaskItem) = viewModelScope.launch {

        repository.deleteTaskItem(newTask)

    }

    fun updateTaskItem(taskItem: TaskItem) = viewModelScope.launch{

        repository.updateTaskItem(taskItem)

    }

    fun insertFeedItem(feedItem: FeedItem) = viewModelScope.launch{

        repository.insertFeedItem(feedItem)

    }

    fun RemoveFeedItem(feedItem: FeedItem) = viewModelScope.launch{

        repository.deleteFeedItem(feedItem)

    }

//    fun RemoveFeedItem(feedItem: Int) = viewModelScope.launch{
//
//        repository.deleteFeedItem()
//
//    }

    fun UpdateFeedItem(feedItem: FeedItem) = viewModelScope.launch{

        repository.updateFeedItem(feedItem)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCompleted(taskItem: TaskItem) = viewModelScope.launch {

        if(!taskItem.isCompleted()){

            taskItem.completedDateString = TaskItem.dateFormater.format(LocalDate.now())

        }

        repository.updateTaskItem(taskItem)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun undoCompleted(taskItem: TaskItem) = viewModelScope.launch {

        if(taskItem.isCompleted()){

            taskItem.completedDateString = null

        }

        repository.updateTaskItem(taskItem)

    }

}

class TaskItemModelFactory(private val repository: TaskItemRespository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(TaskViewModel::class.java)){

            return TaskViewModel(repository) as T

        }

        throw java.lang.IllegalArgumentException("Unknown Class for View Model")

    }
}