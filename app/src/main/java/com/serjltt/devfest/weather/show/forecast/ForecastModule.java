package com.serjltt.devfest.weather.show.forecast;

import com.serjltt.devfest.weather.data.WeatherService;
import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.rx.RxModule;
import com.serjltt.devfest.weather.rx.UseCase;
import com.serjltt.devfest.weather.show.forecast.presenter.ForecastPresenter;
import com.serjltt.devfest.weather.show.forecast.usecase.GetForecastUseCase;
import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;
import rx.Scheduler;

@Consumer
@Module
public class ForecastModule {
  @Provides ForecastMvp.Presenter providePresenter(UseCase<List<ForecastMvp.Model>> useCase,
      @Named(RxModule.IO_SCHEDULER) Scheduler ioScheduler,
      @Named(RxModule.MAIN_SCHEDULER) Scheduler mainScheduler) {
    return new ForecastPresenter(useCase, ioScheduler, mainScheduler);
  }

  @Provides UseCase<List<ForecastMvp.Model>> provideUseCase(WeatherService service) {
    return new GetForecastUseCase(service);
  }
}
