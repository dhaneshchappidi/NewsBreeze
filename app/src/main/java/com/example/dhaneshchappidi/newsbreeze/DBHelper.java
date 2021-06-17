package com.example.dhaneshchappidi.newsbreeze;
import java.util.HashMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SavedNews.db";
    public static final String NEWS_TABLE_NAME = "savedlist";
    public static final String NEWS_COLUMN_ID = "id";
    public static final String NEWS_COLUMN_AUTHOR = "author";
    public static final String NEWS_COLUMN_TITLE = "title";
    public static final String NEWS_COLUMN_DESCRIPTION = "description";
    public static final String NEWS_COLUMN_URL = "url";
    public static final String NEWS_COLUMN_URLTOIMAGE = "urlToImage";
    public static final String NEWS_COLUMN_PUBLISHEDAT = "publishedAt";
    public static final String NEWS_COLUMN_CONTENT = "content";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table savedlist " +
                        "(id integer primary key, author text,title text, description text,url text,urlToImage text,publishedAt text,content text,image text,type text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS savedlist");
        onCreate(db);
    }

    public boolean insertNews (String author, String title, String description,String url,String urlToImage,String publishedAt,String content,String image,String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("author", author);
        contentValues.put("title", title);
        contentValues.put("description", description);
        contentValues.put("url", url);
        contentValues.put("urlToImage", urlToImage);
        contentValues.put("publishedAt", publishedAt);
        contentValues.put("content", content);
        contentValues.put("image",image);
        contentValues.put("type",type);
        db.insert("savedlist", null, contentValues);
        return true;
    }
    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from savedlist", null );
        return res;
    }
    public Integer deleteContact (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("savedlist",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }
}