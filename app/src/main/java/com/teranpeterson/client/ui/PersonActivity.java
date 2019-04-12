package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;

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
    }

    public static Intent newIntent(Context context, String personID) {
        Intent intent = new Intent(context, PersonActivity.class);
        intent.putExtra(EXTRA_PERSON_ID, personID);
        return intent;
    }
}
