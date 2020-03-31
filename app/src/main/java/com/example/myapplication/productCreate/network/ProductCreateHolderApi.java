package com.example.myapplication.productCreate.network;

import com.example.myapplication.account.LoginDTO;
import com.example.myapplication.account.TokenDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ProductCreateHolderApi {
    @POST("create")
    public Call<ProductCreateResultDTO> CreateRequest(@Body ProductCreateDTO product);
}
