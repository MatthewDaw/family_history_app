package com.example.mygeoquiz.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygeoquiz.R;
import com.example.mygeoquiz.activities.MainActivity;
import com.example.mygeoquiz.struct.recyclerViewData;
import com.example.sharedcodemodule.model.event;
import com.example.sharedcodemodule.model.person;

import java.util.ArrayList;

public class recycleAdapter extends RecyclerView.Adapter<recycleAdapter.viewHolder> {

    private ArrayList<recyclerViewData> recycleDList;

    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class viewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public viewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.expandListIcon);
            mTextView1 = itemView.findViewById(R.id.expand_sub1);
            mTextView2 = itemView.findViewById(R.id.expand_sub2);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public recycleAdapter(ArrayList<recyclerViewData> data){
        recycleDList = data;
    }


    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        viewHolder vHolder = new viewHolder(v, mListener);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        recyclerViewData currentItem = recycleDList.get(position);

        //sharedCodeModule.testClass myclass;

        if(MainActivity.familyData.personMap.containsKey(currentItem.getId())){
            person tempPerson = MainActivity.familyData.personMap.get(currentItem.getId());
            if(tempPerson.getGender().equals("f")){
                holder.mImageView.setImageResource(R.drawable.ic_person_female_24dp);
                holder.mImageView.setColorFilter(Color.rgb(255,128,164));
            } else {
                holder.mImageView.setImageResource(R.drawable.ic_person_male_24dp);
                holder.mImageView.setColorFilter(Color.BLUE);
            }
            holder.mTextView1.setText(tempPerson.getFirstName() + " " + tempPerson.getLastName());
            holder.mTextView2.setText("");
        } else {
            event event = MainActivity.familyData.eventMap.get(currentItem.getId());
            holder.mImageView.setImageResource(R.drawable.ic_place_black_24dp);
            holder.mImageView.setColorFilter((int) event.getEventColor2());

            holder.mTextView1.setText(event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")");
            holder.mTextView2.setText(MainActivity.familyData.personMap.get(event.getPersonID()).getFirstName() + " " + MainActivity.familyData.personMap.get(event.getPersonID()).getLastName());
        }

    }

    @Override
    public int getItemCount() {
        return recycleDList.size();
    }
}
