package com.example.dhaneshchappidi.newsbreeze.Adapter;

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
import com.example.dhaneshchappidi.newsbreeze.Offline_News;
import com.example.dhaneshchappidi.newsbreeze.R;

import com.example.dhaneshchappidi.newsbreeze.model.Down_News_model;

import java.io.File;
import java.util.List;

public class Downloaded_Adapter extends RecyclerView.Adapter<Downloaded_Adapter.myHolder> {
    Context context;
    List<Down_News_model> bookmarked;
    public Downloaded_Adapter(Context context, List<Down_News_model> bookmarked){
        this.context=context;
        this.bookmarked=bookmarked;
    }

    @NonNull
    @Override
    public Downloaded_Adapter.myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_saved, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Downloaded_Adapter.myHolder holder, int position) {
        final String News_title=bookmarked.get(position).getTitle();
        final String author = bookmarked.get(position).getAuthor();
        final String description = bookmarked.get(position).getDescription();
        final String url = bookmarked.get(position).getUrl();
        final String urlToImage = bookmarked.get(position).getUrlToImage();
        final String publishedAt = bookmarked.get(position).getPublishedAt();
        final String content = bookmarked.get(position).getContent();
        final String image=bookmarked.get(position).getImage();
        File rootdata=context.getApplicationContext().getExternalFilesDir(null);
        final File name = new File(rootdata.toString()+"/"+ image + ".PNG");
        final String type=bookmarked.get(position).getType();
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
        return bookmarked.size();
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
