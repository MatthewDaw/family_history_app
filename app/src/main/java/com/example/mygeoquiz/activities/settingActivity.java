package com.example.mygeoquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mygeoquiz.R;

public class settingActivity extends AppCompatActivity {

    public boolean[] settingsSub;

    private Switch s1;
    private Switch s2;
    private Switch s3;
    private Switch s4;
    private Switch s5;
    private Switch s6;
    private Switch s7;

    private LinearLayout returnBTN;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Intent resultIntent = new Intent();

        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                resultIntent.putExtra("result", settingsSub);
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                resultIntent.putExtra("result", settingsSub);
                setResult(RESULT_OK, resultIntent);
                finish();
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        setTitle("Family Map: Settings");

        Intent intent = getIntent();
        settingsSub = intent.getBooleanArrayExtra("com/example/sharedcodemodule");

        s1 = (Switch) findViewById(R.id.switch1);
        s2 = (Switch) findViewById(R.id.switch2);
        s3 = (Switch) findViewById(R.id.switch3);
        s4 = (Switch) findViewById(R.id.switch4);
        s5 = (Switch) findViewById(R.id.switch5);
        s6 = (Switch) findViewById(R.id.switch6);
        s7 = (Switch) findViewById(R.id.switch7);

        s1.setChecked(settingsSub[0]);
        s2.setChecked(settingsSub[1]);
        s3.setChecked(settingsSub[2]);
        s4.setChecked(settingsSub[3]);
        s5.setChecked(settingsSub[4]);
        s6.setChecked(settingsSub[5]);
        s7.setChecked(settingsSub[6]);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[0] = isChecked;
            }
        });
        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[1] = isChecked;
            }
        });
        s3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[2] = isChecked;
            }
        });
        s4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[3] = isChecked;
            }
        });
        s5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[4] = isChecked;
            }
        });
        s6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[5] = isChecked;
            }
        });
        s7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.settings[6] = isChecked;
            }
        });
        returnBTN = (LinearLayout) findViewById(R.id.returnBTN);
        returnBTN.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result", "true");
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
