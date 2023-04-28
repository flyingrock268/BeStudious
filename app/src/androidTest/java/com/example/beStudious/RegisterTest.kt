package com.example.beStudious

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

//@RunWith(AndroidJUnit4::class)
//@LargeTest
class RegisterTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Register::class.java)

    @Before
    fun setUp() {
        FirebaseAuth.getInstance().signOut() // sign out the user before running the test
    }

    @Test
    fun testRegister() {

        ActivityScenarioRule(Register::class.java)

        onView(ViewMatchers.withId(R.id.email))
            .perform(typeText("valid-email@example.com"), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.password))
            .perform(typeText("valid-password"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_register))
            .perform(ViewActions.click())
    }
}

