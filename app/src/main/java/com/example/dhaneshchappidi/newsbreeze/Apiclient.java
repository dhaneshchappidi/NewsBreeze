package com.example.dhaneshchappidi.newsbreeze;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Apiclient {
    public static final String URL = "https://newsapi.org/v2/";
    public static Retrofit retrofit;

    public static Retrofit getApiClient(){

        if (retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
