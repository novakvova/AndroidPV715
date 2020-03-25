package com.example.myapplication.userview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.example.myapplication.R;

public class UserCardViewHolder extends RecyclerView.ViewHolder {

    public NetworkImageView userImage;
    public TextView userName;
    public TextView userSurname;
    public TextView userCountry;
    public TextView userCity;
    public TextView userAge;
    public UserCardViewHolder(@NonNull View itemView) {
        super(itemView);
        userImage = itemView.findViewById(R.id.user_image);
        userName = itemView.findViewById(R.id.user_name);
        userSurname = itemView.findViewById(R.id.user_surname);
        userCountry = itemView.findViewById(R.id.user_country);
        userCity = itemView.findViewById(R.id.user_city);
        userAge = itemView.findViewById(R.id.user_age);
    }
}