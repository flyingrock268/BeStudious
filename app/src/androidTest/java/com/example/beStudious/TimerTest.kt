import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.beStudious.Login
import com.example.beStudious.Timer.TimerActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.beStudious.R
import com.google.firebase.auth.FirebaseAuth
import org.junit.Before

class TimerActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(Login::class.java)

    @Before
    fun setUp() {
        FirebaseAuth.getInstance().signOut() // sign out the user before running the test
    }

    @Test
    fun testTimer() {

        if(FirebaseAuth.getInstance().currentUser != null){

            onView(withId(R.id.TimeButton))
                .perform(ViewActions.click())

        }

        onView(ViewMatchers.withId(R.id.email))
            .perform(typeText("valid-email@example.com"), ViewActions.closeSoftKeyboard())
        onView(ViewMatchers.withId(R.id.password))
            .perform(typeText("valid-password"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btn_login))
            .perform(ViewActions.click())

        Thread.sleep(500)

        onView(withId(R.id.TimeButton))
            .perform(ViewActions.click())
        onView(withId(R.id.time_input_hours))
            .perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.time_input_minutes))
            .perform(typeText("0"), closeSoftKeyboard())
        onView(withId(R.id.time_input_seconds))
            .perform(typeText("3"), closeSoftKeyboard())
        onView(withId(R.id.start_button)).perform(click())
    }
}
