package com.example.mygeoquiz.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mygeoquiz.R;
import com.example.mygeoquiz.activities.MainActivity;
import com.example.sharedcodemodule.hanlders.deserializer;
import com.example.sharedcodemodule.request.LoginRequest;
import com.example.sharedcodemodule.request.registerRequest;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LoginFragment extends Fragment {

    public String androidResponse;

    private deserializer decereal = new deserializer();
    private String jsonRequest;
//private final String baseURl = "http://10.0.2.2:8080"
    private final String baseURl = "http://";
    private String authToken;

    private String usersPersonID;

    private EditText serverHostInputv;
    private EditText serverPortInputv;
    private EditText userNameInputv;
    private EditText passwordInputv;
    private EditText firstNameInputv;
    private EditText lastNameInputv;
    private EditText emailInputv;
    private RadioButton maleRadiov;
    private RadioButton femaleRadiov;


    private String serverHostString;
    private String serverPortString;
    private String userNameString;
    private String passwordString;
    private String firstNameString;
    private String lastNameString;
    private String emailString;
    private Boolean maleRadioBoolean;
    private Boolean femaleRadioBoolean;

    private Button loginBtn;
    private Button signUpBtn;

    public com.example.sharedcodemodule.response.loginResponse loginResponse;
    public com.example.sharedcodemodule.response.registerResponse registerResponse;

    private com.example.sharedcodemodule.response.eventResponse eventResponse;
    private com.example.sharedcodemodule.response.personResponse personResponse;
    private com.example.mygeoquiz.struct.familyData familyData;

    private boolean getFamData = true;

    public final CountDownLatch countDownLatch = new CountDownLatch(1);

    public final CountDownLatch countDownLatch2 = new CountDownLatch(1);


    public void updateBTNs() {

        serverHostString = serverHostInputv.getText().toString();
        serverPortString = serverPortInputv.getText().toString();
        userNameString = userNameInputv.getText().toString();
        passwordString = passwordInputv.getText().toString();

        firstNameString = firstNameInputv.getText().toString();
        lastNameString = lastNameInputv.getText().toString();
        emailString = emailInputv.getText().toString();

        boolean mRad = maleRadiov.isChecked();
        boolean fRad = femaleRadiov.isChecked();

        if(!serverHostString.equals("") && !serverHostString.equals("") && !serverPortString.equals("") && !userNameString.equals("") && !passwordString.equals("")){
            loginBtn.setEnabled(true);
        } else {
            loginBtn.setEnabled(false);
        }

        if(!serverHostString.equals("") && !serverHostString.equals("") && !serverPortString.equals("") && !userNameString.equals("") && !passwordString.equals("") && !firstNameString.equals("") && !lastNameString.equals("") && !emailString.equals("") && (mRad || fRad)){
            signUpBtn.setEnabled(true);
        } else {
            signUpBtn.setEnabled(false);
        }

    }

    public void getFamilyInfo(){

        OkHttpClient client = new OkHttpClient();

        String url = baseURl + serverHostString + ":" + serverPortString + "/event";
        Request request = new Request.Builder().url(url).get()
                .addHeader("Authorization", authToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                androidResponse = "Error connecting to database";
                e.printStackTrace();

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    eventResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.eventResponse.class);


                    //start of person api call
                    OkHttpClient client = new OkHttpClient();

                    String url = baseURl + serverHostString + ":" + serverPortString + "/person";
                    Request request = new Request.Builder().url(url).get()
                            .addHeader("Authorization", authToken)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            androidResponse = "Error connecting to database";
                            e.printStackTrace();

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                final String myResponse = response.body().string();
                                personResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.personResponse.class);
                                familyData = new com.example.mygeoquiz.struct.familyData(personResponse, eventResponse, usersPersonID);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getActivity(), "Welcome " + familyData.userPerson.getFirstName() + " " + familyData.userPerson.getLastName(), Toast.LENGTH_LONG).show();
                                        ((MainActivity)getActivity()).setViewPager(familyData);
                                        countDownLatch2.countDown();
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }

    public void signUp(){
        String gender = "f";
        if(maleRadioBoolean){
            gender = "m";
        }

        registerRequest registerRequest = new registerRequest(userNameString, passwordString, emailString, firstNameString, lastNameString, gender);
        String jsonRequest = decereal.serialize(registerRequest);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonRequest);


        String url = baseURl + serverHostString + ":" + serverPortString + "/user/register";

        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                androidResponse = "Error connecting to database";
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), androidResponse , Toast.LENGTH_LONG).show();
                    }
                });
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    registerResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.registerResponse.class);
                    usersPersonID = registerResponse.getPersonID();
                    authToken = registerResponse.getAuthToken();

                    if(getFamData) {
                        getFamilyInfo();
                    }

                } else {
                    final String myResponse = response.body().string();
                    com.example.sharedcodemodule.response.registerResponse registerResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.registerResponse.class);
                    if (registerResponse.getMessage().equals("error Username is not unique")) {
                        androidResponse = "Register Failed \n (username taken/already exists)";
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), androidResponse, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                countDownLatch.countDown();
            }
        });
    }

    public void logIn(){
        LoginRequest loginRequest = new LoginRequest(userNameString, passwordString);
        String jsonRequest = decereal.serialize(loginRequest);

        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonRequest);

        String url = baseURl + serverHostString + ":" + serverPortString + "/user/login";

        Request request = new Request.Builder().url(url).post(body).build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                androidResponse = "Error connecting to database";
                e.printStackTrace();
                countDownLatch.countDown();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), androidResponse , Toast.LENGTH_LONG).show();
                    }
                });
                countDownLatch.countDown();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {



                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    loginResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.loginResponse.class);
                    usersPersonID = loginResponse.getPersonID();
                    authToken = loginResponse.getAuthToken();
                    if(getFamData) {
                        getFamilyInfo();
                    }
                    countDownLatch.countDown();
                } else {
                    final String myResponse = response.body().string();
                    com.example.sharedcodemodule.response.registerResponse registerResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.registerResponse.class);
                    if (registerResponse.getMessage().equals("error Invalid username or password")) {
                        androidResponse = "no account for given username";
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), androidResponse, Toast.LENGTH_LONG).show();
                        }
                    });
                    countDownLatch.countDown();
                }
            }
        });


    }

    public View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.login_form, container, false);

        loginBtn = (Button) view.findViewById(R.id.loginBtn);
        signUpBtn = (Button) view.findViewById(R.id.signUpBtn);

        loginBtn.setEnabled(false);
        signUpBtn.setEnabled(false);

        maleRadiov = (RadioButton) view.findViewById(R.id.maleRadio);
        femaleRadiov = (RadioButton) view.findViewById(R.id.femaleRadio);

        maleRadiov.setChecked(false);
        femaleRadiov.setChecked(false);

        serverHostInputv = (EditText) view.findViewById(R.id.serverHostInput);
        serverPortInputv = view.findViewById(R.id.serverPortInput);
        userNameInputv = view.findViewById(R.id.userNameInput);
        passwordInputv = view.findViewById(R.id.passwordInput);

        firstNameInputv = view.findViewById(R.id.firstNameInput);
        lastNameInputv = view.findViewById(R.id.lastNameInput);
        emailInputv = view.findViewById(R.id.emailInput);


        View.OnClickListener maleRadioListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                femaleRadiov.setChecked(false);
                maleRadioBoolean = true;
                femaleRadioBoolean = false;
                updateBTNs();
            }
        };
        maleRadiov.setOnClickListener(maleRadioListener);
        View.OnClickListener femaleRadioListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maleRadioBoolean = false;
                femaleRadioBoolean = true;
                maleRadiov.setChecked(false);
                updateBTNs();
            }
        };
        femaleRadiov.setOnClickListener(femaleRadioListener);

        serverHostInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        serverPortInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        userNameInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        passwordInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        firstNameInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        lastNameInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });

        emailInputv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                updateBTNs();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });



        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view2){

                logIn();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view2){
                signUp();
            }
        });
        return view;
    }

    private class DownloadTask extends AsyncTask<URL, Integer, Long> {

        @Override
        protected Long doInBackground(URL... urls) {

            firstNameString = "cows";

            return null;
        }
    }

    private class exampleAsync extends AsyncTask<Integer, Integer, String> {

        @Override
        protected String doInBackground(Integer... integers) {
            firstNameString = "cows";
            return "good";
        }

        @Override
        protected void onPostExecute(String s){
            lastNameString = "bulls";
        }

    }

    public void somethingOutThere(){
        new exampleAsync().execute();
        System.out.println("Good");
    }

    public void setServerHostString(String serverHostString) {
        this.serverHostString = serverHostString;
    }

    public void setServerPortString(String serverPortString) {
        this.serverPortString = serverPortString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString = passwordString;
    }

    public void setFirstNameString(String firstNameString) {
        this.firstNameString = firstNameString;
    }

    public void setLastNameString(String lastNameString) {
        this.lastNameString = lastNameString;
    }

    public void setEmailString(String emailString) {
        this.emailString = emailString;
    }

    public void setMaleRadioBoolean(Boolean maleRadioBoolean) {
        this.maleRadioBoolean = maleRadioBoolean;
    }

    public void setFemaleRadioBoolean(Boolean femaleRadioBoolean) {
        this.femaleRadioBoolean = femaleRadioBoolean;
    }

    public void setGetFamData(boolean getFamData) {
        this.getFamData = getFamData;
    }

}
