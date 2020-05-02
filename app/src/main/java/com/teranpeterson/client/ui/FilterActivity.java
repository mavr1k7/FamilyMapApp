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
import java.util.List;
import java.util.Map;

/**
 * The Filter Activity displays a recycler view list of all the event types loaded from the server as
 * well as filters for side of the family and gender. When it is loaded, the current filter states are
 * loaded from the Filter Singleton and used to set the switches to enabled/disabled.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class FilterActivity extends AppCompatActivity {

    /**
     * When the activity is created, first the recycler view is enabled. Then each switch is loaded,
     * set to enabled or disabled based on the current filter settings, and given an OnCheckedChangeListener
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        Filter filter = Filter.get();

        // Gets all event types from the FamilyTree singleton and capitalizes the first letter
        Map<String, Boolean> eventTypes = FamilyTree.get().getEventTypes();
        List<Option> data = new ArrayList<>();
        for (Map.Entry type : eventTypes.entrySet()) {
            String title = ((String) type.getKey()).substring(0, 1).toUpperCase() + (((String) type.getKey()).substring(1));
            data.add(new Option(title, (boolean) type.getValue()));
        }

        // Sets the recyclerView adapter
        RecyclerView recyclerView = findViewById(R.id.filter_event_types);
        recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        OptionAdapter adapter = new OptionAdapter(this, data);
        recyclerView.setAdapter(adapter);

        // Load the switches
        Switch fatherFilter = findViewById(R.id.filter_father_switch);
        Switch motherFilter = findViewById(R.id.filter_mother_switch);
        Switch maleFilter = findViewById(R.id.filter_male_switch);
        Switch femaleFilter = findViewById(R.id.filter_female_switch);

        // Set the switches to enabled/disabled
        fatherFilter.setChecked(filter.isFather());
        motherFilter.setChecked(filter.isMother());
        maleFilter.setChecked(filter.isMale());
        femaleFilter.setChecked(filter.isFemale());

        // Add OnCheckedChangeListeners
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
