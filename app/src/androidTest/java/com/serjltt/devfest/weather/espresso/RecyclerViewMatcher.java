package com.serjltt.devfest.weather.espresso;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Forked from https://github.com/dannyroa/espresso-samples.
 */
public class RecyclerViewMatcher {
  private final int recyclerViewId;

  private RecyclerViewMatcher(int recyclerViewId) {
    this.recyclerViewId = recyclerViewId;
  }

  public Matcher<View> atPosition(int position) {
    return atPositionOnView(position, -1);
  }

  public Matcher<View> atPositionOnView(int position, int targetViewId) {
    return new TypeSafeMatcher<View>() {
      Resources resources = null;
      View childView;

      @Override public void describeTo(Description description) {
        String idDescription = Integer.toString(recyclerViewId);
        if (resources != null) {
          try {
            idDescription = resources.getResourceName(recyclerViewId);
          } catch (Resources.NotFoundException var4) {
            idDescription = String.format("%s (resource name not found)", new Object[] {
                Integer.valueOf(recyclerViewId)
            });
          }
        }

        description.appendText("with id: " + idDescription);
      }

      @Override public boolean matchesSafely(View view) {
        resources = view.getResources();

        if (childView == null) {
          RecyclerView recyclerView =
              (RecyclerView) view.getRootView().findViewById(recyclerViewId);
          if (recyclerView != null && recyclerView.getId() == recyclerViewId) {
            childView = recyclerView.findViewHolderForAdapterPosition(position).itemView;
          } else {
            return false;
          }
        }

        if (targetViewId == -1) {
          return view == childView;
        } else {
          View targetView = childView.findViewById(targetViewId);
          return view == targetView;
        }
      }
    };
  }

  public static RecyclerViewMatcher withRecyclerView(int recyclerViewId) {
    return new RecyclerViewMatcher(recyclerViewId);
  }
}
