package com.example.mygeoquiz;

import com.example.mygeoquiz.activities.MainActivity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleUnitTest {



    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }





    @Test
    public void loginSuccess() throws InterruptedException {
        MainActivity mainActivity = new MainActivity();

        mainActivity.loginFragment.logIn();

//        loginFragment.somethingOutThere();

//        serverProxy serverProxy = new serverProxy();
//
//        serverProxy.login("10.0.2.2", "8080", "sheila", "parker");
//
//        serverProxy.countDownLatch.await();

//        loginFragment.setServerHostString("10.0.2.2");
//        loginFragment.setServerPortString("8080");
//        loginFragment.setUserNameString("sheila");
//        loginFragment.setPasswordString("parker");
//
//        loginFragment.setGetFamData(false);
//
//        loginFragment.setGetFamData(false);

        //loginFragment.logIn();

//        loginFragment.countDownLatch.await();
//
//        assertTrue(loginFragment.loginResponse.getSuccess());
//        assertEquals(loginFragment.loginResponse.getUserName(), "sheila");
//        assertEquals(loginFragment.loginResponse.getPersonID(), "Sheila_Parker");

    }

}
