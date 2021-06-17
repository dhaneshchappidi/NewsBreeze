package com.example.dhaneshchappidi.newsbreeze.API;

import com.example.dhaneshchappidi.newsbreeze.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getNews(
            @Query("q") String keyword,
            @Query("country") String country ,
            @Query("apiKey") String apiKey,
            @Query("category") String category,
            @Query("sortBy") String sortBy


    );
}
