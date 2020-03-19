package com.example.myapplication;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RegisterFragment extends Fragment {

    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        final TextInputLayout passwordTextInput = view.findViewById(R.id.password_text_input);
        final TextInputEditText passwordEditText = view.findViewById(R.id.password_edit_text);

        final TextInputLayout emailTextInput = view.findViewById(R.id.email_text_input);
        final TextInputEditText emailEditText = view.findViewById(R.id.email_edit_text);

//        final TextInputEditText telephoneTextInput = view.findViewById(R.id.telephone_text_input);
//        final TextInputEditText telephoneEditText = view.findViewById(R.id.telephone_edit_text);



        MaterialButton nextButton = view.findViewById(R.id.next_button);

        // Set an error if the password is less than 8 characters.
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isvalid = true;
                if (!isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError("Пароль має бути мін 8 символів");
                    isvalid=false;
                } else {
                    passwordTextInput.setError(null); // Clear the error
                    //((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
                }

                if (!isEmailValid(emailEditText.getText())) {
                    emailTextInput.setError("Невірно вказали пошту");
                    isvalid=false;
                } else {
                    emailTextInput.setError(null); // Clear the error
                    //((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
                }

//                if(!isTelephoneValid(telephoneEditText.getText())){
//                    telephoneTextInput.setError("Невірно вказали телефон");
//                    isvalid=false;
//                } else {
//                    telephoneTextInput.setError(null); // Clear the error
//                }

                if(isvalid){
                    //((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the next Fragment
                }

            }
        });

        // Clear the error once more than 8 characters are typed.
        passwordEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isPasswordValid(passwordEditText.getText())) {
                    passwordTextInput.setError(null); //Clear the error
                    //return true;
                }
                else {
                    passwordTextInput.setError("Пароль має бути мін 8 символів");
                }
                return false;
            }
        });

        // Clear the error once more than 8 characters are typed.
        emailEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (isEmailValid(emailEditText.getText())) {
                    emailTextInput.setError(null); //Clear the error
                    //return true;
                }
                else {
                    emailTextInput.setError("Невірно вказали пошту");
                }
                return false;
            }
        });
        return view;
    }

    private boolean isPasswordValid(@Nullable Editable text) {
        return text != null && text.length() >= 8;
    }

    private boolean isEmailValid(@Nullable Editable text) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(text);
        return matcher.find();
    }

    private boolean isTelephoneValid(@Nullable Editable text){
        final Pattern VALID_TELEPHONE_REGEX =
                Pattern.compile("^\\+[0-9]{1,3}\\.[0-9]{4,14}(?:x.+)?$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_TELEPHONE_REGEX .matcher(text);
        return matcher.find();
    }

}