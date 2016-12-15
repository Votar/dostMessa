package entrego.com.android.ui.splash;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Locale;

import entrego.com.android.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SplashWithRegistrationActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule = new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void splashActivityTest() {
        pressBack();

        String invalidEmail = "n@FM";

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.auth_btn_registration), withText("registration"),
                        withParent(withId(R.id.auth_ll_control))));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.registration_edit_email), isDisplayed()));
        appCompatEditText.perform(replaceText(invalidEmail), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.registration_edit_name), isDisplayed()));
        appCompatEditText2.perform(replaceText("1234567"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.registration_edit_password), isDisplayed()));
        appCompatEditText3.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.registration_edit_password_conf), isDisplayed()));
        appCompatEditText4.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.registration_edit_phone), isDisplayed()));
        appCompatEditText5.perform(replaceText("996069682"), closeSoftKeyboard());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.registration_btn_ok), withText("OK")));
        appCompatButton3.perform(scrollTo(), click());


        String validEmail = String.format(Locale.getDefault(), "%1$s%2$d", invalidEmail, (int) (Math.random() * 100000));

        //Valid registration
        appCompatEditText.perform(replaceText(validEmail), closeSoftKeyboard());
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.succ_reg_btn_login), withText("back to login"),
                        withParent(allOf(withId(R.id.activity_success_registration),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.auth_edit_email), isDisplayed()));
        appCompatEditText6.perform(replaceText(validEmail), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.auth_edit_password), isDisplayed()));
        appCompatEditText7.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.auth_btn_login), withText("login"),
                        withParent(withId(R.id.auth_ll_control))));
        appCompatButton5.perform(scrollTo(), click());

    }

}
