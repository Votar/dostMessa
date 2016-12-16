package entrego.com.android.ui.splash;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import entrego.com.android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class FIrstRegistrationWithAuthTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void firstRegistrationWithAuthTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.auth_edit_email), isDisplayed()));
        appCompatEditText.perform(replaceText("n"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.auth_edit_email), withText("n"), isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.auth_edit_email), withText("n"), isDisplayed()));
        appCompatEditText3.perform(replaceText("n@g"), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.auth_btn_registration), withText("registration"),
                        withParent(withId(R.id.auth_ll_control))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.registration_edit_email), isDisplayed()));
        appCompatEditText4.perform(replaceText("n@g1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.registration_edit_name), isDisplayed()));
        appCompatEditText5.perform(replaceText("1234"), closeSoftKeyboard());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.registration_edit_password), isDisplayed()));
        appCompatEditText6.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.registration_edit_password_conf), isDisplayed()));
        appCompatEditText7.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.registration_edit_phone), isDisplayed()));
        appCompatEditText8.perform(replaceText("996069682"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.registration_btn_ok), withText("OK")));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.succ_reg_btn_login), withText("back to login"),
                        withParent(allOf(withId(R.id.activity_success_registration),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.auth_edit_password), isDisplayed()));
        appCompatEditText9.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.auth_btn_login), withText("login"),
                        withParent(withId(R.id.auth_ll_control))));
        appCompatButton4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.auth_edit_email), withText("n@g"), isDisplayed()));
        appCompatEditText10.perform(replaceText("n@g1"), closeSoftKeyboard());

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.auth_edit_password), withText("123456"), isDisplayed()));
        appCompatEditText11.perform(pressImeActionButton());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.auth_btn_login), withText("login"),
                        withParent(withId(R.id.auth_ll_control))));
        appCompatButton5.perform(scrollTo(), click());

    }

}
