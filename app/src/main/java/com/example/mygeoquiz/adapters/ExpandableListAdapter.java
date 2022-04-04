package com.example.mygeoquiz.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mygeoquiz.R;
import com.example.mygeoquiz.activities.MainActivity;
import com.example.mygeoquiz.activities.personActivity;
import com.example.sharedcodemodule.model.event;
import com.example.sharedcodemodule.model.person;

import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private LinkedHashMap<String, List<String>> mStringListHashMap;
    private String[] mListHeaderGroup;

    public ExpandableListAdapter(LinkedHashMap<String, List<String>> stringListHashMap){
        mStringListHashMap = stringListHashMap;
        mListHeaderGroup = mStringListHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getGroupCount() {
        return mListHeaderGroup.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mStringListHashMap.get(mListHeaderGroup[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mListHeaderGroup[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mStringListHashMap.get(mListHeaderGroup[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandable_head, parent, false);
        }
        TextView textView = convertView.findViewById(R.id.expandHead);

        textView.setText(String.valueOf(getGroup(groupPosition)));

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.expandablelist_child, parent, false);
        }

        TextView textView1 = convertView.findViewById(R.id.expand_sub1);
        TextView textView2 = convertView.findViewById(R.id.expand_sub2);

        ImageView imgV = (ImageView) convertView  .findViewById(R.id.expandListIcon);

        if(String.valueOf(getGroup(groupPosition)).equals("Life Events")){
            event event = MainActivity.familyData.eventMap.get(String.valueOf(getChild(groupPosition, childPosition)));
            textView1.setText(String.valueOf(event.getEventType()));
            textView2.setText(personActivity.showingPerson.getFirstName() + " " + MainActivity.selectedPerson.getLastName());
            imgV.setImageResource(R.drawable.ic_place_black_24dp);
            imgV.setColorFilter((int) event.getEventColor2());
        }

        else if(String.valueOf(getGroup(groupPosition)).equals("Family")){
            person person = MainActivity.familyData.personMap.get(String.valueOf(getChild(groupPosition, childPosition)));
            person.getPersonID();
            textView1.setText(String.valueOf(person.getFirstName() + " " + person.getLastName()));
            textView2.setText(personActivity.showingPerson.getRelation(person.getPersonID()));
            imgV.setImageResource(R.drawable.ic_person_male_24dp);
            imgV.setColorFilter(Color.BLUE);
            if(person.getGender().equals("f")){
                imgV.setImageResource(R.drawable.ic_person_female_24dp);
                imgV.setColorFilter(Color.rgb(255,128,164));
            }
        }

    return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
