package com.serjltt.devfest.weather.robots;

import com.serjltt.devfest.weather.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.serjltt.devfest.weather.espresso.RecyclerViewMatcher.withRecyclerView;

/** Simple abstraction to hide the horrors of espresso. */
public final class ForecastScreenRobot {
  public void assertDisplaysErrorMessage(String error) {
    onView(withText(error)).check(matches(isDisplayed()));
  }

  public void assertItemHasDate(int position, String date) {
    onView(withRecyclerView(R.id.forecast_list)
        .atPositionOnView(position, R.id.item_date))
        .check(matches(withText(date)));
  }
}
