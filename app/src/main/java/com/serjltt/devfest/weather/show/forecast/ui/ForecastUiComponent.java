package com.serjltt.devfest.weather.show.forecast.ui;

import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.show.forecast.ForecastBindModule;
import com.serjltt.devfest.weather.show.forecast.ForecastModule;
import dagger.Component;

@Consumer
@Component(
    dependencies = Injector.class,
    modules = {
        ForecastBindModule.class, ForecastModule.class
    })
interface ForecastUiComponent {
  void inject(ForecastActivity activity);
}
