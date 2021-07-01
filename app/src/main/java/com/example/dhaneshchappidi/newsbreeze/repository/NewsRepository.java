package com.example.dhaneshchappidi.newsbreeze.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.dhaneshchappidi.newsbreeze.NewsDao;
import com.example.dhaneshchappidi.newsbreeze.NewsDatabase;
import com.example.dhaneshchappidi.newsbreeze.model.News;

import java.util.List;

public class NewsRepository {
    private NewsDao newsDao;
    private LiveData<List<News>> allnews;
    private LiveData<List<News>> downnews;

    public NewsRepository(Application application){
        NewsDatabase database=NewsDatabase.getInstance(application);
        newsDao=database.newsDao();
        allnews=newsDao.getBookmarks();
    }
    public void insert(News news){
        new InsertNewsAsyncTask(newsDao).execute(news);
    }
    public void delete(News news){
        new deleteNewsAsyncTask(newsDao).execute(news);
    }
    public void deleteallnews(){
        new deleteallNewsAsyncTask(newsDao).execute();
    }
    public LiveData<List<News>> getBookmarks(){
        return allnews;
    }
    public LiveData<List<News>> getDownnews(){
        downnews=newsDao.getDownloadnews();
        return downnews;
    }

    private static class InsertNewsAsyncTask extends AsyncTask<News,Void,Void>{
        private NewsDao newsDao;
        private InsertNewsAsyncTask(NewsDao newsDao){
            this.newsDao=newsDao;
        }
        @Override
        protected Void doInBackground(News... news) {
            newsDao.insert(news[0]);
            return null;
        }
    }
    private static class deleteNewsAsyncTask extends AsyncTask<News,Void,Void> {
        private NewsDao newsDao;

        private deleteNewsAsyncTask(NewsDao newsDao) {
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.delete(news[0]);
            return null;
        }
    }
        private static class deleteallNewsAsyncTask extends AsyncTask<Void,Void,Void>{
            private NewsDao newsDao;
            private deleteallNewsAsyncTask(NewsDao newsDao){
                this.newsDao=newsDao;
            }
            @Override
            protected Void doInBackground(Void... voids) {
                newsDao.deleteallnews();
                return null;
            }
    }
}
