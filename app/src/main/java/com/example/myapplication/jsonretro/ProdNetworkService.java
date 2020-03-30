package com.example.myapplication.jsonretro;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.application.MyApplication;
import com.example.myapplication.utils.network.ConnectivityInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdNetworkService {
    private final Context context;
    private static ProdNetworkService mInstance;
    private static final String BASE_URL = "https://masterlock20200324083512.azurewebsites.net/api/";
    private Retrofit mRetrofit;

    private ProdNetworkService() {
        context = MyApplication.getAppContext();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        SharedPreferences prefs=context.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        final String token = "Bearer "+ prefs.getString("token","");

        Interceptor interJWT = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", token)
                        .build();
                return chain.proceed(newRequest);
            }
        };

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interJWT)
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

    public static ProdNetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new ProdNetworkService();
        }
        return mInstance;
    }

    public ProductHolderApi getJSONApi() {
        return mRetrofit.create(ProductHolderApi.class);
    }
}
