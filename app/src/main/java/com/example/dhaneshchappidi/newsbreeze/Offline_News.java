package com.example.dhaneshchappidi.newsbreeze;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

public class Offline_News extends AppCompatActivity {
    TextView title,description,Date,Author,Content;
    Button more;
    ImageView newsimage,backbutton;
    String URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offline__news);
        newsimage=(ImageView)findViewById(R.id.image);
        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.desc);
        Date=(TextView)findViewById(R.id.date);
        backbutton=(ImageView) findViewById(R.id.back_button);
        Author=(TextView)findViewById(R.id.author);
        Content=(TextView)findViewById(R.id.content);
        more=(Button)findViewById(R.id.More);

        Intent intent = getIntent();
        final String Url = intent.getStringExtra("url");
        final String Img = intent.getStringExtra("img");
        String Title = intent.getStringExtra("title");
        String Descriptioni = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");
        final String author=intent.getStringExtra("author");
        final String content=intent.getStringExtra("content");
        URL=intent.getStringExtra("url");
        String image=intent.getStringExtra("image");
        title.setText(Title);
        Author.setText("-"+author);
        Content.setText(content);
        description.setText(Descriptioni);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Offline_News.super.onBackPressed();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(URL);
                Intent intent1=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent1);
            }
        });
        String date_formate=Dateformate.Dateformate(date);
        Date.setText(date_formate);
        File rootdata=getApplicationContext().getExternalFilesDir(null);
        final File name = new File(rootdata.toString()+"/"+ image + ".PNG");
        Bitmap bitmap= BitmapFactory.decodeFile(name.getAbsolutePath());
        newsimage.setImageBitmap(bitmap);
    }
}