package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.account.JwtServiceHolder;
import com.example.myapplication.productview.ProductGridFragment;
import com.example.myapplication.userview.UserGridFragment;
import com.example.myapplication.utils.network.RequestErrorNavigate;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            String token = this.getToken();
            if (token != null || token.equals("")) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new ProductGridFragment())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.container, new LoginFragment())
                        .commit();
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.register:
                this.navigateTo(new RegisterFragment(), false);
                return true;
            case R.id.comands:
                this.navigateTo(new ListViewSimpleFragment(), false);
                return true;
            case R.id.customListView:
                this.navigateTo(new CustomListViewFragment(), false);
                return true;
            case R.id.users:
                this.navigateTo(new UserGridFragment(), false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


}
