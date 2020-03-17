package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    public void onClickBegin(View view) {

        EditText editText = findViewById(R.id.txtInfo);
        String text = editText.getText().toString();

        TextView textView = findViewById(R.id.txtViewMessage);
        textView.setText(text);

        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
}
