package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Settings;
import com.teranpeterson.client.result.PersonResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.teranpeterson.client.helpers.ServerProxy.syncPersons;

/**
 * The Settings Activity contains a series of dropdowns, switches, and buttons that are used to modify
 * the application. When it is loaded, all the current settings are pulled from the Settings singleton.
 * Details about each function can be found below.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class SettingsActivity extends AppCompatActivity {
    /**
     * When the activity is created, the current settings are pulled from the Settings singleton and
     * displayed. Each switch is loaded and given an OnCheckedChangeListener. Spinners are loaded and
     * given OnItemSelectListeners.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settings settings = Settings.get();

        // Load the switches
        Switch lifeStorySetting = findViewById(R.id.setting_life_story_switch);
        Switch familyTreeSetting = findViewById(R.id.setting_family_tree_switch);
        Switch spouseSetting = findViewById(R.id.setting_spouse_switch);

        // Set the switches to enabled/disabled
        lifeStorySetting.setChecked(settings.isLifeStoryLines());
        familyTreeSetting.setChecked(settings.isFamilyTreeLines());
        spouseSetting.setChecked(settings.isSpouseLines());

        // Add an OnCheckedChangeListener to each switch
        lifeStorySetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.get().setLifeStoryLines(isChecked);
            }
        });

        familyTreeSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.get().setFamilyTreeLines(isChecked);
            }
        });

        spouseSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Settings.get().setSpouseLines(isChecked);
            }
        });

        // Load the spinners (aka dropdowns)
        Spinner lifeStoryColor = findViewById(R.id.setting_life_story_spinner);
        Spinner familyTreeColor = findViewById(R.id.setting_family_tree_spinner);
        Spinner spouseColor = findViewById(R.id.setting_spouse_spinner);
        Spinner mapMode = findViewById(R.id.setting_map_type_spinner);

        // Set the spinners to their current settings
        lifeStoryColor.setSelection(settings.getColorInt(settings.getLifeStoryLinesColor()));
        familyTreeColor.setSelection(settings.getColorInt(settings.getFamilyTreeLinesColor()));
        spouseColor.setSelection(settings.getColorInt(settings.getSpouseLinesColor()));
        mapMode.setSelection(settings.getMapTypeInt());

        // Add an OnItemSelectedListener to Life Story Color
        lifeStoryColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setLifeStoryLinesColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Add an OnItemSelectedListener to Family Tree Color
        familyTreeColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setFamilyTreeLinesColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Add an OnItemSelectedListener to Spouse Color
        spouseColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setSpouseLinesColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Add an OnItemSelectedListener to Map Mode
        mapMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setMapType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Create the resync button with an onClick listener (see below)
        findViewById(R.id.setting_resync).setOnClickListener(resync);
        findViewById(R.id.setting_resync_label).setOnClickListener(resync);

        // Create the logout button with an onClick listener (see below)}
        findViewById(R.id.setting_logout).setOnClickListener(logout);
        findViewById(R.id.setting_logout_label).setOnClickListener(logout);
    }

    // Create an onClick listener for Re-sync. When this button is clicked, all the data currently in
    // the FamilyTree Singleton is removed and new data is loaded. Calls the below async ReSyncTask.
    // After execution, the Main activity is loaded with the map fragment visible.
    private final View.OnClickListener resync = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FamilyTree.get().clear();
            new ReSyncTask(v.getContext()).execute(FamilyTree.get().getUrl(), FamilyTree.get().getAuthToken());

        }
    };

    // Creates an onClick listener for Logout. When this button is clicked, all the data currently in
    // the FamilyTree Singleton is removed along with the user's credentials. After execution, the Main
    // activity is loaded with the login fragment visible.
    private final View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FamilyTree.get().setAuthToken(null);
            FamilyTree.get().clear();
            startActivity(new Intent(v.getContext(), MainActivity.class));
        }
    };

    /**
     * This asynchronous task reloads all of the data in the system. All of the data stored in the
     * FamilyTree singleton is removed. The stored URL is used to call syncPersons to reload all of
     * the data. After execution, the main activity is loaded with the map fragment visible, showing
     * all the re-synced data.
     */
    private static class ReSyncTask extends AsyncTask<String, Void, PersonResult> {

        private WeakReference<Context> activityReference;
        static final String TAG = "DataSyncTask";

        ReSyncTask(Context context) {
            activityReference = new WeakReference<>(context);
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
            Context context = activityReference.get();
            if (context == null) return;

            if (result.isSuccess()) {
                context.startActivity(new Intent(context, MainActivity.class));
            } else {
                Toast.makeText(context, result.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
