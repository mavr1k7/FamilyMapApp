package com.teranpeterson.client.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.teranpeterson.client.R;

/**
 * The Event Activity contains a map fragment. When it is loaded, it centers on the given event and
 * automatically fills the tray with information about the event. The event ID is passed to the map
 * fragment as a bundle. The same event ID is received through an intent.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class EventActivity extends AppCompatActivity {
    /**
     * Tag for the event ID passed in on creation of an Event Activity
     */
    private static final String EXTRA_EVENT_ID = "com.teranpeterson.client.ui.EventActivity.eventID";

    /**
     * When the activity is created, the eventID is loaded from the creation intent and passed to the
     * map fragment
     *
     * @param savedInstanceState Bundle with an event ID in it
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        String mEventID = getIntent().getStringExtra(EXTRA_EVENT_ID);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fm.beginTransaction().replace(R.id.fragment_container, MapFragment.newInstance(mEventID)).commit();
        }
    }

    /**
     * Static method that creates an intent for creating a new Event Activity with an Event ID
     *
     * @param eventID Event ID to display
     * @return Intent to create event activity
     */
    public static Intent newIntent(Context context, String eventID) {
        Intent intent = new Intent(context, EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventID);
        return intent;
    }
}
