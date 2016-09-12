package com.serjltt.devfest.weather.data.network;

import android.content.Context;
import com.squareup.moshi.Moshi;
import com.serjltt.devfest.weather.BuildConfig;
import com.serjltt.devfest.weather.data.model.ModelModule;
import com.serjltt.devfest.weather.di.Global;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Global
@Module(includes = ModelModule.class)
public class NetworkModule {
  static final String HEADER_CACHE_CONTROL = "Cache-Control";
  static final String NAME_LOGGING = "logging";
  static final String NAME_CACHE = "cache";
  static final String NAME_OFFLINE_CACHE = "offline_cache";
  static final int ONE_KILOBYTE = 1024;
  static final int HTTP_CACHE_SIZE = 10 * ONE_KILOBYTE * ONE_KILOBYTE;

  public interface Component {
    DeviceNetwork deviceNetwork();
  }

  final HttpUrl endpoint;

  public NetworkModule(HttpUrl endpoint) {
    this.endpoint = endpoint;
  }

  @Provides Retrofit provideRetrofit(OkHttpClient client, Moshi moshi) {
    return new Retrofit.Builder().addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(client)
        .baseUrl(endpoint)
        .build();
  }

  @Provides OkHttpClient provideOkHttpClient(Cache cache,
      @Named(NAME_LOGGING) Interceptor loggingInterceptor,
      @Named(NAME_CACHE) Interceptor cacheInterceptor,
      @Named(NAME_OFFLINE_CACHE) Interceptor offlineCacheInterceptor) {
    return new OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(offlineCacheInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .build();
  }

  @Provides Cache provideCache(Context context) {
    return new Cache(new File(context.getCacheDir(), "http-cache"), HTTP_CACHE_SIZE);
  }

  @Provides @Named(NAME_LOGGING) Interceptor provideLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(BuildConfig.DEBUG
        ? HttpLoggingInterceptor.Level.BODY
        : HttpLoggingInterceptor.Level.NONE);
    return interceptor;
  }

  @Provides @Named(NAME_CACHE) Interceptor provideCacheInterceptor() {
    return chain -> {
      Response response = chain.proceed(chain.request());
      // re-write response header to force use of cache
      CacheControl cacheControl = new CacheControl.Builder()
          .maxAge(1, TimeUnit.MINUTES)
          .build();

      return response.newBuilder()
          .header(HEADER_CACHE_CONTROL, cacheControl.toString())
          .build();
    };
  }

  @Provides @Named(NAME_OFFLINE_CACHE) Interceptor provideOfflineCacheInterceptor(
      DeviceNetwork deviceNetwork) {
    return chain -> {
      Request request = chain.request();
      if (!deviceNetwork.isNetworkAvailable()) {
        // If no network is available we use the before returned
        // response which is not older than 7 days.
        CacheControl cacheControl = new CacheControl.Builder()
            .maxStale(1, TimeUnit.HOURS)
            .build();
        request = request.newBuilder()
            .cacheControl(cacheControl)
            .build();
      }
      return chain.proceed(request);
    };
  }

  @Provides DeviceNetwork provideNetwork(Context context) {
    return new RealDeviceNetwork(context);
  }
}
