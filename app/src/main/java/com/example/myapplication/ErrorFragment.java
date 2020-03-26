package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.utils.network.RequestErrorNavigate;

public class ErrorFragment extends Fragment {

    private final String errorStr;
    public ErrorFragment(String errorStr) {
        this.errorStr=errorStr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_error, container, false);
        TextView tv_message = view.findViewById(R.id.tv_message);
        tv_message.setText(errorStr);
        Button btnTry = view.findViewById(R.id.btnTry);
        btnTry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((RequestErrorNavigate) getActivity()).returnRefreshPage();
            }
        });
        return view;
    }
}
