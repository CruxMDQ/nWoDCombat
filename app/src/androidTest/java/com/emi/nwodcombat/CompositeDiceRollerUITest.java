package com.emi.nwodcombat;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.Map;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.emi.nwodcombat.tools.CustomMatchers.withStringMatching;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Emi on 2/23/16.
 * This is an UI test, NOT an unit test.
 * Refer to the Android Testing codelab, chapter 6, for further info.
 */
@RunWith(AndroidJUnit4.class)
//@LargeTest
public class CompositeDiceRollerUITest {

    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickOnFAB() throws Exception {
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void clickOnAttackerDice() throws Exception {
        onView(
            allOf(
                withId(R.id.lblPickDice),
                withText(rule.getActivity().getString(R.string.card_pick_dice_notice,
                    rule.getActivity().getString(R.string.fragment_attacker)))
            )
        ).perform(click());
    }

    @Test
    public void clickOnDefenderDice() throws Exception {
        onView(
            allOf(
                withId(R.id.lblPickDice),
                withText(rule.getActivity().getString(R.string.card_pick_dice_notice,
                    rule.getActivity().getString(R.string.fragment_defender)))
            )
        ).perform(click());
    }

    @Test
    public void increaseAttackerAttributeDice() throws Exception {
        clickOnAttackerDice();

        onView(
            withId(R.id.nbpkAttribute)
        ).perform(click());

        onView(
            withContentDescription("Dice increase for: Attribute")
        )
        .perform(click())
        .perform(click())
        .perform(click());

        onView(
            withText("OK")
        ).perform(click());
    }

    @Test
    public void increaseDefenderAttributeDice() throws Exception {
        clickOnDefenderDice();

        onView(
            withId(R.id.nbpkAttribute)
        ).perform(click());

        onView(
            withContentDescription("Dice increase for: Attribute")
        )
            .perform(click())
            .perform(click());

        onView(
            withText("OK")
        ).perform(click());
    }

    @Test
    public void select8AgainRuleForAttacker() throws Exception {
        increaseAttackerAttributeDice();
        increaseDefenderAttributeDice();

        onView(
            allOf(
                withId(R.id.lblSpecialRules),
                withContentDescription("Special rules for: attacker")
            )
        ).perform(click());

        onView(
            withId(R.id.rvOptions)
        ).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(
            withText("OK")
        ).perform(click());

        clickOnFAB();
    }
}
