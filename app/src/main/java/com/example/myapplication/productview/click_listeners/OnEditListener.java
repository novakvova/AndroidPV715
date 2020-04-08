package com.example.myapplication.productview.click_listeners;

import com.example.myapplication.network.ProductEntry;

public interface OnEditListener {
    void editItem(ProductEntry productEntry, int index);
}