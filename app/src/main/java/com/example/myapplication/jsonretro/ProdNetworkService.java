package com.example.myapplication.jsonretro;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProdNetworkService {
    private static ProdNetworkService mInstance;
    private static final String BASE_URL = "http://10.0.2.2/api";
    private Retrofit mRetrofit;

    private ProdNetworkService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
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
