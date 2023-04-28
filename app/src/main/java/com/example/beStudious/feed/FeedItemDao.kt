package com.example.beStudious.feed

import androidx.room.*
import com.example.beStudious.TaskList.TaskItem
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedItemDao {

    @Query("SELECT * FROM Feed_item_table")
    fun allFeedItems(): Flow<List<FeedItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFeedItem(feedItem: FeedItem)

    @Update
    suspend fun updateFeedItem(feedItem: FeedItem)

    @Delete
    suspend fun deleteFeedItem(feedItem: FeedItem)

}