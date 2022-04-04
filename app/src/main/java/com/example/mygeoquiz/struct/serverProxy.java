package com.example.mygeoquiz.struct;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.sharedcodemodule.hanlders.deserializer;
import com.example.sharedcodemodule.request.LoginRequest;
import com.example.sharedcodemodule.request.registerRequest;


public class serverProxy {

    private String androidResponse;

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

    public boolean success;
    public String returnString;

    public CountDownLatch countDownLatch = new CountDownLatch(1);


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
                                returnString = "Welcome " + familyData.userPerson.getFirstName() + " " + familyData.userPerson.getLastName();

                                //MainActivity.setViewPager

                            }
                        }
                    });
                }
            }
        });
    }


    public void login(String serverHostStringi, String serverPortStringi, String userNameStringi, String passwordStringi) {

        serverHostString = serverHostStringi;
        serverPortString = serverPortStringi;
        userNameString = userNameStringi;
        passwordString = passwordStringi;

    LoginRequest loginRequest = new LoginRequest(userNameString, passwordString);
    String jsonRequest = decereal.serialize(loginRequest);

    OkHttpClient client = new OkHttpClient();

    RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonRequest);

    String url = baseURl + serverHostString + ":" + serverPortString + "/user/login";

    Request request = new Request.Builder().url(url).post(body).build();



        client.newCall(request).enqueue(new Callback() {

        @Override
        public void onFailure (Call call, IOException e){
            androidResponse = "Error connecting to database";
            success = false;
            e.printStackTrace();
        }

        @Override
        public void onResponse (Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                final String myResponse = response.body().string();
                loginResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.loginResponse.class);
                usersPersonID = loginResponse.getPersonID();
                authToken = loginResponse.getAuthToken();
                if (getFamData) {
                    getFamilyInfo();
                }

            } else {
                final String myResponse = response.body().string();
                com.example.sharedcodemodule.response.registerResponse registerResponse = decereal.deserialize(myResponse, com.example.sharedcodemodule.response.registerResponse.class);
                if (registerResponse.getMessage().equals("error Invalid username or password")) {
                    androidResponse = "no account for given username";
                }
                returnString = androidResponse;
            }
        }
    });

}

    public void signUp(String serverHostStringi, String serverPortStringi, String userNameStringi, String passwordStringi, String firstNameStringi, String lastNameStringi, String emailStringi, boolean maleRadioBooleani){

        serverHostString = serverHostStringi;
        serverPortString = serverPortStringi;
        userNameString = userNameStringi;
        passwordString = passwordStringi;

        firstNameString = firstNameStringi;
        lastNameString = lastNameStringi;
        emailString = emailStringi;
        maleRadioBoolean = maleRadioBooleani;

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
                returnString = androidResponse;
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
                    returnString = androidResponse;
                }
            }
        });
    }


}
