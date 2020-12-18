package com.oo115.myapplication;


import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.NavigationViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ProfileTest {

//    @Rule
//    public FragmentTestRule<HomeFragment> mFragmentTestRule = new FragmentTestRule<>(HomeFragment.class);

    private Random rand = new Random();

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Rule
    public ActivityTestRule<RegisterAndLoginActivity> mActivityRule_login =
            new ActivityTestRule<>(RegisterAndLoginActivity.class);


    @Rule
    public FragmentTestRule<RegisterFragment> mFragmentTestRule = new FragmentTestRule<>(RegisterFragment.class);


    @Before
    public void setUp() throws Exception {
        Intents.init();


    }

    @After
    public void cleanUp() {
        Intents.release();
    }


    // user can visit the profile page
    @Test
    public void drawer_profile_test() {

        mActivityRule.launchActivity(new Intent());
        onView(withId(R.id.btnQuickWork)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout))
                .perform(click());


        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile));
        onView(withText("Sex")).check(matches(isDisplayed()));

    }

//testing users age


    @Test
    public void profile_ageTest() {
        int year = 2020;
        int monthOfYear = 03;
        int dayOfMonth = 20;

        mActivityRule_login.launchActivity(new Intent());
        onView(withId(R.id.etEmail)).perform(typeText("testingtesting@yahoo.com"), closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText("123456"), closeSoftKeyboard());

        onView(withId(R.id.btn_Signin))
                .perform(click());
        intended(hasComponent(MainActivity.class.getName()));
        onView(withText("Quick Workout")).check(matches(isDisplayed()));

        onView(withId(R.id.btnQuickWork)).check(matches(isDisplayed()));
        onView(withId(R.id.drawer_layout))
                .perform(click());


        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_profile));
        onView(withText("Sex")).check(matches(isDisplayed()));

//        onView(withId(R.id.selectBirthday)).perform(PickerActions.setDate(2017, 6, 30));

//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1989, 8, 25));
//        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(year, monthOfYear, dayOfMonth));

    }


    public String generateString(Random random, String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }


}
