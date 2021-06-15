package com.example.dhaneshchappidi.newsbreeze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhaneshchappidi.newsbreeze.API.ApiInterface;
import com.example.dhaneshchappidi.newsbreeze.API.Apiclient;
import com.example.dhaneshchappidi.newsbreeze.Adapter.NewsAdapter;
import com.example.dhaneshchappidi.newsbreeze.model.Article;
import com.example.dhaneshchappidi.newsbreeze.model.News;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQEST_CODE =1 ;
    Typeface tf;
    TextView title;
    SearchView searchView;
    ImageView Bookmark;
    private NewsAdapter newsAdapter;
    public static final String API_KEY = "e4f293b3fbed47b4a376cb44d5b654ce";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case PERMISSION_REQEST_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"permission granted",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();

                }
            }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title=(TextView)findViewById(R.id.title);
        Bookmark=(ImageView)findViewById(R.id.bookmark);
        tf=Typeface.createFromAsset(getAssets(), "METALORD.TTF");
        title.setTypeface(tf);

        Bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Downloaded_News.class);
                startActivity(intent);
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)findViewById(R.id.searchview);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                LoadJson(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                LoadJson(s);
                return false;
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson("");
    }
    public void LoadJson(String keyword){
        ApiInterface apiInterface = Apiclient.getApiClient().create(ApiInterface.class);

        String country ="in";

        Call<News> call;
        call=apiInterface.getNews(keyword,country,API_KEY,"business");
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticles() != null){
                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticles();

                    Integer total=response.body().getTotalResults();
                    newsAdapter = new NewsAdapter(articles, MainActivity.this,total);
                    recyclerView.setAdapter(newsAdapter);
                    newsAdapter.notifyDataSetChanged();
                    initListener();
                } else {
                    Toast.makeText(MainActivity.this,"No result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
            }
        });

    }
    private void initListener(){
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, Second_Activity.class);
                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("description",article.getDescription());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("author",article.getAuthor());
                intent.putExtra("content",article.getContent());
                startActivity(intent);
            }
            });
    }
}