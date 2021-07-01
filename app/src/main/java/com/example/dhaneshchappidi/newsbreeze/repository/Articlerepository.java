package com.example.dhaneshchappidi.newsbreeze.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.dhaneshchappidi.newsbreeze.ApiInterface;
import com.example.dhaneshchappidi.newsbreeze.Apiclient;
import com.example.dhaneshchappidi.newsbreeze.response.ArticleResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Articlerepository {
    private static final String TAG = Articlerepository.class.getSimpleName();
    private final ApiInterface apiInterface;

    public Articlerepository() {
        apiInterface = Apiclient.getApiClient().create(ApiInterface.class);
    }

    public LiveData<ArticleResponse> getDashBoardNews(String keyword, String country, String apikey, String category, String sortby) {
        final MutableLiveData<ArticleResponse> data = new MutableLiveData<>();
        apiInterface.getNews(keyword, country, apikey, category, sortby)
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                        if (response.body() != null) {
                            data.setValue(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }
}
