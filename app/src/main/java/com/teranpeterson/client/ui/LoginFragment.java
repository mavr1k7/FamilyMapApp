package com.teranpeterson.client.ui;

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

import com.teranpeterson.client.R;
import com.teranpeterson.client.helpers.ServerProxy;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.RegisterRequest;
import com.teranpeterson.client.request.Request;
import com.teranpeterson.client.result.LoginResult;
import com.teranpeterson.client.result.PersonResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class LoginFragment extends Fragment {
    private EditText mServerHostField;
    private EditText mServerPortField;
    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mFirstNameField;
    private EditText mLastNameField;
    private EditText mEmailField;
    private String mGender = "m";
    private Button mSignInButton;
    private Button mRegisterButton;

    private String mUserID;

    private static Request request;
    private static String url;

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
        RadioGroup mGenderField = view.findViewById(R.id.radio_group_gender);
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
                new LoginRegisterTask(LoginFragment.this).execute();
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new RegisterRequest(mUserNameField.getText().toString(), mPasswordField.getText().toString(),
                        mEmailField.getText().toString(), mFirstNameField.getText().toString(), mLastNameField.getText().toString(), mGender);
                url = "http://" + mServerHostField.getText().toString() + ":" + mServerPortField.getText().toString() + "/user/register";
                new LoginRegisterTask(LoginFragment.this).execute();
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

    private static class LoginRegisterTask extends AsyncTask<Void, Void, LoginResult> {

        private WeakReference<LoginFragment> fragmentReference;

        LoginRegisterTask(LoginFragment context) {
            fragmentReference = new WeakReference<>(context);
        }

        @Override
        protected LoginResult doInBackground(Void... params) {
            try {
                return new ServerProxy().login(url, request);
            } catch (IOException e) {
                Log.e("LoginFragment-RegisterT", "Failed to connect to server: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(LoginResult result) {
            LoginFragment fragment = fragmentReference.get();
            if (fragment == null || fragment.isRemoving()) return;

            if (result != null) {
                if (result.isSuccess()) {
                    fragment.mUserID = result.getPersonID();
                    new DataSyncTask(fragment).execute("http://" + fragment.mServerHostField.getText().toString() + ":" + fragment.mServerPortField.getText().toString(), result.getAuthToken());
                } else {
                    Toast.makeText(fragment.getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(fragment.getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private static class DataSyncTask extends AsyncTask<String, Void, PersonResult> {

        private WeakReference<LoginFragment> fragmentReference;

        DataSyncTask(LoginFragment context) {
            fragmentReference = new WeakReference<>(context);
        }

        @Override
        protected PersonResult doInBackground(String... params) {
            try {
                new ServerProxy().syncEvents(params[0], params[1]);
                return new ServerProxy().syncPersons(params[0], params[1]);
            } catch (IOException e) {
                Log.e("LoginFragment-DataSyncT", "Failed to connect to server: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(PersonResult result) {
            LoginFragment fragment = fragmentReference.get();
            if (fragment == null || fragment.isRemoving()) return;

            if (result.isSuccess()) {
                Toast.makeText(fragment.getActivity(), result.find(fragment.mUserID), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(fragment.getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
