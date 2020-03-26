package com.example.myapplication.utils.network;

import android.content.Context;
import android.util.Log;

import com.example.myapplication.application.MyApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {
    private final Context context;
    public ConnectivityInterceptor() {
        context = MyApplication.getAppContext();
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtils.isOnline(context)) {
            throw new NoConnectivityException();
        }
        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
