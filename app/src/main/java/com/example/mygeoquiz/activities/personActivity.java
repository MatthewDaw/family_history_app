package com.example.mygeoquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygeoquiz.R;
import com.example.mygeoquiz.adapters.ExpandableListAdapter;
import com.example.sharedcodemodule.model.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.example.sharedcodemodule.model.person;

public class personActivity extends AppCompatActivity {

    private ExpandableListView expandableList;
    private List<String> listGroup;
    private HashMap<String, List<String>> listItem;

    private person selectedPerson;
    private event selectedEvent;

    private TextView firstNameView;
    private TextView lastNameView;
    private TextView genderView;

    public static person showingPerson;

    private event showingEvent;

    private ArrayList<person> familyList;

    private LinkedHashMap<String, List<String>> expandListData;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent resultIntent = new Intent();

        switch (item.getItemId()){
            case android.R.id.home:

                onBackPressed();
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                setResult(RESULT_OK, resultIntent);
                finish();
                return super.onOptionsItemSelected(item);
        }

    }

    private void resetView(){

        firstNameView.setText(showingPerson.getFirstName());
        lastNameView.setText(showingPerson.getLastName());

        if(showingPerson.getGender().equals("m")){
            genderView.setText("Male");
        } else {
            genderView.setText("Female");
        }


        listGroup = new ArrayList<>();
        listItem = new HashMap<>();
        expandListData = new LinkedHashMap<String, List<String>>();

        expandListData.clear();

        getEventData();

        getPersonData();

        ExpandableListAdapter adapter = new ExpandableListAdapter(expandListData);
        expandableList.setAdapter(adapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                List<String> somethign = expandListData.get(groupPosition);

                if(groupPosition == 0) {
                    showingEvent  = MainActivity.familyData.eventMap.get(expandListData.get("Life Events").get(childPosition));

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("result", showingEvent.getEventID());
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

                if(groupPosition == 1) {
                    showingPerson = MainActivity.familyData.personMap.get(expandListData.get("Family").get(childPosition));

                    resetView();
                }
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("FAMILY MAP");
        setContentView(R.layout.activity_person);

        expandableList = findViewById(R.id.expandableList);

        familyList = new ArrayList<person>();

        firstNameView = findViewById(R.id.firstNameField);
        lastNameView = findViewById(R.id.lastNameField);
        genderView = findViewById(R.id.genderField);

        Intent intent = getIntent();
        String personID = intent.getStringExtra("com/example/sharedcodemodule");

        if(personID != null && personID != ""){
            showingPerson = MainActivity.familyData.personMap.get(personID);
        } else {
            showingPerson = MainActivity.selectedPerson;
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        resetView();

    }


    private void getEventData(){
        List<String> lifeEvents = showingPerson.getEvents();
        if(showingPerson.eventsTemp != null) {
            expandListData.put("Life Events", lifeEvents);
        }
    }

    public void getPersonData(){
        List<String> family = showingPerson.getPersonData();
        if(showingPerson.familyList != null) {
            expandListData.put("Family", family);
        }
    }
}
