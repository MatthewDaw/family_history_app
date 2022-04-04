package com.example.mygeoquiz;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.mygeoquiz.activities.MainActivity;
import com.example.mygeoquiz.struct.pEntry;
import com.example.mygeoquiz.struct.recyclerViewData;
import com.example.sharedcodemodule.model.person;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mActivity = null;

    @Before
    public void setUp() throws Exception{
        mActivity = mActivityTestRule.getActivity();

        mActivity.loginFragment.setServerHostString("10.0.2.2");
        mActivity.loginFragment.setServerPortString("8080");
        mActivity.loginFragment.setUserNameString("sheila");
        mActivity.loginFragment.setPasswordString("parker");

        mActivity.loginFragment.setFirstNameString("cows");
        mActivity.loginFragment.setLastNameString("bulls");
        mActivity.loginFragment.setEmailString("bill");
        mActivity.loginFragment.setMaleRadioBoolean(true);
        mActivity.loginFragment.setFemaleRadioBoolean(false);

        mActivity.loginFragment.setGetFamData(false);
    }

    @Test
    public void getPersonsFail() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();

        assertNull(mActivity.familyData);
    }


    @Test
    public void loginPass() throws Exception{

        mActivity.loginFragment.setGetFamData(false);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();

        assertNotNull(mActivity.loginFragment.loginResponse);

        if(mActivity.loginFragment.loginResponse != null){
            assertTrue(mActivity.loginFragment.loginResponse.getSuccess());
            assertEquals(mActivity.loginFragment.loginResponse.getUserName(), "sheila");
            assertEquals(mActivity.loginFragment.loginResponse.getPersonID(), "Sheila_Parker");
        }
    }

    @Test
    public void loginFail1() throws Exception{

        mActivity.loginFragment.setServerHostString("10");

        mActivity.loginFragment.setGetFamData(false);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();
        assertNull(mActivity.loginFragment.loginResponse);
        assertEquals(mActivity.loginFragment.androidResponse, "Error connecting to database");
    }

    @Test
    public void loginFail2() throws Exception{

        mActivity.loginFragment.setUserNameString("sheilagarbageplapla");

        mActivity.loginFragment.setGetFamData(false);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();
        assertNull(mActivity.loginFragment.loginResponse);
        assertEquals(mActivity.loginFragment.androidResponse,  "no account for given username");
    }

    @Test
    public void loginFail4() throws Exception{

        mActivity.loginFragment.setPasswordString("parkerplaplaplushpiplushpi");

        mActivity.loginFragment.setGetFamData(false);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();
        assertNull(mActivity.loginFragment.loginResponse);
        assertEquals(mActivity.loginFragment.androidResponse,  "no account for given username");
    }

    @Test
    public void registerPass() throws Exception{

        UUID gfg1 = UUID.randomUUID();

        mActivity.loginFragment.setUserNameString(gfg1.toString());

        mActivity.loginFragment.setGetFamData(false);
        mActivity.loginFragment.signUp();

        mActivity.loginFragment.countDownLatch.await();
        assertNotNull(mActivity.loginFragment.registerResponse);

        if(mActivity.loginFragment.registerResponse != null){
            assertTrue(mActivity.loginFragment.registerResponse.getSuccess());
        }
    }

    @Test
    public void registerFail() throws Exception{



        mActivity.loginFragment.setGetFamData(false);
        mActivity.loginFragment.signUp();

        mActivity.loginFragment.countDownLatch.await();
        assertNull(mActivity.loginFragment.registerResponse);

        assertEquals(mActivity.loginFragment.androidResponse, "Register Failed \n (username taken/already exists)");
    }

    @Test
    public void getEventFail() throws Exception {

        mActivity.loginFragment.setPasswordString("parkerplaplaplushpiplushpi");

        mActivity.loginFragment.setGetFamData(false);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();

        assertEquals(mActivity.loginFragment.androidResponse, "no account for given username");

    }



    @Test
    public void getPersonsPass() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();

        assertNotNull(mActivity.familyData);

        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
        }
    }


    @Test
    public void getEventPass() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();

        assertNotNull(mActivity.familyData);

        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.eventMap);
        }
    }

    @Test
    public void calculateFamilyRelations() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Sheila_Parker");
                assertEquals(mActivity.familyData.getSpouce(person).getPersonID(), "Davis_Hyer");
                assertEquals(mActivity.familyData.getMom(person).getPersonID(), "Betty_White");
                assertEquals(mActivity.familyData.getDad(person).getPersonID(), "Blaine_McGary");

                com.example.sharedcodemodule.model.person person3 = mActivity.familyData.personMap.get("Ken_Rodham");
                assertEquals(mActivity.familyData.getSpouce(person3).getPersonID(), "Mrs_Rodham");

                Iterator value = person3.familyList.iterator();
                pEntry entry1 = null;

                int i = 0;

                while (value.hasNext()) {
                    entry1 = (pEntry) value.next();
                    com.example.sharedcodemodule.model.person person4 = MainActivity.familyData.personMap.get(entry1.value);

                    if(i == 0){
                        assertEquals(person4.getPersonID(), "Mrs_Rodham");
                    }
                    if(i == 1){
                        assertEquals(person4.getPersonID(), "Blaine_McGary");
                    }
                    i++;
                }
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void calculateFamilyRelationsFail() throws Exception {

        mActivity.loginFragment.setUserNameString("patrick");
        mActivity.loginFragment.setPasswordString("spencer");

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                assertNull(mActivity.familyData.personMap.get("Sheila_Parker"));
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void correctSortingEventPass() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Sheila_Parker");
                mActivity.settings[0] = true;
                mActivity.settings[1] = true;
                mActivity.settings[2] = true;
                mActivity.settings[3] = true;
                mActivity.settings[4] = true;
                mActivity.settings[5] = true;
                mActivity.settings[6] = true;

                List<String> events = person.getEvents();

                assertEquals(events.size(),5);

                for(int i = 0; i < events.size(); i++){
                    if(i == 0){
                        assertEquals(events.get(i), "Sheila_Birth");
                    }
                    if(i == 1){
                        assertEquals(events.get(i), "Sheila_Marriage");
                    }
                    if(i == 2){
                        assertEquals(events.get(i), "Sheila_Asteroids");
                    }
                    if(i == 3){
                        assertEquals(events.get(i), "Other_Asteroids");
                    }
                    if(i == 4){
                        assertEquals(events.get(i), "Sheila_Death");
                    }

                }
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void correctSortingPersonPass() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Sheila_Parker");
                mActivity.settings[0] = true;
                mActivity.settings[1] = true;
                mActivity.settings[2] = true;
                mActivity.settings[3] = true;
                mActivity.settings[4] = true;
                mActivity.settings[5] = true;
                mActivity.settings[6] = true;

                List<String> events = person.getPersonData();

                assertEquals(events.size(),3);

                for(int i = 0; i < events.size(); i++){
                    if(i == 0){
                        assertEquals(events.get(i), "Blaine_McGary");
                    }
                    if(i == 1){
                        assertEquals(events.get(i), "Davis_Hyer");
                    }
                    if(i == 2){
                        assertEquals(events.get(i), "Betty_White");
                    }

                }
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void correctSortingPersonFail() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.setUserNameString("patrick");

        mActivity.loginFragment.setPasswordString("spencer");

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Happy_Birthday");
                mActivity.settings[0] = false;
                mActivity.settings[1] = false;
                mActivity.settings[2] = false;
                mActivity.settings[3] = false;
                mActivity.settings[4] = false;
                mActivity.settings[5] = false;
                mActivity.settings[6] = false;

                List<String> events = person.getPersonData();

                assertEquals(events.size(),2);

                for(int i = 0; i < events.size(); i++){
                    if(i == 0){
                        assertEquals(events.get(i), "Golden_Boy");
                    }
                    if(i == 1){
                        assertEquals(events.get(i), "Patrick_Spencer");
                    }
                }
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void correctSortingEventsFail() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Ken_Rodham");
                mActivity.settings[0] = true;
                mActivity.settings[1] = true;
                mActivity.settings[2] = true;
                mActivity.settings[3] = true;
                mActivity.settings[4] = true;
                mActivity.settings[5] = false;
                mActivity.settings[6] = true;

                List<String> events = person.getEvents();
                assertEquals(events.size(),0);
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void correctFilterPass() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();


        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();


        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Sheila_Parker");
                mActivity.settings[0] = true;
                mActivity.settings[1] = true;
                mActivity.settings[2] = true;
                mActivity.settings[3] = true;
                mActivity.settings[4] = true;
                mActivity.settings[5] = true;
                mActivity.settings[6] = true;
                List<String> events = person.getEvents();
                assertEquals(events.size(),5);
            }
        } else {
            assertTrue(false);
        }
    }


    @Test
    public void filterEventsFail() throws Exception {

        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();

        if(mActivity.familyData != null){
            assertNotNull(mActivity.familyData.personMap);
            if(mActivity.familyData.personMap != null){
                person person = mActivity.familyData.personMap.get("Sheila_Parker");
                mActivity.settings[0] = false;
                mActivity.settings[1] = false;
                mActivity.settings[2] = false;
                mActivity.settings[3] = false;
                mActivity.settings[4] = false;
                mActivity.settings[5] = false;
                mActivity.settings[6] = false;

                List<String> events = person.getEvents();
                assertNotEquals(events.size(),5);
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void searchPass() throws Exception {
        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();

        mActivity.familyData.getSearchResults("au", MainActivity.settings);

        ArrayList<recyclerViewData> recycleDList = mActivity.familyData.recycleDList;

        if(recycleDList.size() == 3){
            assertEquals(3, recycleDList.size());

            for(int i = 0; i < recycleDList.size(); i++){
                if(i==0){
                    assertEquals("Mrs_Jones_Surf", recycleDList.get(i).getId());
                }
                if(i==1){
                    assertEquals("Sheila_Birth", recycleDList.get(i).getId());
                }
                if(i==2){
                    assertEquals("Jones_Frog", recycleDList.get(i).getId());
                }
            }
        } else {
            assertTrue(false);
        }
    }

    @Test
    public void searchFail() throws Exception {
        mActivity.loginFragment.setGetFamData(true);

        mActivity.loginFragment.logIn();

        mActivity.loginFragment.countDownLatch.await();
        mActivity.loginFragment.countDownLatch2.await();

        mActivity.familyData.getSearchResults("", MainActivity.settings);

        ArrayList<recyclerViewData> recycleDList = mActivity.familyData.recycleDList;
        assertEquals(0, recycleDList.size());
    }

        @After
    public void tearDown() throws Exception{
        mActivity = null;
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.mygeoquiz", appContext.getPackageName());
    }
}
