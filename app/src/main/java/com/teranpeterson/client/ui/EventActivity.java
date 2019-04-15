package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.teranpeterson.client.R;

public class EventActivity extends AppCompatActivity {
    private static final String EXTRA_EVENT_ID = "com.teranpeterson.client.ui.EventActivity.eventID";

    private String mEventID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mEventID = getIntent().getStringExtra(EXTRA_EVENT_ID);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fm.beginTransaction().replace(R.id.fragment_container, MapFragment.newInstance(mEventID)).commit();
        }
    }

    public static Intent newIntent(Context context, String eventID) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventID);
        return intent;
    }
}
