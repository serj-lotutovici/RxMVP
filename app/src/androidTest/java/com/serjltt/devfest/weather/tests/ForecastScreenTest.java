package com.serjltt.devfest.weather.tests;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.serjltt.devfest.weather.TestHelper;
import com.serjltt.devfest.weather.robots.ForecastScreenRobot;
import com.serjltt.devfest.weather.rules.MockWebServerRule;
import com.serjltt.devfest.weather.show.forecast.ui.ForecastActivity;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class) public class ForecastScreenTest {
  private final MockWebServerRule mockWebServerRule = new MockWebServerRule();

  @Rule public RuleChain rules = RuleChain.emptyRuleChain()
      .around(mockWebServerRule)
      .around(new ActivityTestRule<>(ForecastActivity.class));

  private ForecastScreenRobot robot;

  @Before public void setUp() throws Exception {
    robot = new ForecastScreenRobot();
  }

  @Ignore("https://github.com/serj-lotutovici/RxMVP/issues/2") @Test public void displaysError()
      throws Exception {
    MockWebServer server = mockWebServerRule.mockWebServer();
    server.enqueue(new MockResponse().setResponseCode(501));

    Thread.sleep(2000); // Normally I would use an IdlingResource

    // Error message is constructed by MockResponse
    robot.assertDisplaysErrorMessage("HTTP 501 Server Error");
  }

  @Ignore("https://github.com/serj-lotutovici/RxMVP/issues/2") @Test public void displaysResponse()
      throws Exception {
    MockWebServer server = mockWebServerRule.mockWebServer();
    server.enqueue(new MockResponse().setBody(TestHelper.fromResource("yahoo_response.json")));

    Thread.sleep(2000); // Normally I would use an IdlingResource

    robot.assertItemHasDate(4, "03 Aug 2016");
  }
}
