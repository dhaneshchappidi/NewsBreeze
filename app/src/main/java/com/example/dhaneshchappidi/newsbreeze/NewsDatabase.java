package com.example.dhaneshchappidi.newsbreeze;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dhaneshchappidi.newsbreeze.model.News;

@Database(entities = {News.class},version = 1)
public abstract class NewsDatabase extends RoomDatabase {
    private static NewsDatabase instance;
    public abstract NewsDao newsDao();

    public static synchronized NewsDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),
                    NewsDatabase.class,"news_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
