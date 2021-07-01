package com.example.dhaneshchappidi.newsbreeze.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dhaneshchappidi.newsbreeze.adapter.NewsAdapter;
import com.example.dhaneshchappidi.newsbreeze.model.Article;
import com.example.dhaneshchappidi.newsbreeze.R;
import com.example.dhaneshchappidi.newsbreeze.response.ArticleResponse;
import com.example.dhaneshchappidi.newsbreeze.viewmodel.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int PERMISSION_REQEST_CODE =1 ;
    Typeface tf;
    TextView title;
    SearchView searchView;
    ImageView Bookmark,download;
    private NewsAdapter newsAdapter;
    ProgressBar progressBar;
    public static final String API_KEY = "e4f293b3fbed47b4a376cb44d5b654ce";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private String TAG = MainActivity.class.getSimpleName();
    Spinner Spinner;
    String[] category = {"business","entertainment","general","health","scince","sports","technology"};

    ViewModel articleViewMode;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
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
        download=(ImageView)findViewById(R.id.downloaded);
        tf=Typeface.createFromAsset(getAssets(), "METALORD.TTF");
        title.setTypeface(tf);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        Spinner=(Spinner)findViewById(R.id.spinner);
        Spinner.setOnItemSelectedListener(this);
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,category);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(aa);
        articleViewMode= ViewModelProviders.of(this).get(ViewModel.class);

        Bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, Bookmarked_news.class);
                startActivity(intent);
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this, Downloaded_News.class);
                startActivity(i);
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView=(SearchView)findViewById(R.id.searchview);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                LoadJson(s,Spinner.getSelectedItem().toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                LoadJson(s,Spinner.getSelectedItem().toString());
                return false;
            }
        });
        recyclerView = findViewById(R.id.recyclerview);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        LoadJson("",Spinner.getSelectedItem().toString());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            },PERMISSION_REQEST_CODE);
        }
    }
    public void LoadJson(String keyword,String category){
            articleViewMode.getArticleResponseLiveData(keyword,"in",API_KEY,category,"publishedAt").observe(this, new Observer<ArticleResponse>() {
                @Override
                public void onChanged(ArticleResponse articleResponse) {
                    progressBar.setVisibility(View.GONE);
                    articles.clear();
                    if (articleResponse != null && articleResponse.getArticles() != null
                            && !articleResponse.getArticles().isEmpty()) {
                        List<Article> articleList = articleResponse.getArticles();
                        articles.addAll(articleList);
                        newsAdapter = new NewsAdapter(articles, MainActivity.this);
                        newsAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(newsAdapter);
                        Clickevent();
                    }
                }
            });
    }
    private void Clickevent(){
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        progressBar.setVisibility(View.VISIBLE);
        Toast.makeText(MainActivity.this,category[i]+ " NEWS",Toast.LENGTH_SHORT).show();
        LoadJson("",category[i]);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}