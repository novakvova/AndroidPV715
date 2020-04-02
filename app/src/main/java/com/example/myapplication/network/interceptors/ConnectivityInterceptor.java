package com.example.myapplication.network.interceptors;

import android.content.Context;

import com.example.myapplication.ConnectionInternetError;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.utils.network.NetworkUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Context context= MyApplication.getAppContext();
        Request originalRequest = chain.request();

        if (!NetworkUtils.isOnline(context)) {
            MyApplication beginApplication = (MyApplication) context;
            ((ConnectionInternetError) beginApplication.getCurrentActivity()).navigateErrorPage();
        }
        Request newRequest = originalRequest.newBuilder()
                .build();
        return chain.proceed(newRequest);
    }
}
