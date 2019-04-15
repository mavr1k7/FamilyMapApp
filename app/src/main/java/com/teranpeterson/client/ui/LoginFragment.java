package com.teranpeterson.client.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
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
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.request.LoginRequest;
import com.teranpeterson.client.request.RegisterRequest;
import com.teranpeterson.client.request.Request;
import com.teranpeterson.client.result.LoginResult;
import com.teranpeterson.client.result.PersonResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.teranpeterson.client.helpers.ServerProxy.login;
import static com.teranpeterson.client.helpers.ServerProxy.syncPersons;

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

        mSignInButton.setEnabled(true);
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
        static final String TAG = "LoginRegisterTask";

        LoginRegisterTask(LoginFragment context) {
            fragmentReference = new WeakReference<>(context);
        }

        @Override
        protected LoginResult doInBackground(Void... params) {
            try {
                return login(url, request);
            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(LoginResult result) {
            LoginFragment fragment = fragmentReference.get();
            if (fragment == null || fragment.isRemoving()) return;

            if (result != null) {
                if (result.isSuccess()) {
                    String url = "http://" + fragment.mServerHostField.getText().toString() + ":" + fragment.mServerPortField.getText().toString();
                    FamilyTree.get().setUrl(url);
                    FamilyTree.get().setAuthToken(result.getAuthToken());
                    new DataSyncTask(fragment).execute(url, result.getAuthToken());
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
        static final String TAG = "DataSyncTask";

        DataSyncTask(LoginFragment context) {
            fragmentReference = new WeakReference<>(context);
        }

        @Override
        protected PersonResult doInBackground(String... params) {
            try {
                return syncPersons(params[0], params[1]);
            } catch (IOException e) {
                Log.e(TAG, "Failed to connect to server: ", e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(PersonResult result) {
            LoginFragment fragment = fragmentReference.get();
            if (fragment == null || fragment.isRemoving()) return;

            if (result.isSuccess()) {
                FragmentManager fragmentManager = fragment.getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction().replace(R.id.fragment_container, MapFragment.newInstance()).commit();
                }
            } else {
                Toast.makeText(fragment.getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
