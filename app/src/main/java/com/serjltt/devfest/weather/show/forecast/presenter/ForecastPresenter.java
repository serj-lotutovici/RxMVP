package com.serjltt.devfest.weather.show.forecast.presenter;

import com.serjltt.devfest.weather.mvp.Presenter;
import com.serjltt.devfest.weather.rx.RxModule;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;

public final class ForecastPresenter implements Presenter<ForecastMvp.View> {
  private final RxUseCase<List<ForecastMvp.Model>, String> getForecastUseCase;
  private final Scheduler ioScheduler;
  private final Scheduler mainThreadScheduler;

  @Inject ForecastPresenter(RxUseCase<List<ForecastMvp.Model>, String> getForecastUseCase,
      @Named(RxModule.IO_SCHEDULER) Scheduler ioScheduler,
      @Named(RxModule.MAIN_SCHEDULER) Scheduler mainThreadScheduler) {
    this.getForecastUseCase = getForecastUseCase;
    this.ioScheduler = ioScheduler;
    this.mainThreadScheduler = mainThreadScheduler;
  }

  @Override public Disposable bind(ForecastMvp.View view) {
    Observable<List<ForecastMvp.Model>> forecastObservable = view.cityName()
        .doOnNext(city -> view.showLoading())
        .flatMap(getForecastUseCase::stream)
        .subscribeOn(ioScheduler)
        .observeOn(mainThreadScheduler)
        .doOnNext(name -> view.hideLoading());

    return forecastObservable.subscribe(view::showForecast,
        throwable -> {
          view.hideLoading();
          view.showError(throwable.getMessage());
        });
  }
}
