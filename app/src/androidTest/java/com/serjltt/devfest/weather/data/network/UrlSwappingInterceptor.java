package com.serjltt.devfest.weather.data.network;

import android.support.annotation.Nullable;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/** Interceptor tht allows easy swapping of the url. */
public class UrlSwappingInterceptor implements Interceptor {
  private HttpUrl swappableUrl;

  public UrlSwappingInterceptor(HttpUrl swappableUrl) {
    this.swappableUrl = swappableUrl;
  }

  public void swapUrl(@Nullable HttpUrl url) {
    swappableUrl = url;
  }

  @Override public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    if (swappableUrl != null) {
      request = request.newBuilder()
          .url(swappableUrl)
          .build();
    }
    return chain.proceed(request);
  }
}
