package com.example.jiangnan.newpageview.Retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jiangnan on 2018/4/24.
 */

public class GetRetrofitUtil {
  private volatile static Retrofit retrofit;
  private volatile static GetRequest_Interface getRequest_interface;

    public static GetRequest_Interface getRetrofit(){
        String url = "https://hfmeditech.com:8070/TF02/V2/help/answer/list";
        return getRetrofit(url);
    }

    public static GetRequest_Interface getRetrofit(String url){
         retrofit = new Retrofit.Builder()
                .baseUrl("https://hfmeditech.com:8070/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();
        getRequest_interface = retrofit.create(GetRequest_Interface.class);
        return getRequest_interface;
    }
}
