package com.example.myapplication.productview.network;

import com.example.myapplication.productview.dto.ProductCreateDTO;
import com.example.myapplication.productview.dto.ProductCreateResultDTO;
import com.example.myapplication.productview.dto.ProductDTO;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ProductHolderApi {
    @GET("products")
    public Call<List<ProductDTO>> getAllProducts();

    @POST("products/create")
    public Call<ProductCreateResultDTO> CreateRequest(@Body ProductCreateDTO product);

    @DELETE("products/delete/{id}")
    public Call<ResponseBody> DeleteRequest(@Path("id") int id);

}
