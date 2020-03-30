package com.example.myapplication.jsonretro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserHolderApi {
    @GET("user")
    public Call<List<UserDTO>> getAllUsers();

}
