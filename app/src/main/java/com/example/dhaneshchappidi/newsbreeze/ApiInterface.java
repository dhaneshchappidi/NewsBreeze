package com.example.dhaneshchappidi.newsbreeze;

import com.example.dhaneshchappidi.newsbreeze.response.ArticleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<ArticleResponse> getNews(
            @Query("q") String keyword,
            @Query("country") String country ,
            @Query("apiKey") String apiKey,
            @Query("category") String category,
            @Query("sortBy") String sortBy

    );
}
