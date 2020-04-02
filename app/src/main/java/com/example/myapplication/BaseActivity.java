package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.account.JwtServiceHolder;
import com.example.myapplication.application.MyApplication;
import com.example.myapplication.utils.network.RequestErrorNavigate;

public abstract class BaseActivity  extends AppCompatActivity implements NavigationHost,
         JwtServiceHolder, ConnectionInternetError {

    private Fragment callbackFragment;
    protected Fragment currentFragment;

    public BaseActivity() {
        MyApplication myApp=(MyApplication)MyApplication.getAppContext();
        myApp.setCurrentActivity(this);
    }

    @Override
    public void saveJWTToken(String token) {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.putString("token",token);
            Log.i("Login",token);
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getToken() {
        SharedPreferences prefs=this.getSharedPreferences("jwtStore",Context.MODE_PRIVATE);
        String token = prefs.getString("token","");
        return token;
    }

    @Override
    public void removeToken() {
        SharedPreferences prefs;
        SharedPreferences.Editor edit;
        prefs=this.getSharedPreferences("jwtStore", Context.MODE_PRIVATE);
        edit=prefs.edit();
        try {
            edit.remove("token");
            edit.apply();
            edit.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        this.currentFragment = fragment;
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, this.currentFragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public void navigateErrorPage() {
        this.callbackFragment=currentFragment;
        this.navigateTo(new ConnectionInternetErrorFragment(), true);
    }

    @Override
    public void refreshCurrentErrorPage() {
        this.navigateTo(this.callbackFragment, true);
    }
}
