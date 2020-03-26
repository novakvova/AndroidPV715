package com.example.myapplication.utils.network;

import androidx.fragment.app.Fragment;

public interface RequestErrorNavigate {
    void navigateErrorPage(Fragment callBackfragment, boolean addToFackstack, String errorStr);
    void returnRefreshPage();
}
