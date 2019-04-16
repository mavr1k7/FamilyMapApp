package com.teranpeterson.client.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.teranpeterson.client.R;
import com.teranpeterson.client.model.Event;
import com.teranpeterson.client.model.FamilyTree;
import com.teranpeterson.client.model.Person;
import com.teranpeterson.client.model.Settings;

import java.util.ArrayList;
import java.util.List;

/**
 * The Map Fragment contains a Google Map that displays markers and lines corresponding to the user's
 * family data. Each event is marked on the map. When an event is selected, the map moves to center that
 * marker and the tray below the map is updated to display that event's details. Lines are also drawn
 * based on current settings connecting that event to any related person's first event. When a new event
 * is selected, the current lines are destroyed. When it is loaded in the main activity, it shows a toolbar
 * with no up button and no selected event. When it is loaded in the event activity, an event ID is passed
 * in and automatically selected.
 *
 * @author Teran Peterson
 * @version v0.1.1
 */
public class MapFragment extends Fragment implements OnMapReadyCallback {
    /**
     * Map displayed in the UI
     */
    private GoogleMap mMap;
    /**
     * The ID of the person the selected event belongs to
     */
    private String mPersonID;
    /**
     * The ID of the selected event
     */
    private String mEventID;
    /**
     * Google Play Service client
     */
    private GoogleApiClient mClient;
    /**
     * List of all the lines currently drawn on the map
     */
    private List<Polyline> mLines;
    /**
     * ID for the event ID passed in in the bundle
     */
    private static final String ARG_EVENT_ID = "event_id";
    /**
     * Location permission value. Not 0 if permissions are enabled
     */
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    /**
     * List of permissions needed by the fragment
     */
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    /**
     * When the fragment is created, check if the bundle contains an event ID. If it has one, the menu
     * is not displayed. Then a series of checks and requests are performed for location permissions.
     *
     * @param savedInstanceState Optional event ID
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLines = new ArrayList<>();

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey(ARG_EVENT_ID)) {
            mEventID = (String) arguments.getSerializable(ARG_EVENT_ID);
        }

        if (mEventID != null && !mEventID.isEmpty()) {
            setHasOptionsMenu(false);
        } else {
            setHasOptionsMenu(true);
        }

        Activity activity = getActivity();
        if (activity != null)
            mClient = new GoogleApiClient.Builder(activity)
                .addApi(LocationServices.API)
                .build();

        if (!hasLocationPermission()) {
            requestPermissions(LOCATION_PERMISSIONS,
                    REQUEST_LOCATION_PERMISSIONS);
        }
    }

    /**
     * When the fragment view is created, load the map and create an onClick for the information tray.
     * When the tray is clicked, a person activity should be loaded with the corresponding persons info.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        view.findViewById(R.id.map_text).setOnClickListener(trayClick);
        view.findViewById(R.id.map_profile).setOnClickListener(trayClick);

        return view;
    }

    // Connect the map to Google Play Services
    @Override
    public void onStart() {
        super.onStart();

        mClient.connect();
    }

    // Disconnect the map from Google Play Services
    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    // Create an options menu using the fragment menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);
    }

    /**
     * When an option from the menu is selected, load the corresponding activity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                startActivity(new Intent(this.getContext(), FilterActivity.class));
                return true;
            case R.id.search:
                startActivity(new Intent(this.getContext(), SearchActivity.class));
                return true;
            case R.id.settings:
                startActivity(new Intent(this.getContext(), SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * If the app does not have location permissions, request it.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    Log.d("PermissionResult", "Has permission."); // Literally only here to suppress a warning
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**
     * Check if the app has permissions or not.
     * @return True if it has the needed permissions. False if not.
     */
    private boolean hasLocationPermission() {
        Activity activity = getActivity();
        if (activity != null) {
            int result = ContextCompat.checkSelfPermission(activity, LOCATION_PERMISSIONS[0]);
            return result == PackageManager.PERMISSION_GRANTED;
        } else return false;
    }

