package com.example.dhaneshchappidi.newsbreeze.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhaneshchappidi.newsbreeze.Dateformate;
import com.example.dhaneshchappidi.newsbreeze.R;
import com.example.dhaneshchappidi.newsbreeze.model.News;
import com.example.dhaneshchappidi.newsbreeze.view.Offline_News;

import java.io.File;
import java.util.List;

public class Downloaded_Adapter extends RecyclerView.Adapter<Downloaded_Adapter.myHolder> {
    Context context;
    List<News> downloaded_news;
    public Downloaded_Adapter(Context context, List<News> downloaded_news){
        this.context=context;
        this.downloaded_news=downloaded_news;
    }

    @NonNull
    @Override
    public Downloaded_Adapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        final String News_title=downloaded_news.get(position).getTitle();
        final String author = downloaded_news.get(position).getAuthor();
        final String description = downloaded_news.get(position).getDescription();
        final String url = downloaded_news.get(position).getUrl();
        final String urlToImage = downloaded_news.get(position).getUrlToImage();
        final String publishedAt = downloaded_news.get(position).getPublishedAt();
        final String content = downloaded_news.get(position).getContent();
        final String image=downloaded_news.get(position).getImage();
        File rootdata=context.getApplicationContext().getExternalFilesDir(null);
        final File name = new File(rootdata.toString()+"/"+ image + ".PNG");
        final String type=downloaded_news.get(position).getType();
        String date_formate= Dateformate.Dateformate(publishedAt);
        holder.Title.setText(News_title);
        holder.Time.setText(date_formate);
        Bitmap bitmap= BitmapFactory.decodeFile(name.getAbsolutePath());
        holder.NewsImage.setImageBitmap(bitmap);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Offline_News.class);
                intent.putExtra("url", url);
                intent.putExtra("title", News_title);
                intent.putExtra("description",description);
                intent.putExtra("img",  urlToImage);
                intent.putExtra("date",  publishedAt);
                intent.putExtra("author",author);
                intent.putExtra("content",content);
                intent.putExtra("image",image);
                intent.putExtra("type",type);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return downloaded_news.size();
    }
    class myHolder extends RecyclerView.ViewHolder{
        ImageView NewsImage;
        TextView Title,Time;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            NewsImage=itemView.findViewById(R.id.newsimage);
            Title=itemView.findViewById(R.id.title);
            Time=itemView.findViewById(R.id.time);
        }
    }
}
