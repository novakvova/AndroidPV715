package com.example.myapplication.userview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.network.ImageRequester;
import com.example.myapplication.network.UserEntry;
import com.example.myapplication.userview.UserCardViewHolder;

import java.util.List;

/**
 * Adapter used to show a simple grid of products.
 */
public class UserCardRecyclerViewAdapter extends RecyclerView.Adapter<UserCardViewHolder> {

    private List<UserEntry> userList;
    private ImageRequester imageRequester;

    UserCardRecyclerViewAdapter(List<UserEntry> userList) {
        this.userList = userList;
        imageRequester = ImageRequester.getInstance();
    }

    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {
        if (userList != null && position < userList.size()) {
            UserEntry user = userList.get(position);
            holder.userName.setText(user.name);
            holder.userSurname.setText(user.surname);
            holder.userCountry.setText(user.country);
            holder.userCity.setText(user.city);
            holder.userAge.setText(user.age);
            imageRequester.setImageFromUrl(holder.userImage, user.url);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
