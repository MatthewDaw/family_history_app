package com.example.mygeoquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mygeoquiz.R;
import com.example.mygeoquiz.adapters.recycleAdapter;

public class searchActivity extends AppCompatActivity {

    private EditText searchField;

    private RecyclerView mRecyclerView;
    private recycleAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView clearSearch = (ImageView) findViewById(R.id.clearSearch);

        searchField = (EditText) findViewById(R.id.searchField);

        mRecyclerView = findViewById(R.id.recycleView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);

        clearSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                searchField.setText("");
            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                MainActivity.familyData.getSearchResults(searchField.getText().toString(), MainActivity.settings);
                mAdapter = new recycleAdapter(MainActivity.familyData.recycleDList);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setOnItemClickListener(new recycleAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("id", MainActivity.familyData.recycleDList.get(position).getId());
                        resultIntent.putExtra("type", MainActivity.familyData.recycleDList.get(position).getType());
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }
                });

            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

}