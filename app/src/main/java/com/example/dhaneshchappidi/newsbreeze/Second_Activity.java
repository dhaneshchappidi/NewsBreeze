package com.example.dhaneshchappidi.newsbreeze;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class Second_Activity extends AppCompatActivity {
    ImageView newsimage,Share;
    TextView title,description,Date,Author,Content;
    ImageView backbutton;
    Button more,Save;
    String URL;

    private static final int PERMISSION_REQEST_CODE =1 ;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
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
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        final String Url = intent.getStringExtra("url");
        final String Img = intent.getStringExtra("img");
        final String Title = intent.getStringExtra("title");
        String Descriptioni = intent.getStringExtra("description");
        String date = intent.getStringExtra("date");
        final String author=intent.getStringExtra("author");
        final String content=intent.getStringExtra("content");
        URL=intent.getStringExtra("url");
        more=(Button)findViewById(R.id.More);
        Share=(ImageView)findViewById(R.id.share_img);
        newsimage=(ImageView)findViewById(R.id.image);
        title=(TextView)findViewById(R.id.title);
        description=(TextView)findViewById(R.id.desc);
        Save=(Button)findViewById(R.id.save);
        Date=(TextView)findViewById(R.id.date);
        backbutton=(ImageView) findViewById(R.id.back_button);
        Author=(TextView)findViewById(R.id.author);
        Content=(TextView)findViewById(R.id.content);
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse(URL);
                Intent intent1=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent1);
            }
        });
        Share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.EXTRA_SUBJECT,Title);
                String body=Url+"\n" +"-NewsBreeze"+"\n";
                startActivity(Intent.createChooser(i,"Share with:"));
            }
        });
        title.setText(Title);
        Author.setText("-"+author);
        Content.setText(content);
        description.setText(Descriptioni);
        Glide.with(this)
                .load(Img)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(newsimage);
        String date_formate=Dateformate.Dateformate(date);
        Date.setText(date_formate);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Second_Activity.super.onBackPressed();
            }
        });
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Second_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },PERMISSION_REQEST_CODE);

                }
                Picasso.with(getApplicationContext())
                        .load(Img)
                        .into(new Target() {
                                  @Override
                                  public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                      File rootdata=getApplicationContext().getExternalFilesDir(null);
                                      rootdata.mkdirs();
                                      String date_time=new SimpleDateFormat("YYYYmmdd_HHmmss", Locale.getDefault())
                                              .format(System.currentTimeMillis());
                                      String name = date_time + ".PNG";
                                      File file = new File(rootdata, name);
                                      OutputStream out;
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
}