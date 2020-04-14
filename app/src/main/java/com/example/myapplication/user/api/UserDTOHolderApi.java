package com.example.myapplication.user.api;
import com.example.myapplication.user.dto.UserProfileDTO;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserDTOHolderApi {
    @GET("account/profile")
    public Call<UserProfileDTO> getUserProfile();

}
