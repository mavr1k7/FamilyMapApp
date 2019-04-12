package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.Filter;

public class FilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Switch mBirthFilter = findViewById(R.id.filter_birth_switch);
        Switch mBaptismFilter = findViewById(R.id.filter_baptism_switch);
        Switch mMarriageFilter = findViewById(R.id.filter_marriage_switch);
        Switch mDeathFilter = findViewById(R.id.filter_death_switch);
        Switch mFatherFilter = findViewById(R.id.filter_father_switch);
        Switch mMotherFilter = findViewById(R.id.filter_mother_switch);
        Switch mMaleFilter = findViewById(R.id.filter_male_switch);
        Switch mFemaleFilter = findViewById(R.id.filter_female_switch);

        mBirthFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setBirth(isChecked);
            }
        });

        mBaptismFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setBaptism(isChecked);
            }
        });

        mMarriageFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setMarriage(isChecked);
            }
        });

        mDeathFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setDeath(isChecked);
            }
        });

        mFatherFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setFather(isChecked);
            }
        });

        mMotherFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setMother(isChecked);
            }
        });

        mMaleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setMale(isChecked);
            }
        });

        mFemaleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setFemale(isChecked);
            }
        });
    }
}
