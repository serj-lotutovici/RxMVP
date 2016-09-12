package com.serjltt.devfest.weather;

import com.serjltt.devfest.weather.data.network.NetworkModule;
import com.serjltt.devfest.weather.di.DaggerInjector;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.di.InjectorApp;
import com.serjltt.devfest.weather.di.PlatformModule;
import com.serjltt.devfest.weather.rx.RxModule;
import okhttp3.HttpUrl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/** Main application class, that constructs the "real" di component. */
public final class WeatherApp extends InjectorApp {
  private static final HttpUrl REAL_ENDPOINT = HttpUrl.parse("https://query.yahooapis.com/");

  @Override protected Injector buildInjector() {
    return DaggerInjector.builder()
        .platformModule(new PlatformModule(this))
        .networkModule(new NetworkModule(REAL_ENDPOINT))
        .rxModule(new RxModule(Schedulers.io(), AndroidSchedulers.mainThread()))
        .build();
  }
}
