package com.teranpeterson.client.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.teranpeterson.client.R;
import com.teranpeterson.client.model.FamilyTree;

/**
 * The Main Activity contains either a map fragment or a login fragment. When it is loaded, it checks
 * the FamilyTree singleton for a logged in user. It no user is logged in, the login fragment is displayed.
 * If there is a logged in user, the map fragment is displayed. It also checks for access to Google
 * Play Services onResume.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Request error code
     */
    private static final int REQUEST_ERROR = 0;

    /**
     * When the activity is created, it checks for a logged in user and displays either the map fragment
     * or the login fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            if (FamilyTree.get().isLoggedIn()) {
                fragment = new MapFragment();
            } else {
                fragment = new LoginFragment();
            }
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }

    /**
     * Checks for Google Play Services availability. This code comes from the book "Android Programming:
     * The Big Nerd Ranch Guide." For more information see https://www.bignerdranch.com.
     */
    @Override
    protected void onResume() {
        super.onResume();

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(this);

        if (errorCode != ConnectionResult.SUCCESS) {
            Dialog errorDialog = apiAvailability
                .getErrorDialog(this, errorCode, REQUEST_ERROR,
                    new DialogInterface.OnCancelListener() {

                        @Override
                        public void onCancel(DialogInterface dialog) {
                            // Leave if services are unavailable.
                            finish();
                        }
                    });

            errorDialog.show();
        }
    }
}
