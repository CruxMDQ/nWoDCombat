package com.emi.nwodcombat.tools;

import android.support.test.espresso.matcher.BoundedMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Created by Emi on 2/24/16.
 */
public class CustomMatchers {
    public static Matcher<Object> withStringMatching(String expectedText) {
        checkNotNull(expectedText);
        return withStringMatching(equalTo(expectedText));
    }

    public static Matcher<Object> withStringMatching(final Matcher<String> itemTextMatcher) {
        checkNotNull(itemTextMatcher);

        return new BoundedMatcher<Object, String>(String.class) {
            @Override
            public boolean matchesSafely(String string) {
                return itemTextMatcher.matches(string);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("with string: ");
                itemTextMatcher.describeTo(description);
            }
        };
    }
}
