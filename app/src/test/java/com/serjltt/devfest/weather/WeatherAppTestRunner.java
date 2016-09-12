package com.serjltt.devfest.weather;

import android.support.annotation.NonNull;
import java.lang.reflect.Method;
import java.util.List;
import org.junit.rules.TestRule;
import org.junit.runners.model.InitializationError;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

/** Custom test runner, that will allow unit tests to access the test app and the test injector. */
public class WeatherAppTestRunner extends RobolectricGradleTestRunner {
  private static final int SDK_EMULATE_LEVEL = 23;

  public WeatherAppTestRunner(Class<?> klass) throws InitializationError {
    super(klass);
  }

  @Override
  public Config getConfig(@NonNull Method method) {
    Config defaultConfig = super.getConfig(method);
    return new Config.Implementation(
        new int[] { SDK_EMULATE_LEVEL },
        defaultConfig.manifest(),
        defaultConfig.qualifiers(),
        defaultConfig.packageName(),
        defaultConfig.abiSplit(),
        defaultConfig.resourceDir(),
        defaultConfig.assetDir(),
        defaultConfig.buildDir(),
        defaultConfig.shadows(),
        defaultConfig.instrumentedPackages(),
        UnitTestsInjectorApp.class, // We need to override the real application class for Unit tests.
        defaultConfig.libraries(),
        defaultConfig.constants() == Void.class ? BuildConfig.class : defaultConfig.constants()
    );
  }

  @Override protected List<TestRule> getTestRules(Object target) {
    List<TestRule> rules = super.getTestRules(target);
    rules.addAll(testApp().getTestRules());
    return rules;
  }

  /** Returns the application class for all tests executed by <strong>this</strong> runner. */
  public static UnitTestsInjectorApp testApp() {
    return (UnitTestsInjectorApp) RuntimeEnvironment.application;
  }
}
