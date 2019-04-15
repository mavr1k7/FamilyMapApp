package com.teranpeterson.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.teranpeterson.client.R;
import com.teranpeterson.client.recycler.Item;
import com.teranpeterson.client.recycler.SearchAdapter;
import com.teranpeterson.client.model.Event;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ImageView mIcon;
    private EditText mSearch;
    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private List<Item> mData;
    private boolean clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mIcon = findViewById(R.id.search_icon);
        mSearch = findViewById(R.id.search_value);
        mData = new ArrayList<>();
        clear = false;

        mRecyclerView = findViewById(R.id.search_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        mAdapter = new SearchAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);

        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clear) {
                    clear = false;

                    mIcon.setImageResource(R.drawable.magnifying);
                    mSearch.setText("");
                    mData.clear();
                    mAdapter.notifyDataSetChanged();
                } else {
                    clear = true;

                    String match = mSearch.getText().toString();
                    mIcon.setImageResource(R.drawable.x);

                    List<Person> persons = FamilyTree.get().searchPersons(match);
                    List<Event> events = FamilyTree.get().searchEvents(match);

                    for (Person person : persons) {
                        String part1 = person.getFirstName() + " " + person.getLastName();
                        String part2 = "";
                        mData.add(new Item(person.getPersonID(), part1, part2, true, person.isMale()));
                    }
                    for (Event event : events) {
                        String part1 = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
                        Person person = FamilyTree.get().getPerson(event.getPersonID());
                        String part2 = person.getFirstName() + " " + person.getLastName();
                        mData.add(new Item(event.getEventID(), part1, part2, false, false));
                    }

                    mAdapter.notifyDataSetChanged();
                }
            }
        });

        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clear = false;
                mIcon.setImageResource(R.drawable.magnifying);
                mData.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
