package com.example.mygeoquiz.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.mygeoquiz.R;
import com.example.mygeoquiz.activities.MainActivity;
import com.example.mygeoquiz.struct.pEntry;
import com.example.sharedcodemodule.model.event;
import com.example.sharedcodemodule.model.person;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class mapFragment extends Fragment implements OnMapReadyCallback {

    private View view;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private FragmentManager supportFragmentManager;

    private Marker miMarker;

    private List<Polyline> polylines = new ArrayList<Polyline>();
    private int standardWidth = 15;

    public Map<String, String> markerKey;

    public person user;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private com.example.mygeoquiz.struct.familyData familyData;
    private boolean[] settings;

    private TextView mapTextView;

    private TextView tv1;
    private TextView tv2;
    private ImageView imgV;

    private event selectedEvent = null;
    private person selectedPerson = null;
    private person selectedPersonSpouce = null;

    private RelativeLayout footBox;

    private int pink = Color.rgb(255, 184, 184);
    private int blue = Color.rgb(86, 98, 225);

    public mapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume()
    {
        //drawLines();
        super.onResume();

        if(mapFragment != null && mMap != null && markerKey != null && polylines != null){
            mMap.clear();
//            markerKey.clear();
            polylines.clear();

            if(selectedEvent != null){
                if(!checkEvent(selectedEvent)){
                    selectedEvent = null;
                    tv1.setText("Click on a marker to event details");
                    tv2.setText("" );
                    imgV.setImageResource(R.drawable.ic_android_black_24dp);
                }
            }

            placeStartupMarkers();
            drawLines();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        familyData = ((MainActivity)getActivity()).familyData;
        settings = ((MainActivity)getActivity()).settings;
        view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        markerKey = new HashMap<String, String>();

        tv1 = (TextView) view.findViewById(R.id.mapTextViewName);
        tv2 = (TextView)  view.findViewById(R.id.mapTextViewSub);

        imgV = (ImageView) view.findViewById(R.id.eventIcon);
        footBox = (RelativeLayout) view.findViewById(R.id.InnerRelativeLayout);

        selectedEvent = null;
        selectedPerson = null;
        selectedPersonSpouce = null;

        footBox.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(selectedEvent != null && selectedPerson != null ){
                    ((MainActivity)getActivity()).setPersonPage(selectedEvent, selectedPerson);
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.example_menu, menu);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if(marker != null){
                    event tempEvent = familyData.eventMap.get(markerKey.get(marker.getId()));

                    person tempPerson = familyData.personMap.get(tempEvent.getPersonID());
                }
            }
        });
        user = ((MainActivity)getActivity()).familyData.userPerson;

        placeStartupMarkers();


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){

                selectedEvent = familyData.eventMap.get(markerKey.get(marker.getId()));

                selectedPerson = familyData.personMap.get(selectedEvent.getPersonID());

                if(selectedPerson.getSpouceID() != null) {
                    selectedPersonSpouce = familyData.personMap.get(selectedPerson.getSpouceID());
                }

                tv1.setText(selectedPerson.getFirstName() + " " + selectedPerson.getLastName());

                tv2.setText(selectedEvent.getEventType() + ": " + selectedEvent.getCity() + ", " + selectedEvent.getCountry() + " (" + selectedEvent.getYear() + ")" );

                if(selectedPerson.getGender().equals("m")){
                    imgV.setImageResource(R.drawable.ic_person_male_24dp);
                } else {
                    imgV.setImageResource(R.drawable.ic_person_female_24dp);
                }
                mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedEvent.getEventCoord()));

                marker.setIcon(BitmapDescriptorFactory.defaultMarker(selectedEvent.getEventColor()));

                drawLines();

              return true;
            }
        });

    }

    private void drawLines(){

        if(selectedEvent == null || selectedPerson == null){
            return;
        }

        for(Polyline line: polylines){
            line.remove();
        }
        polylines.clear();


        if(settings[0]){
            if(selectedPerson.eventsTemp.size() > 1){
                Iterator value = selectedPerson.eventsTemp.iterator();
                pEntry entry1 = (pEntry) value.next();
                pEntry entry2 = null;
                event event1 = null;
                event event2 = null;
                while(value.hasNext()){
                    entry2 = (pEntry) value.next();
                    event1 = familyData.eventMap.get(entry1.value);
                    event2 = familyData.eventMap.get(entry2.value);
                    drawLine(event1, event2, standardWidth, blue);
                    entry1 = entry2;
                }
            }
        }

        if(settings[1]){
            satisfySetting2();
        }

        if(settings[2]){
            if(selectedPerson != null && selectedPersonSpouce != null){
                if(selectedPerson.getTopEventID() != null && selectedPersonSpouce.getTopEventID() != null){
                    drawLine(selectedEvent, familyData.eventMap.get(selectedPersonSpouce.getTopEventID()), standardWidth, pink);
                }
            }
        }

    }

    public boolean checkEvent(event event) {
        person person = MainActivity.familyData.personMap.get(event.getPersonID());
        if (person == null) {
            return false;
        }
        if (person.getGender() != null){
            if (!settings[5] && person.getGender().equals("m")) {
                return false;
            } else if (!settings[6] && person.getGender().equals("f")) {
                return false;
            }
        }
        if(person.getFamilySide() == null){
            return true;
        }
        if(!settings[3] && person.getFamilySide().equals("dad")){
            return false;
        }
        else if(!settings[4] && person.getFamilySide().equals("mom")){
            return false;
        }
        return true;
    }



    public void placeStartupMarkers(){
        for(int i = 0; i < familyData.eventResponseG.getEvents().length; i++){
            String eventID = familyData.eventResponseG.getEvents()[i].getEventID();

            boolean good = checkEvent(familyData.eventMap.get(eventID));

            if(good) {
                miMarker = mMap.addMarker(new MarkerOptions().position(familyData.eventMap.get(eventID).getEventCoord()).title(familyData.eventMap.get(eventID).getCity() + ", " + familyData.eventMap.get(eventID).getCountry()).icon(BitmapDescriptorFactory.defaultMarker(familyData.eventMap.get(eventID).getEventColor())));
                markerKey.put(miMarker.getId(), eventID);
            }
        }
    }

    private void drawLine(event event1, event event2, int width, int pColor){

        person person1 = familyData.personMap.get(event1.getPersonID());

        person person2 = familyData.personMap.get(event2.getPersonID());

        if( (checkEvent(event1)) && (checkEvent(event2))) {
            polylines.add(mMap.addPolyline(new PolylineOptions().add(event1.getEventCoord(), event2.getEventCoord()).width(width).color(pColor)));
        }
    }

    public event recursiveSatisfySetting2(event tempEvent, int recursiveD){

        person tempPerson = MainActivity.familyData.personMap.get(tempEvent.getPersonID());

        if(recursiveD <= 3){
            recursiveD = 4;
        }

        if(tempPerson.getFatherID() != null && !tempPerson.getFatherID().equals("")){

            if(settings[3] && settings[5]) {

                //topEvent = familyData.eventMap.get(tempPerson.getFatherID());
                if(familyData.eventMap.get(familyData.getDad(tempPerson).getTopEventID()) != null) {
                    drawLine(tempEvent, recursiveSatisfySetting2(familyData.eventMap.get(familyData.getDad(tempPerson).getTopEventID()), recursiveD - 3), recursiveD, Color.BLUE);
                }
            }
        }

        if(tempPerson.getMotherID() != null && !tempPerson.getMotherID().equals("")){
            if(settings[4] && settings[6]) {
                if (familyData.eventMap.get(familyData.getMom(tempPerson).getTopEventID()) != null) {
                    drawLine(tempEvent, recursiveSatisfySetting2(familyData.eventMap.get(familyData.getMom(tempPerson).getTopEventID()), recursiveD - 3), recursiveD, Color.RED);
                }
            }
        }
        return tempEvent;
    }

    private void satisfySetting2(){

        event topEvent = familyData.eventMap.get(selectedPerson.getTopEventID());

        LatLng userBrithPlace = new LatLng(topEvent.getLatitude(),topEvent.getLongitude());

        recursiveSatisfySetting2(selectedEvent, standardWidth);
    }

    public void setSupportFragmentManager(FragmentManager supportFragmentManager) {
        this.supportFragmentManager = supportFragmentManager;
    }
}
