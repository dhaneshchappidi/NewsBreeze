package com.example.dhaneshchappidi.newsbreeze.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dhaneshchappidi.newsbreeze.repository.NewsRepository;
import com.example.dhaneshchappidi.newsbreeze.model.News;
import com.example.dhaneshchappidi.newsbreeze.repository.Articlerepository;
import com.example.dhaneshchappidi.newsbreeze.response.ArticleResponse;

import java.util.List;

public class ViewModel extends AndroidViewModel {
    Articlerepository articlerepository;
    LiveData<ArticleResponse> articleResponseLiveData;
    private NewsRepository repository;
    private LiveData<List<News>> allNews;
    private LiveData<List<News>> downnews;
    public ViewModel(@NonNull Application application) {
        super(application);
        repository=new NewsRepository(application);
        allNews=repository.getBookmarks();

    }
    public LiveData<ArticleResponse> getArticleResponseLiveData(String keyword,String country,String apikey,String category,String sortby){
        articlerepository=new Articlerepository();
        this.articleResponseLiveData=articlerepository.getDashBoardNews(keyword,country,apikey,category,sortby);
        return articleResponseLiveData;
    }
    public void insert(News news){
        repository.insert(news);
    }
    public void delete(News news){
        repository.delete(news);
    }
    public void deleteallnews(){
        repository.deleteallnews();
    }
    public LiveData<List<News>> getbookmarks(){
        return allNews;
    }
    public LiveData<List<News>> getDownnews(){
        downnews=repository.getDownnews();
        return downnews;
    }

}
