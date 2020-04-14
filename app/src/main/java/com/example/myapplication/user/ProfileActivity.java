package com.example.myapplication.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.R;
import com.example.myapplication.jsonretro.UserDTO;
import com.example.myapplication.network.ImageRequester;
import com.example.myapplication.user.api.UserDTOService;
import com.example.myapplication.user.dto.UserProfileDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private ImageRequester imageRequester;
    private NetworkImageView userPhoto;
    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private TextView userContentName;
    private TextView userContentEmail;
    private TextView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        userPhoto = findViewById(R.id.userPhoto);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPhone = findViewById(R.id.userPhone);
        userContentName= findViewById(R.id.userContentName);
        userContentEmail=findViewById(R.id.userContentEmail);
        imageRequester = ImageRequester.getInstance();
        btnBack = findViewById(R.id.btnBack);
        setButtonBackListener();
        getUser();
    }
    private void setButtonBackListener() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
    public void getUser() {
        UserDTOService.getInstance()
                .getJSONApi()
                .getUserProfile()
                .enqueue(new Callback<UserProfileDTO>() {
                    @Override
                    public void onResponse(@NonNull Call<UserProfileDTO> call, @NonNull Response<UserProfileDTO> response) {
                        if (response.isSuccessful()) {
                            UserProfileDTO userDTO= response.body();
                            userName.setText(userDTO.getName());
                            userEmail.setText(userDTO.getEmail());
                            userPhone.setText(userDTO.getPhone());
                            userContentName.setText(userDTO.getName());
                            userContentEmail.setText(userDTO.getEmail());
                            imageRequester.setImageFromUrl(userPhoto, userDTO.getUrl());
                        } else {
                            //  Log.e(TAG, "_______________________" + response.errorBody().charStream());
                            try {
//                                                String json = response.errorBody().string();
//                                                Gson gson  = new Gson();
//                                                ProductCreateInvalidDTO resultBad = gson.fromJson(json, ProductCreateInvalidDTO.class);
                                //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                //errormessage.setText(resultBad.getInvalid());
                            } catch (Exception e) {
                                //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<UserProfileDTO> call, @NonNull Throwable t) {
                        Log.e("ERROR", "*************ERORR request***********");
                        t.printStackTrace();
                    }
                });
    }
}

