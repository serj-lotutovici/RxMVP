package com.serjltt.devfest.weather;

import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.os.Bundle;
import android.support.test.runner.AndroidJUnitRunner;

/** This way the functional tests will use an application that provides a mock web server. */
public final class FunctionalTestRunner extends AndroidJUnitRunner {
  @Override public void onCreate(Bundle arguments) {
    super.onCreate(arguments);
  }

  @Override public Application newApplication(ClassLoader cl, String className, Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return Instrumentation.newApplication(FunctionalTestsInjectorApp.class, context);
  }
}
