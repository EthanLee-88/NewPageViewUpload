package com.example.jiangnan.newpageview.UI;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jiangnan.newpageview.Data.SQLiteData.MySQLiteDataHelper;
import com.example.jiangnan.newpageview.Data.SQLiteData.SQLitePuter;
import com.example.jiangnan.newpageview.R;

import static com.example.jiangnan.newpageview.Data.SQLiteData.MySQLiteDataHelper.DB_NAME;

/**
 * Created by jiangnan on 2017/12/7.
 */

public class FragmentFour extends Fragment{
    private Button mButton , deButton , getButton;
    private TextView getTextView;
    private static final Object lock = new Object();
    private SQLitePuter sqLitePuter;
    private SQLiteDatabase db = null;

    @Override
    public void onDestroy() {
        super.onDestroy();
        sqLitePuter.closeDB();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_four , container , false);
        intRes(view);
        return view;
    }

    private void intRes(View view){
        mButton = (Button) view.findViewById(R.id.save_data);
        deButton = view.findViewById(R.id.dele_data);
        getButton = view.findViewById(R.id.get_data);
        getTextView = view.findViewById(R.id.show_data);

        sqLitePuter = new SQLitePuter(getActivity());
        db = sqLitePuter.getSqLiteDatabase();
        setOnListener();
    }
    private void setOnListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
        deButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleData();
            }
        });
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
    }
    private void saveData(){

        synchronized (lock){
            db.beginTransaction();
            try{
                sqLitePuter.putDATA();
                db.setTransactionSuccessful();
            }catch (Exception e){

            }finally {
               db.endTransaction();
            }
        }
    }

    private void deleData(){

        synchronized (lock){
            db.beginTransaction();
            try{
                sqLitePuter.deleteData();
                db.setTransactionSuccessful();
            }catch (Exception e){}finally {
                db.endTransaction();
            }
        }
    }
    private void getData(){
        String sql = "SELECT songster_id,time FROM history WHERE page='56' ORDER BY time DESC LIMIT 1";
//                     "SELECT HR,_date FROM ECG WHERE User_CardID='350429196011162235' ORDER BY _date DESC LIMIT 1"
        Cursor cursor = null;
        try{
            db = sqLitePuter.getSqLiteDatabase();

            cursor = db.rawQuery(sql , null);

            if(cursor.moveToFirst()){  /******************/
                String singger = cursor.getString(cursor.getColumnIndex(MySQLiteDataHelper.SONGSTER_ID));
                int time = cursor.getInt(cursor.getColumnIndex(MySQLiteDataHelper.HISTORY_TIME));
                getTextView.setText(singger + time);
            }
        }catch (Exception e){

        }
    }
}
