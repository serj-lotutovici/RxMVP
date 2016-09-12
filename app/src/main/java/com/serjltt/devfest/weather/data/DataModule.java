package com.serjltt.devfest.weather.data;

import com.serjltt.devfest.weather.data.network.NetworkModule;
import com.serjltt.devfest.weather.di.Global;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Global
@Module(includes = NetworkModule.class)
public final class DataModule {
  public interface Component {
    WeatherService weatherService();
  }

  @Provides WeatherService provideWeatherService(Retrofit retrofit) {
    return retrofit.create(WeatherService.class);
  }
}
