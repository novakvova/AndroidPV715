package com.example.myapplication.network;

import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class UserEntry {
    private static final String TAG = UserEntry.class.getSimpleName();

    public final String name;
    public final Uri dynamicUrl;
    public final String url;
    public final String surname;
    public final String country;
    public final String city;
    public final String age;

    public UserEntry(String name, String dynamicUrl, String url, String surname, String country, String city, String age) {
        this.name = name;
        this.dynamicUrl = Uri.parse(dynamicUrl);
        this.url = url;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.age = age;
    }

    public static List<UserEntry> initUserEntryList(Resources resources) {

        InputStream inputStream = resources.openRawResource(R.raw.users);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            int pointer;
            while ((pointer = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, pointer);
            }
        } catch (IOException exception) {
            Log.e(TAG, "Error writing/reading from the JSON file.", exception);
        } finally {
            try {
                inputStream.close();
            } catch (IOException exception) {
                Log.e(TAG, "Error closing the input stream.", exception);
            }
        }
        String jsonUsersString = writer.toString();
        Gson gson = new Gson();
        Type userListType = new TypeToken<ArrayList<UserEntry>>() {
        }.getType();
        return gson.fromJson(jsonUsersString, userListType);
    }
}
