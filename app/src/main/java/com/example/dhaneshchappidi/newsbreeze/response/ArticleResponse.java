package com.example.dhaneshchappidi.newsbreeze.response;

import com.example.dhaneshchappidi.newsbreeze.model.Article;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {
    @SerializedName("articles")
    @Expose
    List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
