package com.teranpeterson.client.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.teranpeterson.client.R;
import com.teranpeterson.client.helpers.ServerProxy;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Settings;
import com.teranpeterson.client.result.PersonResult;

import java.io.IOException;
import java.lang.ref.WeakReference;

import static com.teranpeterson.client.helpers.ServerProxy.syncPersons;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Settings settings = Settings.get();

        Switch lifeStorySetting = findViewById(R.id.setting_life_story_switch);
        Switch familyTreeSetting = findViewById(R.id.setting_family_tree_switch);
        Switch spouseSetting = findViewById(R.id.setting_spouse_switch);

        lifeStorySetting.setChecked(settings.isLifeStoryLines());
        familyTreeSetting.setChecked(settings.isFamilyTreeLines());
        spouseSetting.setChecked(settings.isSpouseLines());

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

        Spinner lifeStoryColor = findViewById(R.id.setting_life_story_spinner);
        Spinner familyTreeColor = findViewById(R.id.setting_family_tree_spinner);
        Spinner spouseColor = findViewById(R.id.setting_spouse_spinner);
        Spinner mapMode = findViewById(R.id.setting_map_type_spinner);

        lifeStoryColor.setSelection(settings.getColorInt(settings.getLifeStoryLinesColor()));
        familyTreeColor.setSelection(settings.getColorInt(settings.getFamilyTreeLinesColor()));
        spouseColor.setSelection(settings.getColorInt(settings.getSpouseLinesColor()));
        mapMode.setSelection(settings.getMapTypeInt());

        lifeStoryColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setLifeStoryLinesColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        familyTreeColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setFamilyTreeLinesColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spouseColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setSpouseLinesColor(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mapMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Settings.get().setMapType(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.setting_resync).setOnClickListener(resync);
        findViewById(R.id.setting_resync_label).setOnClickListener(resync);

        findViewById(R.id.setting_logout).setOnClickListener(logout);
        findViewById(R.id.setting_logout_label).setOnClickListener(logout);
    }

    private final View.OnClickListener resync = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FamilyTree.get().clear();
            new ReSyncTask(v.getContext()).execute(FamilyTree.get().getUrl(), FamilyTree.get().getAuthToken());

        }
    };

    private final View.OnClickListener logout = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FamilyTree.get().setAuthToken(null);
            FamilyTree.get().clear();
            startActivity(new Intent(v.getContext(), MainActivity.class));
        }
    };

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
