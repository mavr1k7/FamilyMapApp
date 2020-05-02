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

/**
 * The Search Activity contains a recycler view used to display all the persons and events that match
 * a given search criteria. When it is loaded, no information is shown. After a search is performed,
 * the list is updated with all the matching items.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class SearchActivity extends AppCompatActivity {
    /**
     * Link to the icon displayed next to the search bar (magnifying glass or x)
     */
    private ImageView mIcon;
    /**
     * Link to the search box
     */
    private EditText mSearch;
    /**
     * Link to the adapter used to display the recycler view info
     */
    private SearchAdapter mAdapter;
    /**
     * List of data to display in the recycler view
     */
    private List<Item> mData;
    /**
     * Boolean value that decides what clicking on the mIcon does. If it is true, it switches mIcon
     * to a magnifying glass, clears the search field, and removes all previous results. If it is false,
     * it switches mIcon to an x, and performs the search function, displaying all matching items.
     */
    private boolean clear;

    /**
     * When the activity is created, the search field and recycler view are created and set to empty.
     * An OnClickListener is enabled on mIcon to perform search or clear functions. An TextWatcher is
     * also enabled to update values if the x is not clicked.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mIcon = findViewById(R.id.search_icon);
        mSearch = findViewById(R.id.search_value);
        mData = new ArrayList<>();
        clear = false;

        RecyclerView recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
        mAdapter = new SearchAdapter(this, mData);
        recyclerView.setAdapter(mAdapter);

        // When mIcon is clicked, if clear is true, the field is cleared and all of the data is reset
        // to empty. It clear is false, a search is performed and the data is updated to display all
        // the items matching the search criteria
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

        // After text in mSearch is modified, mIcon is updated to a magnifying glass and reset to perform
        // a search function
        mSearch.addTextChangedListener(new TextWatcher() {
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
                clear = false;
                mIcon.setImageResource(R.drawable.magnifying);
                mData.clear();
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