    /**
     * Create the map with the settigns listed in the Settings singleton. Add an OnMarkerClickListener
     * to the map. Load all of the events and place corresponding markers on the map. If there is an
     * event ID provided on creation, center the map on that event and display it's information.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Set map mode
        switch (Settings.get().getMapType()) {
            case "Hybrid":
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Satellite":
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Terrain":
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }

        mMap.setOnMarkerClickListener(markerClick);

        // Load all events to map
        for (Event event : FamilyTree.get().getEvents()) {
            LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(location));
            marker.setIcon(BitmapDescriptorFactory.defaultMarker(FamilyTree.get().getEventColor(event.getEventType())));
            marker.setTag(event.getEventID());
        }

        // Center camera on provided event id and display event info
        if (mEventID != null && !mEventID.isEmpty()) {
            Event event = FamilyTree.get().getEvent(mEventID);
            LatLng location = new LatLng(event.getLatitude(), event.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));

            fillInfo(mEventID);
        }
    }

    // When a marker is clicked, load its information into the tray
    private final GoogleMap.OnMarkerClickListener markerClick = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            fillInfo((String) marker.getTag());
            return false;
        }
    };

    // When the tray is clicked on, load a person activity with the person's information
    private final View.OnClickListener trayClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPersonID != null) startActivity(PersonActivity.newIntent(getContext(), mPersonID));
        }
    };

    /**
     * Fill the tray with information about the selected event. Set the icon based on the corresponding
     * person's gender. Then draw lines between all the related events based on the current settings.
     * @param eventID
     */
    private void fillInfo(String eventID) {
        clearLines();
        Activity activity = getActivity();
        if (activity != null) {
            // Load all of the information needed
            TextView map_text = activity.findViewById(R.id.map_text);
            FamilyTree familyTree = FamilyTree.get();
            Settings settings = Settings.get();
            Event event = familyTree.getEvent(eventID);
            Person person = familyTree.getPerson(event.getPersonID());
            mPersonID = person.getPersonID();

            // Build the information string for the tray
            String text = person.getFirstName() + " " + person.getLastName() + "\n" + event.getEventType()
                    + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
            map_text.setText(text);

            // Set the icon based on the person's gender
            ImageView map_profile = activity.findViewById(R.id.map_profile);
            if (person.getGender().equals("f")) {
                map_profile.setImageResource(R.drawable.female);
            } else {
                map_profile.setImageResource(R.drawable.male);
            }

            // Draw lines between this event and the spouses first event (if enabled)
            List<Event> spouseEvents = familyTree.getMyEvents(person.getSpouse());
            if (!spouseEvents.isEmpty() && settings.isSpouseLines()) {
                Event spouseEvent = spouseEvents.get(0);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(event.getLatitude(), event.getLongitude()), new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()))
                        .width(4)
                        .color(settings.getSpouseLinesColorValue()));
                mLines.add(line);
            }

            // Draw lines between this event and the rest of the person's events (if enabled)
            List<Event> personEvents = familyTree.getMyEvents(person.getPersonID());
            if (!personEvents.isEmpty() && settings.isLifeStoryLines()) {
                List<LatLng> points = new ArrayList<>();
                for (Event personEvent : personEvents) {
                    points.add(new LatLng(personEvent.getLatitude(), personEvent.getLongitude()));
                }
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .addAll(points)
                        .width(4)
                        .color(settings.getLifeStoryLinesColorValue()));
                mLines.add(line);
            }

            // Draw lines between this event and the person's parents, recursively backwards for all
            // of the person's ancestors
            if (settings.isFamilyTreeLines()) {
                drawLines(new LatLng(event.getLatitude(), event.getLongitude()), person.getFather(), 10.0f, settings.getFamilyTreeLinesColorValue());
                drawLines(new LatLng(event.getLatitude(), event.getLongitude()), person.getMother(), 10.0f, settings.getFamilyTreeLinesColorValue());
            }
        }
    }

    /**
     * Draw a line between the childLoc and the parent's first event location. Set the width to the width
     * param, which decreases by 2 each generation.
     *
     * @param childLoc Starting point for the line, location of the person's child's first event
     * @param parentID ID of the person to draw lines to
     * @param width Width of the line
     * @param color Color of the line
     */
    private void drawLines(LatLng childLoc, String parentID, float width, int color) {
        List<Event> parentEvents = FamilyTree.get().getMyEvents(parentID);
        if (!parentEvents.isEmpty()) {
            Event parentEvent = parentEvents.get(0);
            LatLng parentLoc = new LatLng(parentEvent.getLatitude(), parentEvent.getLongitude());
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(childLoc, parentLoc)
                    .width(width)
                    .color(color));
            mLines.add(line);

            Person parent = FamilyTree.get().getPerson(parentID);
            if (parent != null) {
                drawLines(parentLoc, parent.getFather(), width - 2.0f, color);
                drawLines(parentLoc, parent.getMother(), width - 2.0f, color);
            }
        }
    }

    /**
     * Remove all the lines currently drawn on the map
     */
    private void clearLines() {
        for (Polyline line : mLines) {
            line.remove();
        }
        mLines.clear();
    }

    /**
     * Static class to create a map fragment without a starting event ID and with a menu
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    /**
     * Static class to create a map fragment with a starting event ID and no menu
     *
     * @param eventID ID of the event to display information for
     */
    public static MapFragment newInstance(String eventID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventID);

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
