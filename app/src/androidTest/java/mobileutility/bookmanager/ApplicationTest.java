package mobileutility.bookmanager;

import android.app.Application;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ApplicationTestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import mobileutility.bookmanager.books.BookActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    @Rule
    public ActivityTestRule<BookActivity> mActivityRule = new ActivityTestRule<>(
            BookActivity.class);
    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void changeText_sameActivity() {
        onView(withId(R.id.add_book)).perform(click());
    }


}