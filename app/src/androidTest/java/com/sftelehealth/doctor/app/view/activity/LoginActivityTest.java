package com.sftelehealth.doctor.app.view.activity;


import androidx.test.espresso.ViewInteraction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.sftelehealth.doctor.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void loginActivityTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction customFontEditText = onView(
                allOf(withId(R.id.phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        2),
                                0)));
        customFontEditText.perform(scrollTo(), click());

        ViewInteraction customFontEditText2 = onView(
                allOf(withId(R.id.phone),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        2),
                                0)));
        customFontEditText2.perform(scrollTo(), replaceText("9"), closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction customFontEditText3 = onView(
                allOf(withId(R.id.phone), withText("9"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        2),
                                0)));
        customFontEditText3.perform(scrollTo(), replaceText("99"));

        ViewInteraction customFontEditText4 = onView(
                allOf(withId(R.id.phone), withText("99"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        2),
                                0),
                        isDisplayed()));
        customFontEditText4.perform(closeSoftKeyboard());

        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        try {
            Thread.sleep(194);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction customFontEditText5 = onView(
                allOf(withId(R.id.phone), withText("99"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        2),
                                0)));
        customFontEditText5.perform(scrollTo(), replaceText("9972808445"));

        ViewInteraction customFontEditText6 = onView(
                allOf(withId(R.id.phone), withText("9972808445"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.main_container),
                                        2),
                                0),
                        isDisplayed()));
        customFontEditText6.perform(closeSoftKeyboard());

        ViewInteraction customButton = onView(
                allOf(withId(R.id.verify_btn), withText("Verify"),
                        childAtPosition(
                                allOf(withId(R.id.main_container),
                                        childAtPosition(
                                                withClassName(is("android.widget.FrameLayout")),
                                                0)),
                                3)));
        customButton.perform(scrollTo(), click());

        /*try {
            Runtime.getRuntime().exec("sms send VM-DRCALL \"code is 654646\""); // Runtime.getRuntime().exec("sms send VM-DRCALL <654646>");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
