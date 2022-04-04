package com.example.mygeoquiz.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.mygeoquiz.fragments.LoginFragment;
import com.example.mygeoquiz.R;
import com.example.mygeoquiz.adapters.SectionStatePagerAdapter;
import com.example.mygeoquiz.fragments.mapFragment;
import com.example.sharedcodemodule.model.event;
import com.example.sharedcodemodule.model.person;

public class MainActivity extends AppCompatActivity {

    private SectionStatePagerAdapter mSectionsStatePageAdapter;
    private ViewPager mViewPager;

    private Button loginBtn;
    private Button signUpBtn;

    public LoginFragment loginFragment = null;
    private com.example.mygeoquiz.fragments.mapFragment mapFragment = null;

    public static com.example.mygeoquiz.struct.familyData familyData;

    private TextView mTextViewResult;

    private FragmentManager fragmentManager;

    public static boolean[] settings;

    public static event selectedEvent;

    public static person selectedPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(loginFragment == null && mapFragment == null) {

            setTitle("Family Map");

            settings = new boolean[7];

            for (int i = 0; i < settings.length; i++) {
                settings[i] = true;
            }

            fragmentManager = getSupportFragmentManager();
            loginFragment = new LoginFragment();
            fragmentManager.beginTransaction().add(R.id.container, loginFragment).commit();
        } else {
            fragmentManager.beginTransaction().replace(R.id.container, mapFragment).commit();
        }
    }

    public void setViewPager(com.example.mygeoquiz.struct.familyData familyDataTemp) {
        familyData = familyDataTemp;
        mapFragment = new mapFragment();
        fragmentManager.beginTransaction().replace(R.id.container, mapFragment).commit();
    }

    public void setPersonPage(event selectedEventIn, person selectedPersonIn){
        selectedEvent = selectedEventIn;
        selectedPerson = selectedPersonIn;
        Intent intent = new Intent(this, personActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settingIcon:
                launchSettings();
                return true;
            case R.id.searchIcon:
                launchSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void launchSettings(){
        Intent intent = new Intent(this, settingActivity.class);
        intent.putExtra("com/example/sharedcodemodule", settings);
        startActivityForResult(intent, 1);
    }

    public void launchSearch(){
        Intent intent2 = new Intent(this, searchActivity.class);
        intent2.putExtra("com/example/sharedcodemodule", settings);
        startActivityForResult(intent2, 4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
           super.onActivityResult(requestCode, resultCode, data);

           if(requestCode == 1){
               if(resultCode == RESULT_OK){
                   String result = data.getStringExtra("result");
                   if(result != null && result.equals("true")){
                       Intent intent = getIntent();
                       finish();
                       startActivity(intent);
                   }
               }
           }

           if(requestCode == 2){
               if(resultCode == RESULT_OK){
                   String result = data.getStringExtra("result");
                   if(result != null && !result.equals("")){
                       Intent intent3 = new Intent(this, eventActivity.class);
                       intent3.putExtra("com/example/sharedcodemodule", result);
                       startActivityForResult(intent3, 3);
                   }
               }
           }
        if(requestCode == 3){
            if(resultCode == RESULT_OK){
                String result = data.getStringExtra("result");
                if(result != null && !result.equals("")){
                    Intent intent = new Intent(this, personActivity.class);
                    intent.putExtra("com/example/sharedcodemodule", result);
                    startActivityForResult(intent, 2);
                }
            }
        }

        if(requestCode == 4){
            if(resultCode == RESULT_OK){
                String id = data.getStringExtra("id");
                String type = data.getStringExtra("type");
                if(type != null && !type.equals("") && id != null && !id.equals("")){

                    if(type.equals("person")){
                        Intent intent = new Intent(this, personActivity.class);
                        intent.putExtra("com/example/sharedcodemodule", id);
                        startActivityForResult(intent, 2);
                    } else if(type.equals("event")){
                        Intent intent3 = new Intent(this, eventActivity.class);
                        intent3.putExtra("com/example/sharedcodemodule", id);
                        startActivityForResult(intent3, 3);
                    }
                }
            }
        }

    }
}
