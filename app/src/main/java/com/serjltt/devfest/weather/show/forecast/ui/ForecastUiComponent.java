package com.serjltt.devfest.weather.show.forecast.ui;

import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.show.forecast.ForecastModule;
import dagger.Component;

@Consumer
@Component(modules = ForecastModule.class, dependencies = Injector.class)
interface ForecastUiComponent {
  void inject(ForecastActivity activity);
}
