package com.example.myapplication.jsonretro;


import com.example.myapplication.utils.network.ConnectivityInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdNetworkService {
    private static ProdNetworkService mInstance;
    private static final String BASE_URL = "https://masterlock20200324083512.azurewebsites.net/api/";
    private Retrofit mRetrofit;

    private ProdNetworkService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor())
                .addInterceptor(interceptor);

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
