package com.example.dhaneshchappidi.newsbreeze.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.dhaneshchappidi.newsbreeze.Dateformate;
import com.example.dhaneshchappidi.newsbreeze.Offline_News;
import com.example.dhaneshchappidi.newsbreeze.R;

import com.example.dhaneshchappidi.newsbreeze.Second_Activity;
import com.example.dhaneshchappidi.newsbreeze.model.Down_News_model;

import java.io.File;
import java.util.List;

public class Bookmarked_News_Adapter extends RecyclerView.Adapter<Bookmarked_News_Adapter.myHolder> {
    Context context;
    List<Down_News_model> bookmarked;
    public Bookmarked_News_Adapter(Context context, List<Down_News_model> bookmarked){
        this.context=context;
        this.bookmarked=bookmarked;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        final String News_title=bookmarked.get(position).getTitle();
        final String author = bookmarked.get(position).getAuthor();
        final String description = bookmarked.get(position).getDescription();
        final String url = bookmarked.get(position).getUrl();
        final String urlToImage = bookmarked.get(position).getUrlToImage();
        final String publishedAt = bookmarked.get(position).getPublishedAt();
        final String content = bookmarked.get(position).getContent();
        final String image=bookmarked.get(position).getImage();
        final String type=bookmarked.get(position).getType();
        String date_formate= Dateformate.Dateformate(publishedAt);
        holder.Title.setText(News_title);
        holder.Time.setText(date_formate);
        holder.progressBar.setVisibility(View.GONE);
        holder.Save.setVisibility(View.GONE);
        try {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.centerCrop();

            Glide.with(context)
                    .load(urlToImage)
                    .apply(requestOptions)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(holder.NewsImage);
        }
        catch (Exception e){}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Second_Activity.class);
                intent.putExtra("url", url);
                intent.putExtra("title", News_title);
                intent.putExtra("description",description);
                intent.putExtra("img",  urlToImage);
                intent.putExtra("date",  publishedAt);
                intent.putExtra("author",author);
                intent.putExtra("content",content);
                context.startActivity(intent);
            }
        });
        holder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.EXTRA_SUBJECT,News_title);
                String body=url+"\n" +"-NewsBreeze"+"\n";
                context.startActivity(Intent.createChooser(i,"Share with:"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookmarked.size();
    }
    class myHolder extends RecyclerView.ViewHolder{
        ImageView NewsImage,Save,Share;
        TextView Title,Time;
        ProgressBar progressBar;
        public myHolder(@NonNull View itemView) {
            super(itemView);
            NewsImage=itemView.findViewById(R.id.image);
            Title=itemView.findViewById(R.id.title);
            Time=itemView.findViewById(R.id.time);
            progressBar=itemView.findViewById(R.id.progressbar);
            Save=itemView.findViewById(R.id.save);
            Share=itemView.findViewById(R.id.share_img);
        }
    }
}
