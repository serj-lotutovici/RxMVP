package com.serjltt.devfest.weather.show.forecast;

import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.show.forecast.presenter.ForecastPresenterTest;
import com.serjltt.devfest.weather.show.forecast.usecase.GetForecastUseCaseTest;
import dagger.Component;

/** Wee need a test component to leverage from all the setup. */
@Consumer
@Component(
    dependencies = Injector.class,
    modules = {
        ForecastBindModule.class, ForecastModule.class
    })
public interface ForecastTestComponent {
  void inject(GetForecastUseCaseTest test);

  void inject(ForecastPresenterTest test);
}
