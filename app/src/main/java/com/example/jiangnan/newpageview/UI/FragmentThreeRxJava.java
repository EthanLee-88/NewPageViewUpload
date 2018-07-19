package com.example.jiangnan.newpageview.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jiangnan.newpageview.OkHttp.OkHttp;
import com.example.jiangnan.newpageview.R;
import com.example.jiangnan.newpageview.Retrofit.GetRequest_Interface;
import com.example.jiangnan.newpageview.Retrofit.GetRetrofitUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by jiangnan on 2017/12/7.
 */

public class FragmentThreeRxJava extends Fragment{
    private TextView mtextView;
    private Button mButton;
    private static final String  fUrl = "TF02/V2/help/answer/list";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three , container , false);
        mtextView = (TextView) view.findViewById(R.id.data_show);
        mButton = (Button) view.findViewById(R.id.data_updata);
        dataDownLoad(fUrl);
        upDate();
        return view;
    }
    private void upDate() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataDownLoad(fUrl);
            }
        });
    }
    /************************************* RxJava及Retrofit实现网络加载 **************************/
    private void dataDownLoad(final String url) {
        //步骤4:创建Retrofit接口对象
        final GetRequest_Interface retrofitInterface = GetRetrofitUtil.getRetrofit();

        OkHttp okHttp = new OkHttp(getActivity());
        final OkHttpClient MokHttpClient = okHttp.getOkHttpClient();
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String result = null;

                Request request = new Request.Builder()
                        .url("http://www.baidu.com").build();
                result = MokHttpClient.newCall(request).execute().body().string();

//                result = retrofitInterface.getCall(url).execute().body().string();
                 Log.d("OkHttp数据组合" , result + "\n当前线程=" +  Thread.currentThread());
                e.onNext(result);
                e.onComplete();
                }
            }).subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("retrofit" , "回调" );
                    }
                    @Override
                    public void onNext(String backCode) {
                        Log.d("retrofit" , "下一步" );
                        mtextView.setText(backCode);
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.d("retrofit" , "错误" );
                    }
                    @Override
                    public void onComplete() {
                        Log.d("retrofit" , "完成" );
                    }
                });
            }
}
