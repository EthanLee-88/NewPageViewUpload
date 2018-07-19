package com.example.jiangnan.newpageview.OkHttp;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by jiangnan on 2018/4/27.
 */

public class OkHttp {
    private static volatile OkHttpClient mOkHttpClient;

    private static Context Mcontext;

    public OkHttp(Context context){
       Mcontext = context;
    }
    private static final Interceptor cacheControlInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (true) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response originalResponse = chain.proceed(request);
            if (true) {
                // 有网络时 设置缓存为默认值
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                // 无网络时 设置超时为1周
                int maxStale = 60 * 60 * 24 * 7;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    };


    public static OkHttpClient getOkHttpClient(){

        File cacheDir = new File(Mcontext.getExternalCacheDir() , "cache");
//File cacheDir = new File(getExternalCacheDir(), "okhttp");
        Cache cache = new Cache(cacheDir, 10 * 1024 * 1024);

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5 * 1000 , TimeUnit.MICROSECONDS)
                .readTimeout(10 * 1000 , TimeUnit.MICROSECONDS)
                .writeTimeout(10 * 1000 , TimeUnit.MICROSECONDS)
//                .addInterceptor(cacheControlInterceptor)
//                .addInterceptor(new HttpLoggingInterceptor())
                .retryOnConnectionFailure(true)
                .build();

        return mOkHttpClient;
    }
}
