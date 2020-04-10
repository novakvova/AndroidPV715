package com.example.myapplication.productview.network;


import android.content.Context;

import com.example.myapplication.account.JwtServiceHolder;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.network.interceptors.AuthorizationInterceptor;
import com.example.myapplication.network.interceptors.ConnectivityInterceptor;
import com.example.myapplication.network.interceptors.JWTInterceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductNetworkService {
    private final Context context;
    private static ProductNetworkService mInstance;
    private static final String BASE_URL = "https://masterlockbest.azurewebsites.net/api/";//"http://10.0.2.2/api/";
    private Retrofit mRetrofit;

    private ProductNetworkService() {
        context = MyApplication.getAppContext();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);



        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new ConnectivityInterceptor())
                .addInterceptor(new AuthorizationInterceptor())
                .addInterceptor(new JWTInterceptor())
                .addInterceptor(interceptor);


//        OkHttpClient.Builder client = new OkHttpClient.Builder()
//                .addInterceptor(new ConnectivityInterceptor())
//                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static ProductNetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new ProductNetworkService();
        }
        return mInstance;
    }

    public ProductHolderApi getJSONApi() {
        return mRetrofit.create(ProductHolderApi.class);
    }
}
