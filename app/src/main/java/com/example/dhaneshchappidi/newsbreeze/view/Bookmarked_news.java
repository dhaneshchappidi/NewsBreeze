package com.example.dhaneshchappidi.newsbreeze.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dhaneshchappidi.newsbreeze.adapter.Bookmarked_News_Adapter;
import com.example.dhaneshchappidi.newsbreeze.R;
import com.example.dhaneshchappidi.newsbreeze.model.News;
import com.example.dhaneshchappidi.newsbreeze.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Bookmarked_news extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<News> news = new ArrayList<>();
    ImageView Back_Button;
    Bookmarked_News_Adapter bookmark_adapter;
    ViewModel newsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_news);
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(Bookmarked_news.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        Back_Button=(ImageView) findViewById(R.id.back_button);
        newsViewModel= ViewModelProviders.of(this).get(ViewModel.class);
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookmarked_news.super.onBackPressed();
            }
        });
        loadbookmarks();


    }

    public void loadbookmarks() {
        news.clear();
        newsViewModel.getbookmarks().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                bookmark_adapter=new Bookmarked_News_Adapter(Bookmarked_news.this,news);
                recyclerView.setAdapter(bookmark_adapter);
                bookmark_adapter.notifyDataSetChanged();
            }
        });

    }
}