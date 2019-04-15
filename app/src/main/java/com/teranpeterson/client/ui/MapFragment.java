package com.teranpeterson.client.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    private String mPersonID;
    private String mEventID;
    private GoogleApiClient mClient;
    private List<Polyline> mLines;

    private static final String ARG_EVENT_ID = "event_id";
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };

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

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .build();

        if (!hasLocationPermission()) {
            requestPermissions(LOCATION_PERMISSIONS,
                    REQUEST_LOCATION_PERMISSIONS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) mapFragment.getMapAsync(this);

        view.findViewById(R.id.map_text).setOnClickListener(trayClick);
        view.findViewById(R.id.map_profile).setOnClickListener(trayClick);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();

        mClient.disconnect();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map, menu);
    }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {}
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

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

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    public static MapFragment newInstance(String eventID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT_ID, eventID);

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private final GoogleMap.OnMarkerClickListener markerClick = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            fillInfo((String) marker.getTag());
            return false;
        }
    };

    private final View.OnClickListener trayClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPersonID != null) startActivity(PersonActivity.newIntent(getContext(), mPersonID));
        }
    };

    private void fillInfo(String eventID) {
        clearLines();
        Activity activity = getActivity();
        if (activity != null) {
            TextView map_text = activity.findViewById(R.id.map_text);
            FamilyTree familyTree = FamilyTree.get();
            Settings settings = Settings.get();
            Event event = familyTree.getEvent(eventID);
            Person person = familyTree.getPerson(event.getPersonID());
            mPersonID = person.getPersonID();

            String text = person.getFirstName() + " " + person.getLastName() + "\n" + event.getEventType()
                    + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
            map_text.setText(text);

            ImageView map_profile = activity.findViewById(R.id.map_profile);
            if (person.getGender().equals("f")) {
                map_profile.setImageResource(R.drawable.female);
            } else {
                map_profile.setImageResource(R.drawable.male);
            }

            // Spouse lines
            List<Event> spouseEvents = familyTree.getMyEvents(person.getSpouse());
            if (!spouseEvents.isEmpty() && settings.isSpouseLines()) {
                Event spouseEvent = spouseEvents.get(0);
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(event.getLatitude(), event.getLongitude()), new LatLng(spouseEvent.getLatitude(), spouseEvent.getLongitude()))
                        .width(4)
                        .color(settings.getSpouseLinesColorValue()));
                mLines.add(line);
            }

            // Life Story lines
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

            // Family Tree lines
            if (settings.isFamilyTreeLines()) {
                drawLines(new LatLng(event.getLatitude(), event.getLongitude()), person.getFather(), 10.0f, settings.getFamilyTreeLinesColorValue());
                drawLines(new LatLng(event.getLatitude(), event.getLongitude()), person.getMother(), 10.0f, settings.getFamilyTreeLinesColorValue());
            }
        }
    }

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

    private void clearLines() {
        for (Polyline line : mLines) {
            line.remove();
        }
        mLines.clear();
    }
}
