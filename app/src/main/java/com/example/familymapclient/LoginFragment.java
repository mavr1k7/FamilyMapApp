package com.example.familymapclient;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LoginFragment extends Fragment {
    private EditText mServerHostField;
    private EditText mServerPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private Button mSignInButton;
    private Button mRegisterButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        mServerHostField = v.findViewById(R.id.edit_server_host);
        mServerPortField = v.findViewById(R.id.edit_server_port);
        mUserNameField = v.findViewById(R.id.edit_user_name);
        mPasswordField = v.findViewById(R.id.edit_password);
        mFirstNameField = v.findViewById(R.id.edit_first_name);
        mLastNameField = v.findViewById(R.id.edit_last_name);
        mEmailField = v.findViewById(R.id.edit_email);
        mSignInButton = v.findViewById(R.id.button_sign_in);
        mRegisterButton = v.findViewById(R.id.button_register);

        mSignInButton.setEnabled(false);
        mRegisterButton.setEnabled(false);

        mServerHostField.addTextChangedListener(watcher);
        mServerPortField.addTextChangedListener(watcher);
        mUserNameField.addTextChangedListener(watcher);
        mPasswordField.addTextChangedListener(watcher);
        mFirstNameField.addTextChangedListener(watcher);
        mLastNameField.addTextChangedListener(watcher);
        mEmailField.addTextChangedListener(watcher);

        return v;
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Blank
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Blank
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (mServerHostField.getText().length() != 0 && mServerPortField.getText().length() != 0
            && mUserNameField.getText().length() != 0 && mPasswordField.getText().length() != 0) {
                mSignInButton.setEnabled(true);
                if (mFirstNameField.getText().length() != 0 && mLastNameField.getText().length() != 0
                && mEmailField.getText().length() != 0) {
                    mRegisterButton.setEnabled(true);
                } else {
                    mRegisterButton.setEnabled(false);
                }
            } else {
                mSignInButton.setEnabled(false);
                mRegisterButton.setEnabled(false);
            }
        }
    };
}
