package com.teranpeterson.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.Settings;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch lifeStorySetting = findViewById(R.id.setting_life_story_switch);
        Switch familyTreeSetting = findViewById(R.id.setting_family_tree_switch);
        Switch spouseSetting = findViewById(R.id.setting_spouse_switch);

        Spinner lifeStoryColor = findViewById(R.id.setting_life_story_spinner);
        Spinner familyTreeColor = findViewById(R.id.setting_family_tree_spinner);
        Spinner spouseColor = findViewById(R.id.setting_spouse_spinner);
        Spinner mapMode = findViewById(R.id.setting_map_type_spinner);

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
    }
}
