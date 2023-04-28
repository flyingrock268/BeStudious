package com.example.beStudious

import android.app.Instrumentation
import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.beStudious.feed.feedMain
import com.google.firebase.auth.FirebaseAuth
import junit.framework.TestCase.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)

    @Before
    fun setUp() {
        FirebaseAuth.getInstance().signOut() // sign out the user before running the test
    }

    @Test
    fun testLogin() {

        // Enter valid email and password
        onView(ViewMatchers.withId(R.id.email))
            .perform(typeText("valid-email@example.com"), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.password))
            .perform(typeText("valid-password"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_login))
            .perform(ViewActions.click())
        }

}
