package com.serjltt.devfest.weather;

import android.annotation.SuppressLint;
import android.support.test.InstrumentationRegistry;
import java.io.InputStream;
import okio.Okio;

public final class TestHelper {
  @SuppressLint("NewApi") // suppressing warning since all test will run under java 7 or 8
  public static String fromResource(String file) throws Exception {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try (InputStream is = classLoader.getResourceAsStream(file)) {
      return Okio.buffer(Okio.source(is)).readUtf8();
    }
  }

  /** Returns the instance of <strong>this</strong> app retrieved form the instrumentation. */
  public static FunctionalTestsInjectorApp getApplication() {
    return (FunctionalTestsInjectorApp) InstrumentationRegistry.getTargetContext()
        .getApplicationContext();
  }

  private TestHelper() {
  }
}
