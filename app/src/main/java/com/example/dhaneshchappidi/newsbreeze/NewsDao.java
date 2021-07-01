package com.example.dhaneshchappidi.newsbreeze;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dhaneshchappidi.newsbreeze.model.News;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void insert(News news);

    @Delete
    void delete(News news);

    @Query("DELETE FROM News_table")
    void deleteallnews();

    @Query("SELECT *FROM News_table WHERE type='Bookmark'")
    LiveData<List<News>> getBookmarks();

    @Query("SELECT *FROM News_table WHERE type='Download'")
    LiveData<List<News>> getDownloadnews();
}
