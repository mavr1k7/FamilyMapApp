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

/**
 * The Login Fragment contains a list of fields that the user can use to login or register. Buttons
 * for each action at the bottom are disabled until the correct fields are filled in. After a successful
 * login or register, the user's data is pulled from the server and the map fragment is loaded.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class LoginFragment extends Fragment {
    /**
     * Link to the Server Host input field
     */
    private EditText mServerHostField;
    /**
     * Link to the Server Port input field
     */
    private EditText mServerPortField;
    /**
     * Link to the User Name input field
     */
    private EditText mUserNameField;
    /**
     * Link to the Password input field
     */
    private EditText mPasswordField;
    /**
     * Link to the First Name input field
     */
    private EditText mFirstNameField;
    /**
     * Link to the Last Name input field
     */
    private EditText mLastNameField;
    /**
     * Link to the Email input field
     */
    private EditText mEmailField;
    /**
     * Gender of the user registering (m by default)
     */
    private String mGender = "m";
    /**
     * Link to the Sign In button
     */
    private Button mSignInButton;
    /**
     * Link to the Register button
     */
    private Button mRegisterButton;
    /**
     * Request object created with the user's input
     */
    private static Request request;
    /**
     * URL used to access the server created with the user's input
     */
    private static String url;

    /**
     * When the fragment is created, call the onCreateView function
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * When the fragment view is created, all of the fields are loaded and the buttons are disabled. Each
     * field is given the same TextWatcher. When all the correct fields are filled in, the corresponding
     * button is enabled. The radio button is loaded and cycles between gender. The buttons are loaded
     * and given onClick listeners that call the async LoginRegisterTask and DataSyncTask classes.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Load all the text fields
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

        // Disable the buttons
        mSignInButton.setEnabled(false);
        mRegisterButton.setEnabled(false);

        // Add the same watcher to all the text fields
        mServerHostField.addTextChangedListener(watcher);
        mServerPortField.addTextChangedListener(watcher);
        mUserNameField.addTextChangedListener(watcher);
        mPasswordField.addTextChangedListener(watcher);
        mFirstNameField.addTextChangedListener(watcher);
        mLastNameField.addTextChangedListener(watcher);
        mEmailField.addTextChangedListener(watcher);

        // Add an OnCheckedChangeListener to cycle between male and female
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

        // When the sign in button is clicked, it creates a Login Request and attempts to log the user
        // in using the LoginRegisterTask. If it is successful, all the user's data is loaded with the
        // DataSyncTask. Then the map fragment is loaded displaying all the loaded information. The user's
        // login data is stored in the FamilyTree singleton
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request = new LoginRequest(mUserNameField.getText().toString(), mPasswordField.getText().toString());
                url = "http://" + mServerHostField.getText().toString() + ":" + mServerPortField.getText().toString() + "/user/login";
                new LoginRegisterTask(LoginFragment.this).execute();
            }
        });

        // When the register button is clicked, it creates a Register Request and attempts to log the user
        // in using the LoginRegisterTask. If it is successful, all the user's data is loaded with the
        // DataSyncTask. Then the map fragment is loaded displaying all the loaded information. The user's
        // login data is stored in the FamilyTree singleton
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

    // After any of the text fields are modified, the fields are checked for valid inputs. If the server
    // host, server port, user name, and password fields are all filled with valid inputs, the sign in
    // button is enabled. If all of the fields are filled with valid inputs, the register button is also
    // enabled. Otherwise they remain disabled.
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

    /**
     * This asynchronous task attempts to login or register the user with the given information. The
     * ServerProxy is used to connect with the server. If the login/register is successful, the DataSyncTask
     * is called and used to load the user's information. Otherwise, a Toast is displayed showing the
     * error that occurred.
     */
    private static class LoginRegisterTask extends AsyncTask<Void, Void, LoginResult> {

        private WeakReference<LoginFragment> fragmentReference;
        static final String TAG = "LoginRegisterTask";

        LoginRegisterTask(LoginFragment context) {
            fragmentReference = new WeakReference<>(context);
        }

        @Override
        protected LoginResult doInBackground(Void... params) {
            try {
                return login(url, request); // Login attempt
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
                    new DataSyncTask(fragment).execute(url, result.getAuthToken()); // Load the user's data
                } else {
                    Toast.makeText(fragment.getActivity(), result.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(fragment.getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * After a successful login, this asynchronous task is called to load the user's data. The user's
     * auth token is used to load all the persons and events related to them. After the data is loaded
     * and stored, the map fragment is loaded. If there is an error, a Toast is displayed showing the
     * error that occurred.
     */
    private static class DataSyncTask extends AsyncTask<String, Void, PersonResult> {

        private WeakReference<LoginFragment> fragmentReference;
        static final String TAG = "DataSyncTask";

        DataSyncTask(LoginFragment context) {
            fragmentReference = new WeakReference<>(context);
        }

        @Override
        protected PersonResult doInBackground(String... params) {
            try {
                return syncPersons(params[0], params[1]); // Load the user's data
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
