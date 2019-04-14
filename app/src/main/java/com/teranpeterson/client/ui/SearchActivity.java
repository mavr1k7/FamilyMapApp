package com.teranpeterson.client.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.teranpeterson.client.R;

public class SearchActivity extends AppCompatActivity {
    private ImageView icon;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        icon = findViewById(R.id.search_icon);
        search = findViewById(R.id.search_value);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String match = search.getText().toString();
                icon.setImageResource(R.drawable.x);

                RecyclerView recyclerView = findViewById(R.id.search_recycler);
                recyclerView.setLayoutManager(new LinearLayoutManager(getParent()));
            }
        });
    }
}
