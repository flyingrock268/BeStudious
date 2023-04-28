package com.example.beStudious.feed

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.UUID

@Entity(tableName = "Feed_item_table")
class FeedItem(


    @ColumnInfo(name = "picture") var imageResource: Int,
    @ColumnInfo(name = "title") var text1: String,
    @ColumnInfo(name = "desc")var text2: String,
    @ColumnInfo(name = "replies")var replies: ArrayList<FeedItem>,


){

    @PrimaryKey(autoGenerate = true)var id: Int = 0



    constructor() : this(0,"","", ArrayList()){


    }

}

class FeedTypeConverter{

    @androidx.room.TypeConverter
    fun fromString(value: String): ArrayList<FeedItem>{

        val listType = object: TypeToken<ArrayList<FeedItem>>(){}.type
        return Gson().fromJson(value, listType)

    }

    @androidx.room.TypeConverter
    fun fromList(list: ArrayList<FeedItem>): String{

        return Gson().toJson(list)

    }

}
