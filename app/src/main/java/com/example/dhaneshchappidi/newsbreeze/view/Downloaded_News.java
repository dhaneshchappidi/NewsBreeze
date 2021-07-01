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

import com.example.dhaneshchappidi.newsbreeze.adapter.Downloaded_Adapter;
import com.example.dhaneshchappidi.newsbreeze.R;
import com.example.dhaneshchappidi.newsbreeze.model.News;
import com.example.dhaneshchappidi.newsbreeze.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class Downloaded_News extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<News> news = new ArrayList<>();
    ImageView Back_Button;
    Downloaded_Adapter downloaded_adapter;
    ViewModel newsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloaded_news);

        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(Downloaded_News.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        newsViewModel= ViewModelProviders.of(this).get(ViewModel.class);
        Back_Button=(ImageView) findViewById(R.id.back_button);
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Downloaded_News.super.onBackPressed();
            }
        });
        loaddownloadnews();
    }

    public void loaddownloadnews() {
        news.clear();
        newsViewModel.getDownnews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                downloaded_adapter=new Downloaded_Adapter(Downloaded_News.this,news);
                recyclerView.setAdapter(downloaded_adapter);
                downloaded_adapter.notifyDataSetChanged();
            }
        });
    }

}