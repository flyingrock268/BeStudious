package com.example.beStudious.TaskList

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beStudious.feed.FeedItem
import com.example.beStudious.feed.FeedItemDao
import com.example.beStudious.feed.FeedTypeConverter

@Database(entities = [TaskItem::class,FeedItem::class], version = 1, exportSchema = false)
@TypeConverters(FeedTypeConverter::class)
abstract class taskItemDatabase: RoomDatabase(){

    abstract fun taskItemDao(): taskItemDao
    abstract fun FeedItemDao(): FeedItemDao

    companion object{

        @Volatile
        private var INSTANCE: taskItemDatabase? = null

        fun getDatabase(context: Context): taskItemDatabase {

            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    taskItemDatabase::class.java,
                    "task_item_database"

                ).build()
                INSTANCE = instance
                instance

            }

        }

    }

}