package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.teranpeterson.client.recycler.Header;
import com.teranpeterson.client.recycler.Item;
import com.teranpeterson.client.recycler.ExpandableRecyclerAdapter;
import com.teranpeterson.client.model.Event;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * The Person Activity contains an expandable recycler view. When it is loaded, it contains information
 * about a given person, including full name, gender, and related persons and events. The ID of the person
 * to display the information for is passed in in an intent.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class PersonActivity extends AppCompatActivity {
    /**
     * Tag for the person ID passed in on creation of a Person Activity
     */
    private static final String EXTRA_PERSON_ID = "com.teranpeterson.client.ui.PersonActivity.personID";

    /**
     * When the activity is created, the person ID is loaded from the creation intent and all of their
     * information is displayed. First the TextViews are updated with the person's name and gender. Then
     * all the persons relatives and events are wrapped up in Item objects and displayed in an expandable
     * recycler adapter.
     *
     * @param savedInstanceState Bundle with a person ID in it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        // Loads the person
        String mPersonID = getIntent().getStringExtra(EXTRA_PERSON_ID);
        Person person = FamilyTree.get().getPerson(mPersonID);

        // Loads the text views
        TextView firstName = findViewById(R.id.person_first_name);
        TextView lastName = findViewById(R.id.person_last_name);
        TextView gender = findViewById(R.id.person_gender);

        // Updates the text views with the person's information
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        gender.setText(person.getGenderFull());

        // Creates the expandable recycler view
        RecyclerView recyclerView = findViewById(R.id.person_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Header> categories = new ArrayList<>();

        // Wraps all the events related to the person in Items under the Header "Life Events"
        List<Item> events = new ArrayList<>();
        for (Event event : FamilyTree.get().getMyEvents(person.getPersonID())) {
            String part1 = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
            String part2 = person.getFirstName() + " " + person.getLastName();
            events.add(new Item(event.getEventID(), part1, part2, false, false));
        }

        Header lifeEvents = new Header("Life Events", events);
        categories.add(lifeEvents);

        // Wraps all the family members related to the person in Items under the Header "Family"
        List<Item> persons = new ArrayList<>();
        if (person.getFather() != null) {
            Person father = FamilyTree.get().getPerson(person.getFather());
            String part1 = father.getFirstName() + " " + father.getLastName();
            String part2 = "Father";
            persons.add(new Item(father.getPersonID(), part1, part2, true, father.isMale()));
        }
        if (person.getMother() != null) {
            Person mother = FamilyTree.get().getPerson(person.getMother());
            String part1 = mother.getFirstName() + " " + mother.getLastName();
            String part2 = "Mother";
            persons.add(new Item(mother.getPersonID(), part1, part2, true, mother.isMale()));
        }
        if (person.getSpouse() != null) {
            Person spouse = FamilyTree.get().getPerson(person.getSpouse());
            String part1 = spouse.getFirstName() + " " + spouse.getLastName();
            String part2 = "Spouse";
            persons.add(new Item(spouse.getPersonID(), part1, part2, true, spouse.isMale()));
        }
        if (person.getChild() != null) {
            Person child = FamilyTree.get().getPerson(person.getChild());
            String part1 = child.getFirstName() + " " + child.getLastName();
            String part2 = "Child";
            persons.add(new Item(child.getPersonID(), part1, part2, true, child.isMale()));
        }

        Header family = new Header("Family", persons);
        categories.add(family);

        // Passes all the wrapped data to the recycler view
        ExpandableRecyclerAdapter adapter = new ExpandableRecyclerAdapter(categories);
        recyclerView.setAdapter(adapter);
    }

    /**
     * Static method that creates an intent for creating a new Person Activity with a Person ID
     *
     * @param personID Person ID to display
     * @return Intent to create event activity
     */
    public static Intent newIntent(Context context, String personID) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personID);
        return intent;
    }
}
