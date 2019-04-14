package com.teranpeterson.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.teranpeterson.client.R;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        RecyclerView recyclerView = findViewById(R.id.search_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
