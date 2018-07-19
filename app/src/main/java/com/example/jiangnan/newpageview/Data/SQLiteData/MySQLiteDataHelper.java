package com.example.jiangnan.newpageview.Data.SQLiteData;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jiangnan on 2018/5/10.
 */
/***
 *  SQLiteOpenHelper类，有覆盖继承两个方法onCreate() 、onUpgrade() ，一个构造器。
 *  onCreate()方法用于创建数据表，onUpgrade()用于更新数据库版本。构造器SQLiteOpenHelper
 *  用于传入数据库版本号，以及传入数据库名称以创建数据库。
 *
 *  开发中可通过SQLiteOpenHelper子类的getWritableDatabase()及getReadableDatabase()方法
 *  获取db以打开数据库。
 *
 *  getWritableDatabase() 以写的方式打开数据库，磁盘空间满时会出错。
 *  getReadableDatabase() 以读写的方式打开数据库，磁盘满时以只读的方式打开。
 *
 *  用 Database 的insert() 方法插入数据时，需要创建ContentValues接口对象
 *  可以用 Database 的 execSQL() 方法操作数据库，通过 SQL 语句操作数据表
 *  execSQL() 方法也用于创建数据表，传入的参数包括表名，自增益列名id，以及
 *  各列数据列名。
 *
 *  需要获取数据库的数据时，可以用 Database 的 rawQuery（）获取 Cursor 的对象
 *  rawQuery（）方法需要传入的SQL包含表名、数据列名、以及其他筛选参数。
 *  Cursor 对象获取后可直接取得数据表内的数据。
 *
 *  开发技巧，SQLiteOpenHelper 的子类只用于创建数据库(构造器内)、数据表和更新
 *  数据库版本，再另外创建一个 helper 用来统一获取 Database 并编写统一的增加和删
 *  减数据的方法。
 *
 * ***/
public class MySQLiteDataHelper extends SQLiteOpenHelper{
    public static final String DB_NAME = "poet_data";

    public static final String SUBJECTS_ID = "_id";
    public static final String SONGSTER_ID = "songster_id";

    public static final String TABLE_NAME_HISTORY = "history";//表名
    public static final String HISTORY_SUBJECTS_ID = SUBJECTS_ID;
    public static final String HISTORY_SONGSTER_ID = SONGSTER_ID;
    public static final String HISTORY_SCORE = "score";
    public static final String HISTORY_AD = "christian_era";
    public static final String HISTORY_PAGE = "page";
    public static final String HISTORY_TIME = "time";

    public static final String TABLE_NAME_GEOGRAPHY = "geography";
    public static final String GEOGRAPHY_SUBJECT_ID = SUBJECTS_ID;
    public static final String GEOGRAPHY_SONGSTER_ID = SONGSTER_ID;
    public static final String GEOGRAPHY_SCORE = "score";
    public static final String GEOGRAPHY_EREA = "area";
    public static final String GEOGRAPHY_BOOK_PAGE = "book_page";
    public static final String GEOGRAPHY_TIME = "time";

    private static final String CREATE_TABLE_HISTORY = "create table "
            + TABLE_NAME_HISTORY
            + "("
            + HISTORY_SUBJECTS_ID + " integer primary key autoincrement,"
            + HISTORY_SONGSTER_ID + " text,"
            + HISTORY_SCORE + " integer,"
            + HISTORY_AD + " integer,"
            + HISTORY_PAGE + " integer,"
            + HISTORY_TIME + " text"
            + ")";

    private static final String CREATE_TABLE_GEOGRAPHY = "create table "
            + TABLE_NAME_GEOGRAPHY
            + "("
            + GEOGRAPHY_SUBJECT_ID + " integer primary key autoincrement,"
            + GEOGRAPHY_SONGSTER_ID + " text,"
            + GEOGRAPHY_SCORE + " integer,"
            + GEOGRAPHY_EREA + " text,"
            + GEOGRAPHY_BOOK_PAGE + " integer,"
            + GEOGRAPHY_TIME + " text"
            + ")";


    public MySQLiteDataHelper(Context context , String dbName ,
                              SQLiteDatabase.CursorFactory factory , int version){
        super(context , dbName , factory , version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         sqLiteDatabase.execSQL(CREATE_TABLE_HISTORY);
         sqLiteDatabase.execSQL(CREATE_TABLE_GEOGRAPHY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
