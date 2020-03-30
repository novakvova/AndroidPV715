package com.example.myapplication.account;

public interface JwtServiceHolder {
    void SaveJWTToken(String token);
    String getToken();
}
