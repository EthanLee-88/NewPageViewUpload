package com.example.jiangnan.newpageview.Data.SQLiteData;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jiangnan on 2018/5/10.
 */

public class SQLitePuter {
    public static final String DB_NAME = MySQLiteDataHelper.DB_NAME;
    public static SQLitePuter sqLitePuter;
    private SQLiteDatabase sqLiteDatabase;
    private static final Object lock = new Object();

    public SQLitePuter(Context context){
        MySQLiteDataHelper mySQLiteDataHelper = new MySQLiteDataHelper(context ,DB_NAME ,
                null , 1);
        try{
            sqLiteDatabase = mySQLiteDataHelper.getReadableDatabase();
        }catch (Exception e){
        }
    }
    public  SQLiteDatabase getSqLiteDatabase(){
        return sqLiteDatabase;
    }

    public void putDATA(){
        synchronized (lock){
            ContentValues contentValues = new ContentValues();
            contentValues.put(MySQLiteDataHelper.HISTORY_SONGSTER_ID , "莎士比亚");
            contentValues.put(MySQLiteDataHelper.HISTORY_SCORE , 99);
            contentValues.put(MySQLiteDataHelper.HISTORY_AD , 1666);
            contentValues.put(MySQLiteDataHelper.HISTORY_PAGE , 56);
            contentValues.put(MySQLiteDataHelper.HISTORY_TIME , "17世纪");

            sqLiteDatabase.insert(MySQLiteDataHelper.TABLE_NAME_HISTORY ,
                    null , contentValues);
        }
    }
    public void deleteData(){
        String deData = "DELETE FROM history WHERE score='99'";
        if(sqLiteDatabase != null){
            synchronized (lock){
                sqLiteDatabase.execSQL(deData);
            }
        }
    }
    public void closeDB(){
        sqLiteDatabase.close();
    }
}
