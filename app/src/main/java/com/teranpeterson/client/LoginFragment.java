package com.teranpeterson.client;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.teranpeterson.client.helpers.Client;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.RegisterRequest;
import com.teranpeterson.client.request.Request;
import com.teranpeterson.client.result.LoginResult;

import java.io.IOException;

public class LoginFragment extends Fragment {
    private EditText mServerHostField;
    private EditText mServerPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private String mGender = "m";
    private RadioGroup mGenderField;
    private Button mSignInButton;
    private Button mRegisterButton;

    private static Request request;
    private static String url;
    private static LoginResult result;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mServerHostField = view.findViewById(R.id.edit_server_host);
        mServerPortField = view.findViewById(R.id.edit_server_port);
        mUserNameField = view.findViewById(R.id.edit_user_name);
        mPasswordField = view.findViewById(R.id.edit_password);
        mFirstNameField = view.findViewById(R.id.edit_first_name);
        mLastNameField = view.findViewById(R.id.edit_last_name);
        mEmailField = view.findViewById(R.id.edit_email);
        mGenderField = view.findViewById(R.id.radio_group_gender);
        mSignInButton = view.findViewById(R.id.button_sign_in);
        mRegisterButton = view.findViewById(R.id.button_register);

        mSignInButton.setEnabled(false);
        mRegisterButton.setEnabled(false);

        mServerHostField.addTextChangedListener(watcher);
        mServerPortField.addTextChangedListener(watcher);
        mUserNameField.addTextChangedListener(watcher);
        mPasswordField.addTextChangedListener(watcher);
        mFirstNameField.addTextChangedListener(watcher);
        mLastNameField.addTextChangedListener(watcher);
        mEmailField.addTextChangedListener(watcher);

        mGenderField.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_male:
                        mGender = "m";
                        break;
                    case R.id.radio_female:
                        mGender = "f";
                        break;
                    default:
                        mGender = "m";
                        break;
                }
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new LoginRequest(mUserNameField.getText().toString(), mPasswordField.getText().toString());
                url = "http://" + mServerHostField.getText().toString() + ":" + mServerPortField.getText().toString() + "/user/login";
                new LoginTask().execute();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new RegisterRequest(mUserNameField.getText().toString(), mPasswordField.getText().toString(),
                        mEmailField.getText().toString(), mFirstNameField.getText().toString(), mLastNameField.getText().toString(), mGender);
                url = "http://" + mServerHostField.getText().toString() + ":" + mServerPortField.getText().toString() + "/user/register";
                new LoginTask().execute();
            }
        });

        return view;
    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Do nothing
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Do nothing
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

    private class LoginTask extends AsyncTask<Void, Void, LoginResult> {
        @Override
        protected LoginResult doInBackground(Void... params) {
            try {
                return new Client().login(url, request);
            } catch (IOException e) {
                Log.e("LoginFragment-LoginTask", "Failed to connect to server: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(LoginResult result) {
            if (result.isSuccess()) {
                Toast.makeText(LoginFragment.this.getActivity(), result.getAuthToken(), Toast.LENGTH_SHORT).show();
                Log.i("LoginFragment", "Success: " + result.getAuthToken());
            } else {
                Toast.makeText(LoginFragment.this.getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("LoginFragment", "Error: " + result.getMessage());
            }
        }
    }

    private class SyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                new Client().syncPersons(url);
            } catch (IOException e) {
                Log.e("LoginFragment-SyncTask", "Failed to connect to server: ", e);
            }
            return null;
        }
    }
}
