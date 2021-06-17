package com.example.dhaneshchappidi.newsbreeze;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;

import com.example.dhaneshchappidi.newsbreeze.Adapter.Bookmarked_News_Adapter;
import com.example.dhaneshchappidi.newsbreeze.model.Down_News_model;

import java.util.ArrayList;
import java.util.List;

public class Bookmarked_news extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Down_News_model> articles = new ArrayList<>();
    DBHelper dbHelper;
    SearchView searchView;
    ImageView Back_Button;
    Bookmarked_News_Adapter bookmark_adapter;
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
        Back_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bookmarked_news.super.onBackPressed();
            }
        });
        loadbookmarks("%");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)findViewById(R.id.searchview);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                loadbookmarks(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                loadbookmarks(s);
                return false;
            }
        });

    }

    public void loadbookmarks(String keyword) {
        articles.clear();
        dbHelper=new DBHelper(this);
        Cursor cursor=dbHelper.getData(2);
        if(keyword=="%") {
            if (cursor.moveToFirst()) {
                do {
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));
                    String urlToImage = cursor.getString(cursor.getColumnIndex("urlToImage"));
                    String publishedAt = cursor.getString(cursor.getColumnIndex("publishedAt"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    String type=cursor.getString(cursor.getColumnIndex("type"));
                    if(type.equals("Bookmark")) {
                        articles.add(new Down_News_model(author, title, description, url, urlToImage, publishedAt, content, image, type));
                        bookmark_adapter = new Bookmarked_News_Adapter(Bookmarked_news.this, articles);
                    }
                } while (cursor.moveToNext());
            }
            try {
                recyclerView.setAdapter(bookmark_adapter);
                bookmark_adapter.notifyDataSetChanged();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

        else {
            if (cursor.moveToFirst()) {
                do {
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    String title = cursor.getString(cursor.getColumnIndex("title"));
                    String description = cursor.getString(cursor.getColumnIndex("description"));
                    String url = cursor.getString(cursor.getColumnIndex("url"));
                    String urlToImage = cursor.getString(cursor.getColumnIndex("urlToImage"));
                    String publishedAt = cursor.getString(cursor.getColumnIndex("publishedAt"));
                    String content = cursor.getString(cursor.getColumnIndex("content"));
                    String image = cursor.getString(cursor.getColumnIndex("image"));
                    String type=cursor.getString(cursor.getColumnIndex("type"));
                    if(type.equals("Bookmark")) {
                        if (title.contains(keyword) || description.contains(keyword)) {
                            articles.add(new Down_News_model(author, title, description, url, urlToImage, publishedAt, content, image, type));
                            bookmark_adapter = new Bookmarked_News_Adapter(Bookmarked_news.this, articles);
                        }
                    }
                }while (cursor.moveToNext());
            }
            recyclerView.setAdapter(bookmark_adapter);
            bookmark_adapter.notifyDataSetChanged();
        }
    }
}