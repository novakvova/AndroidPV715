package com.example.myapplication.jsonretro;


import com.example.myapplication.utils.network.ConnectivityInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserNetworkService {
    private static UserNetworkService mInstance;
    private static final String BASE_URL = "http://10.0.2.2/api/";
    private Retrofit mRetrofit;

    private UserNetworkService() {
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

    public static UserNetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new UserNetworkService();
        }
        return mInstance;
    }

    public UserHolderApi getJSONApi() {
        return mRetrofit.create(UserHolderApi.class);
    }
}
