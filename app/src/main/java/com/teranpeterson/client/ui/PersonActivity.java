package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.teranpeterson.client.helpers.Header;
import com.teranpeterson.client.helpers.Item;
import com.teranpeterson.client.helpers.ExpandableRecyclerAdapter;
import com.teranpeterson.client.model.Event;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonActivity extends AppCompatActivity {
    private static final String EXTRA_PERSON_ID = "com.teranpeterson.client.ui.PersonActivity.personID";

    private String mPersonID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        mPersonID = getIntent().getStringExtra(EXTRA_PERSON_ID);
        Person person = FamilyTree.get().getPerson(mPersonID);

        TextView firstName = findViewById(R.id.person_first_name);
        TextView lastName = findViewById(R.id.person_last_name);
        TextView gender = findViewById(R.id.person_gender);

        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        gender.setText(person.getGenderFull());

        RecyclerView recyclerView = findViewById(R.id.person_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Header> categories = new ArrayList<>();

        List<Item> events = new ArrayList<>();
        for (Event event : person.getEvents()) {
            String part1 = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
            String part2 = person.getFirstName() + " " + person.getLastName();
            events.add(new Item(part1, part2, false, false));
        }

        Header lifeEvents = new Header("Life Events", events);
        categories.add(lifeEvents);

        List<Item> persons = new ArrayList<>();
        if (person.getFather() != null) {
            Person father = FamilyTree.get().getPerson(person.getFather());
            String part1 = father.getFirstName() + " " + father.getLastName();
            String part2 = "Father";
            persons.add(new Item(part1, part2, true, father.isMale()));
        }
        if (person.getMother() != null) {
            Person mother = FamilyTree.get().getPerson(person.getMother());
            String part1 = mother.getFirstName() + " " + mother.getLastName();
            String part2 = "Mother";
            persons.add(new Item(part1, part2, true, mother.isMale()));
        }
        if (person.getSpouse() != null) {
            Person spouse = FamilyTree.get().getPerson(person.getSpouse());
            String part1 = spouse.getFirstName() + " " + spouse.getLastName();
            String part2 = "Spouse";
            persons.add(new Item(part1, part2, true, spouse.isMale()));
        }
        if (person.getChild() != null) {
            Person child = FamilyTree.get().getPerson(person.getChild());
            String part1 = child.getFirstName() + " " + child.getLastName();
            String part2 = "Child";
            persons.add(new Item(part1, part2, true, child.isMale()));
        }

        Header family = new Header("Family", persons);
        categories.add(family);

        ExpandableRecyclerAdapter adapter = new ExpandableRecyclerAdapter(categories);
        recyclerView.setAdapter(adapter);
    }

    public static Intent newIntent(Context context, String personID) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personID);
        return intent;
    }
}
