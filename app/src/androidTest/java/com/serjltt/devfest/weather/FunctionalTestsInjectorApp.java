package com.serjltt.devfest.weather;

import com.serjltt.devfest.weather.data.network.TestNetworkModule;
import com.serjltt.devfest.weather.data.network.UrlSwappingInterceptor;
import com.serjltt.devfest.weather.di.DaggerInjector;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.di.InjectorApp;
import com.serjltt.devfest.weather.di.PlatformModule;
import com.serjltt.devfest.weather.rx.RxModule;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;

public final class FunctionalTestsInjectorApp extends InjectorApp {
  private UrlSwappingInterceptor urlSwappingInterceptor;

  @Override protected Injector buildInjector() {
    // We need the reference for tests.
    TestNetworkModule networkModule = new TestNetworkModule(HttpUrl.parse("http://localhost/"));
    urlSwappingInterceptor = networkModule.urlSwappingInterceptor;

    return DaggerInjector.builder()
        .platformModule(new PlatformModule(this))
        .networkModule(networkModule)
        .rxModule(new RxModule(Schedulers.io(), AndroidSchedulers.mainThread()))
        .build();
  }

  public UrlSwappingInterceptor urlSwappingInterceptor() {
    return urlSwappingInterceptor;
  }
}
