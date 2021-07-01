package com.example.dhaneshchappidi.newsbreeze.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhaneshchappidi.newsbreeze.Dateformate;
import com.example.dhaneshchappidi.newsbreeze.R;
import com.example.dhaneshchappidi.newsbreeze.model.News;
import com.example.dhaneshchappidi.newsbreeze.view.Second_Activity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Bookmarked_News_Adapter extends RecyclerView.Adapter<Bookmarked_News_Adapter.myHolder> {
    Context context;
    List<News> bookmarked;
    public Bookmarked_News_Adapter(Context context, List<News> bookmarked){
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

            Picasso.with(context)
                    .load(urlToImage)
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
