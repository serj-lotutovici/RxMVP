package com.serjltt.devfest.weather.data.network;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;

/** Allows us to hook in different interceptors for testing purposes. */
public class TestNetworkModule extends NetworkModule {
  public final UrlSwappingInterceptor urlSwappingInterceptor;

  public TestNetworkModule(HttpUrl endpoint) {
    super(endpoint);
    urlSwappingInterceptor = new UrlSwappingInterceptor(endpoint);
  }

  // We replace the logging interceptor with a url swapping one
  // feels bad to alter the dependency graph, but Retrofit doesn't give us much choice.
  @Override Interceptor provideLoggingInterceptor() {
    return urlSwappingInterceptor;
  }

  @Override Interceptor provideCacheInterceptor() {
    return dummyInterceptor();
  }

  @Override Interceptor provideOfflineCacheInterceptor(DeviceNetwork deviceNetwork) {
    return dummyInterceptor();
  }

  /** Allows to override the caching interceptors, so that tests would run independently. */
  private Interceptor dummyInterceptor() {
    return chain -> chain.proceed(chain.request());
  }
}
