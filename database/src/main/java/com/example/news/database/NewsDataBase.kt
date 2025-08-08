package com.example.news.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


import com.example.news.database.dao.ArticleDao
import com.example.news.database.models.ArticleDBO
import com.example.news.database.utils.Converters

@Database(entities = [ArticleDBO::class], version = 1)
@TypeConverters(Converters::class)
abstract class NewsDataBase: RoomDatabase() {

    abstract fun articlesDao(): ArticleDao
}

fun NewsDataBase(applicationContext: Context): NewsDataBase{
    return Room.databaseBuilder(
        checkNotNull(applicationContext.applicationContext),
        NewsDataBase::class.java,
        "news"
    ).build()
}