package com.thatnawfal.githubuser.presentation.ui.home

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.thatnawfal.githubuser.R
import com.thatnawfal.githubuser.presentation.ui.MainActivity
import com.thatnawfal.githubuser.presentation.ui.splashscreen.SplashScreenActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeFragmentTest {

    @Before
    fun setup(){
        ActivityScenario.launch(SplashScreenActivity::class.java)
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun changeDarkMode() {
        onView(withId(R.id.btn_settings)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_settings)).perform(click())

        onView(withId(R.id.switch_theme)).check(matches(isDisplayed()))
        onView(withId(R.id.switch_theme)).perform(click())

    }
}