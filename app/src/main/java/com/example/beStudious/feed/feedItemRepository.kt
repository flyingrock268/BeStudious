package com.example.beStudious.feed

import androidx.annotation.WorkerThread
import com.example.beStudious.TaskList.TaskItem
import com.example.beStudious.TaskList.taskItemDao
import kotlinx.coroutines.flow.Flow

class feedItemRepository(private val FeedItemDao: FeedItemDao) {

    fun allFeedItems(): Flow<List<FeedItem>> = FeedItemDao.allFeedItems()

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