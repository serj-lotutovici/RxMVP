package com.serjltt.devfest.weather.show.forecast.presenter;

import com.fernandocejas.frodo.annotation.RxLogSubscriber;
import com.serjltt.devfest.weather.mvp.Presenter;
import com.serjltt.devfest.weather.rx.RxModule;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

@RxLogSubscriber
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

  @Override public Subscription bind(ForecastMvp.View view) {
    CompositeSubscription subscription = new CompositeSubscription();

    Observable<String> cityName = view.cityName()
        .doOnNext(city -> view.showLoading());

    subscription.add(cityName
        .subscribe(city -> {
          subscription.add(getForecastUseCase.stream(city)
              .subscribeOn(ioScheduler)
              .observeOn(mainThreadScheduler)
              .doOnNext(name -> view.hideLoading())
              .subscribe(view::showForecast, throwable -> {
                view.hideLoading();
                view.showError(throwable.getMessage());
              }));
        }));

    return subscription;
  }
}
