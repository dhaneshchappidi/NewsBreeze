package com.example.dhaneshchappidi.newsbreeze.adapter;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhaneshchappidi.newsbreeze.Dateformate;
import com.example.dhaneshchappidi.newsbreeze.R;
import com.example.dhaneshchappidi.newsbreeze.model.Article;
import com.example.dhaneshchappidi.newsbreeze.model.News;
import com.example.dhaneshchappidi.newsbreeze.viewmodel.ViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    List<Article> articles;
    Context context;
    Integer total;
    OnItemClickListener onItemClickListener;
    public List<Article> saved;
    private static final int PERMISSION_REQEST_CODE =1 ;
    Application application;

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new MyViewHolder(view, onItemClickListener);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_REQEST_CODE:
            {
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(context,"permission granted",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(context,"permission denied",Toast.LENGTH_LONG).show();

                }
            }
            break;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holders, final int position) {
        final MyViewHolder holder = holders;
        final Article model = articles.get(position);
        String picture=model.getUrlToImage();
        try {
            Picasso.with(holder.newsimage.getContext())
                    .load(model.getUrlToImage())
                    .into(holder.newsimage);
            holder.progressBar.setVisibility(View.GONE);
        }
        catch (Exception e){}
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        String original_time= Dateformate.Dateformate(model.getPublishedAt());
        holder.time.setText(original_time);
        holder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    holder.bookmark.setBackgroundResource(R.drawable.bookmarked);
                News news=new News(model.getAuthor(),model.getTitle(),model.getDescription(),model.getUrl(),model.getUrlToImage(),model.getPublishedAt(),model.getContent(),null,"Bookmark");
                holder.newsViewMode.insert(news);
                Toast.makeText(context,"Added successfully",Toast.LENGTH_LONG).show();

            }
        });
        holder.Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.EXTRA_SUBJECT,model.getTitle());
                String body=model.getUrl()+"\n" +"-NewsBreeze"+"\n";
                i.putExtra(Intent.EXTRA_TEXT,body);
                context.startActivity(Intent.createChooser(i,"Share with:"));
            }
        });
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions((AppCompatActivity)context,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                    },PERMISSION_REQEST_CODE);
                }
                Picasso.with(context)
                        .load(model.getUrlToImage())
                        .into(new com.squareup.picasso.Target() {
                                  @Override
                                  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                      File rootdata=context.getApplicationContext().getExternalFilesDir(null);
                                      rootdata.mkdirs();
                                      String date_time=new SimpleDateFormat("YYYYmmdd_HHmmss", Locale.getDefault())
                                              .format(System.currentTimeMillis());
                                      String name = date_time + ".PNG";
                                      File file = new File(rootdata, name);
                                      OutputStream out;
                                      News news=new News(model.getAuthor(),model.getTitle(),model.getDescription(),model.getUrl(),model.getUrlToImage(),model.getPublishedAt(),model.getContent(),date_time,"Download");
                                      holder.newsViewMode.insert(news);
                                      Toast.makeText(context,"News downloaded",Toast.LENGTH_LONG).show();
                                      try {
                                          out=new FileOutputStream(file);
                                          bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                          out.flush();
                                          out.close();
                                      }
                                      catch (Exception ex){
                                          ex.printStackTrace();
                                      }
                                  }
                                  @Override
                                  public void onBitmapFailed(Drawable errorDrawable) {
                                  }

                                  @Override
                                  public void onPrepareLoad(Drawable placeHolderDrawable) {
                                  }
                              }
                        );
                }



        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title, description,time;
        ImageView newsimage,Share;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;
        Button save;
        ImageButton bookmark;
        ViewModel newsViewMode;
        public MyViewHolder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.desc);
            time=itemView.findViewById(R.id.time);
            newsimage=itemView.findViewById(R.id.image);
            progressBar=itemView.findViewById(R.id.progressbar);
            bookmark=itemView.findViewById(R.id.save);
            save=itemView.findViewById(R.id.savetomemory);
            this.onItemClickListener=onItemClickListener;
            Share=itemView.findViewById(R.id.share_img);
            newsViewMode= ViewModelProviders.of((FragmentActivity) context).get(ViewModel.class);
        }

        @Override
        public void onClick(View view) {
                onItemClickListener.onItemClick(view,getAdapterPosition());
        }
    }
}
