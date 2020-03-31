package com.example.myapplication.productCreate.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.myapplication.application.MyApplication;
import com.example.myapplication.jsonretro.ProdNetworkService;
import com.example.myapplication.jsonretro.ProductHolderApi;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductCreateService {
    private final Context context;
    private static ProductCreateService mInstance;
    private static final String BASE_URL = "https://masterlock20200324083512.azurewebsites.net/api/products/";
    private Retrofit mRetrofit;

    private ProductCreateService() {
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

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static ProductCreateService getInstance() {
        if (mInstance == null) {
            mInstance = new ProductCreateService();
        }
        return mInstance;
    }

    public ProductCreateHolderApi getJSONApi() {
        return mRetrofit.create(ProductCreateHolderApi.class);
    }
}
