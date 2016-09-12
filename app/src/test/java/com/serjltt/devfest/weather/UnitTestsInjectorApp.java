package com.serjltt.devfest.weather;

import com.serjltt.devfest.weather.data.network.NetworkModule;
import com.serjltt.devfest.weather.di.DaggerInjector;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.di.InjectorApp;
import com.serjltt.devfest.weather.di.PlatformModule;
import com.serjltt.devfest.weather.rx.RxModule;
import java.util.Collections;
import java.util.List;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.rules.TestRule;
import rx.schedulers.Schedulers;

public class UnitTestsInjectorApp extends InjectorApp {
  private final MockWebServer mockWebServer = new MockWebServer();

  @Override protected Injector buildInjector() {
    return DaggerInjector.builder()
        .platformModule(new PlatformModule(this))
        .networkModule(new NetworkModule(mockWebServer.url("/")))
        // We don't need io, nor main thread schedulers durring unit tests
        .rxModule(new RxModule(Schedulers.immediate(), Schedulers.immediate()))
        .build();
  }

  public MockWebServer mockWebServer() {
    return mockWebServer;
  }

  /**
   * Provides additional tests rules that should be injected into every test relying on this
   * application.
   */
  List<TestRule> getTestRules() {
    return Collections.singletonList(mockWebServer);
  }
}
