package com.example.myapplication.jsonretro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductHolderApi {
    @GET("/products")
    public Call<List<ProductDTO>> getAllProducts();

}
