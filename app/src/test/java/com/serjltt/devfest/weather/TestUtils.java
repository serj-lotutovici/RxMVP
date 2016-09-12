package com.serjltt.devfest.weather;

import android.annotation.SuppressLint;
import java.io.InputStream;
import okio.Okio;

public final class TestUtils {
  @SuppressLint("NewApi") // suppressing warning since all test will run under java 7 or 8
  public static String fromResource(String file) throws Exception {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    try (InputStream is = classLoader.getResourceAsStream(file)) {
      return Okio.buffer(Okio.source(is)).readUtf8();
    }
  }

  private TestUtils() {
  }
}
