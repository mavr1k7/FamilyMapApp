package com.teranpeterson.client.ui;

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

        Switch birthFilter = findViewById(R.id.filter_birth_switch);
        Switch baptismFilter = findViewById(R.id.filter_baptism_switch);
        Switch marriageFilter = findViewById(R.id.filter_marriage_switch);
        Switch deathFilter = findViewById(R.id.filter_death_switch);
        Switch fatherFilter = findViewById(R.id.filter_father_switch);
        Switch motherFilter = findViewById(R.id.filter_mother_switch);
        Switch maleFilter = findViewById(R.id.filter_male_switch);
        Switch femaleFilter = findViewById(R.id.filter_female_switch);

        birthFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setBirth(isChecked);
            }
        });

        baptismFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setBaptism(isChecked);
            }
        });

        marriageFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setMarriage(isChecked);
            }
        });

        deathFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setDeath(isChecked);
            }
        });

        fatherFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setFather(isChecked);
            }
        });

        motherFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setMother(isChecked);
            }
        });

        maleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setMale(isChecked);
            }
        });

        femaleFilter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Filter.get().setFemale(isChecked);
            }
        });
    }
}
