package com.example.myapplication.productview;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.BaseActivity;

import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.network.ProductEntry;

public class ProductEditActivity extends BaseActivity {

    private ProductEntry productEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);
        initPerson();

    }

    private void initPerson() {
        Intent intent = getIntent();
        if(intent != null){
            int id = intent.getIntExtra(ConstantIds.PRODUCT_INTENT_OBJECT,0);
            Toast.makeText(this, "Hello"+id, Toast.LENGTH_LONG);


        }
    }

}
