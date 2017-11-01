package mobileutility.bookmanager;

/**
 * Created by chegaikw on 9/9/2016.
 */

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.LargeTest;
import android.text.format.DateUtils;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import java.util.concurrent.TimeUnit;

import mobileutility.bookmanager.books.BookActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static mobileutility.bookmanager.TestUtils.clickChildViewWithId;
import static mobileutility.bookmanager.TestUtils.withRecyclerView;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
public class BookActivityAndroidUnitTest extends ActivityInstrumentationTestCase2<BookActivity> {



    @Rule
    public ActivityTestRule<BookActivity> mActivityRule = new ActivityTestRule<>(
            BookActivity.class);

    public BookActivityAndroidUnitTest() {
        super(BookActivity.class);
    }
    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
    }

    @Before
    public void resetTimeout() {
        IdlingPolicies.setMasterPolicyTimeout(60, TimeUnit.SECONDS);
        IdlingPolicies.setIdlingResourceTimeout(26, TimeUnit.SECONDS);
    }

    /**
     * Test 1 Add book and check if added
     * Press add button
     * fill details
     * click ok
     * check if book added on recyclerview
     */
    @Test
    public void addBookToTheServerTest() {
        addBook(DateUtils.SECOND_IN_MILLIS * 3);
        checkIfBookAddedInList(DateUtils.SECOND_IN_MILLIS * 3);
    }

    /**
     * Test 2 Update book and check if updated
     * wait for books to be loaded
     * press edit icon
     * update book detals
     * press submit
     * press ok
     * check if book updated in recyclerview
     */
    @Test
    public void updateBookToTheServerTest() {
        waitForBookToBeLoadedInList(DateUtils.SECOND_IN_MILLIS * 3);
        updateBook(DateUtils.SECOND_IN_MILLIS * 3);
        checkIfBookUpdatedInList(DateUtils.SECOND_IN_MILLIS * 3);
    }


    /**
     * Test 3 delete book and check if deleted
     * wait for books to be loaded
     * click on book
     * click on delete
     * go back once delete and check list
     */
    @Test
    public void zdeleteBookToTheServerTest() {

        waitForBookToBeLoadedToDeleteInList(DateUtils.SECOND_IN_MILLIS * 3);
        deleteBook(DateUtils.SECOND_IN_MILLIS * 3);
        checkIfBookUpdatedInList(DateUtils.SECOND_IN_MILLIS * 3);
    }


    public void deleteBook(long waitingTime) {

        onView(withId(R.id.btn_delete)).perform(click());

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        Espresso.pressBack();

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);

    }


    /**
     * Update book
     * @param waitingTime
     */
    public void updateBook(long waitingTime) {
        onView(withId(R.id.edt_book_title)).perform(typeText(" new"));

        onView(withId(R.id.btn_update)).perform(click());

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(android.R.id.button1)).perform(click());

        Espresso.pressBack();

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);

    }


    /**
     * Add book
     * @param waitingTime
     */
    public void addBook(long waitingTime) {
        onView(withId(R.id.add_book)).perform(click());
        onView(withId(R.id.edt_book_title)).perform(typeText("John"));
        onView(withId(R.id.edt_author)).perform(typeText("John"));
        onView(withId(R.id.edt_categories)).perform(typeText("John"));
        onView(withId(R.id.edt_publisher)).perform(typeText("John"));
        onView(withId(R.id.btn_submit)).perform(click());

        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(android.R.id.button1)).perform(click());

        Espresso.pressBack();

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);

    }

    /**
     * Check if book added in List
     * @param waitingTime
     */
    public void checkIfBookAddedInList(long waitingTime) {
        Espresso.pressBack();
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        onView(withRecyclerView(R.id.books_recyclerview)
                .atPositionOnView(0, R.id.book_list_item_title))
                 .check(matches(withText("John")));

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }

    /**
     * Check if book updated in List
     * @param waitingTime
     */
    public void checkIfBookUpdatedInList(long waitingTime) {
        Espresso.pressBack();
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        onView(withRecyclerView(R.id.books_recyclerview)
                .atPositionOnView(0, R.id.book_list_item_title))
                .check(matches(withText("John new")));

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }

    /**
     * Wait for books to be loaded from server into recyclerview
     * @param waitingTime
     */
    public void waitForBookToBeLoadedInList(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(R.id.books_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.update_book)));


        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }


    public void waitForBookToBeLoadedToDeleteInList(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        onView(withId(R.id.books_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, click()));


        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }

    public void waitFor(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }

    public void waitActivityToLoad(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait for book to be added on the cloud
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        // Clean up
        Espresso.unregisterIdlingResources(idlingResource);
    }
}

