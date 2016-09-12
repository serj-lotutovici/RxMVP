package com.serjltt.devfest.weather.rules;

import com.serjltt.devfest.weather.FunctionalTestsInjectorApp;
import com.serjltt.devfest.weather.TestHelper;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Simple rule that delegates to the {@linkplain MockWebServer} bind to the
 * {@linkplain FunctionalTestsInjectorApp}.
 */
public final class MockWebServerRule implements TestRule {
  private final MockWebServer delegate = new MockWebServer();

  @Override public Statement apply(Statement base, Description description) {
    TestHelper.getApplication().urlSwappingInterceptor().swapUrl(delegate.url("/"));
    return delegate.apply(base, description);
  }

  public MockWebServer mockWebServer() {
    return delegate;
  }
}
