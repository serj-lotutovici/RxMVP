package com.serjltt.devfest.weather.show.forecast;

import android.content.Context;
import android.content.SharedPreferences;
import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.serjltt.devfest.weather.di.Consumer;
import dagger.Module;
import dagger.Provides;

@SuppressWarnings("MethodMayBeStatic")
@Consumer
@Module
public final class ForecastModule {
  @Provides SharedPreferences provideSharedPreferences(Context context) {
    return context.getSharedPreferences("forecast", Context.MODE_PRIVATE);
  }

  @Provides RxSharedPreferences provideRxSharedPreferences(SharedPreferences preferences) {
    return RxSharedPreferences.create(preferences);
  }

  @Provides Preference<String> provideCityPreference(RxSharedPreferences rxPreferences) {
    return rxPreferences.getString("city", "amsterdam");
  }
}
