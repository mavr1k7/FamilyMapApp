package com.teranpeterson.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Filter;
import com.teranpeterson.client.recycler.Option;
import com.teranpeterson.client.recycler.OptionAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private OptionAdapter mAdapter;
    private List<Option> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Filter filter = Filter.get();

        Map<String, Boolean> eventTypes = FamilyTree.get().getEventTypes();

        mData = new ArrayList<>();
        for (Map.Entry type : eventTypes.entrySet()) {
            String title = ((String) type.getKey()).substring(0, 1).toUpperCase() + (((String) type.getKey()).substring(1));
            mData.add(new Option(title, (boolean) type.getValue()));
        }

        mRecyclerView = findViewById(R.id.filter_event_types);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        mAdapter = new OptionAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);

        Switch fatherFilter = findViewById(R.id.filter_father_switch);
        Switch motherFilter = findViewById(R.id.filter_mother_switch);
        Switch maleFilter = findViewById(R.id.filter_male_switch);
        Switch femaleFilter = findViewById(R.id.filter_female_switch);

        fatherFilter.setChecked(filter.isFather());
        motherFilter.setChecked(filter.isMother());
        maleFilter.setChecked(filter.isMale());
        femaleFilter.setChecked(filter.isFemale());

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
