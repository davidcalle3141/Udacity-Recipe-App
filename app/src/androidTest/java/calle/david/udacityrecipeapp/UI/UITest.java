package calle.david.udacityrecipeapp.UI;


import android.content.pm.ActivityInfo;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.Utilities.EspressoIdlingResource;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assume.assumeTrue;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class UITest {

    private boolean isPhone;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
        isPhone = mActivityTestRule.getActivity().findViewById(R.id.twoPane) == null;


    }

    @Test
    public void NavigationPortrait(){
        Assume.assumeTrue(isPhone);
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        onView(withId(R.id.recipeCardsRV))
                .perform(actionOnItemAtPosition(0,click()));
        onView(withId(R.id.ingredients_master_list_fragment)).check(matches(isDisplayed()));
        onView(withId(R.id.ingredients_master_list_fragment))
                .perform(swipeUp());


        onView(withId(R.id.ingredientsListStepsRV))
                .perform(actionOnItemAtPosition(0,click()));
        onView(withId(R.id.recipe_steps_fragment)).check(matches(isDisplayed()));


    }
    @Test
    public void NavigationLandscape(){
        Assume.assumeTrue(isPhone);
        mActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        onView(withId(R.id.recipeCardsRV))
                .perform(actionOnItemAtPosition(0,click()));
        onView(withId(R.id.ingredients_master_list_fragment)).check(matches(isDisplayed()));

        onView(withId(R.id.ingredientsListStepsRV))
                .perform(scrollTo());




    }
    @Test
    public void SimpleTabletTest(){
        Assume.assumeFalse(isPhone);
        onView(withId(R.id.recipeCardsRV))
                .perform(actionOnItemAtPosition(0,click()));
        onView(withId(R.id.master_fragment_list)).check(matches(isDisplayed()));
        onView(withId(R.id.master_fragment_detail)).check(matches(isDisplayed()));
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
