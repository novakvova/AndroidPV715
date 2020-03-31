package com.example.myapplication.productCreate.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductCreateResultDTO {
    @SerializedName("id")
    @Expose
    private int id;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}