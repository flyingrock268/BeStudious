package com.example.beStudious.TaskList

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beStudious.Login
import org.junit.Test
import org.junit.runner.RunWith
import com.example.beStudious.R
import com.example.beStudious.databinding.TaskMainBinding
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class TaskMainTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Login::class.java)

    @Before
    fun setUp() {
        FirebaseAuth.getInstance().signOut() // sign out the user before running the test
    }

    @Test
    fun addNewTask() {

        onView(ViewMatchers.withId(R.id.email))
            .perform(typeText("valid-email@example.com"), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.password))
            .perform(typeText("valid-password"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_login))
            .perform(ViewActions.click())

        Thread.sleep(500)

        onView(withId(R.id.tasksButton))
            .perform(ViewActions.click())

        // Click on the "New Task" button
        onView(withId(R.id.newTaskButton)).perform(click())

        // Type a task name
        onView(withId(R.id.name)).perform(typeText("Example Task"))

        Espresso.closeSoftKeyboard()

        // Click the "Add" button
        onView(withId(R.id.SaveButtonTask)).perform(click())

    }
}
