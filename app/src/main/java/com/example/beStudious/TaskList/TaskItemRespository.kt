package com.example.beStudious.TaskList

import androidx.annotation.WorkerThread
import com.example.beStudious.feed.FeedItem
import com.example.beStudious.feed.FeedItemDao
import kotlinx.coroutines.flow.Flow

class TaskItemRespository(private val taskItemDao: taskItemDao,private val FeedItemDao: FeedItemDao) {

    val allTaskItems: Flow<List<TaskItem>> = taskItemDao.allTaskItems()
    val allFeedItems: Flow<List<FeedItem>> = FeedItemDao.allFeedItems()

    @WorkerThread
    suspend fun insertTaskItem(taskItem: TaskItem){

        taskItemDao.insertTaskItem(taskItem)

    }

    @WorkerThread
    suspend fun updateTaskItem(taskItem: TaskItem){

        taskItemDao.updateTaskItem(taskItem)

    }

    @WorkerThread
    suspend fun deleteTaskItem(taskItem: TaskItem){

        taskItemDao.deleteTaskItem(taskItem)

    }

    @WorkerThread
    suspend fun insertFeedItem(feedItem: FeedItem){

        FeedItemDao.insertFeedItem(feedItem)

    }

    @WorkerThread
    suspend fun updateFeedItem(feedItem: FeedItem){

        FeedItemDao.updateFeedItem(feedItem)

    }

    @WorkerThread
    suspend fun deleteFeedItem(feedItem: FeedItem){

        FeedItemDao.deleteFeedItem(feedItem)

    }

}