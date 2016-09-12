package com.serjltt.devfest.weather.show.forecast.presenter;

import com.serjltt.devfest.weather.rx.UseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import java.util.List;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public final class ForecastPresenter implements ForecastMvp.Presenter {
  final UseCase<List<ForecastMvp.Model>> getForecastUseCase;
  final Scheduler subscribeScheduler;
  final Scheduler observeScheduler;

  public ForecastPresenter(UseCase<List<ForecastMvp.Model>> getForecastUseCase,
      Scheduler subscribeScheduler, Scheduler observeScheduler) {
    this.getForecastUseCase = getForecastUseCase;
    this.subscribeScheduler = subscribeScheduler;
    this.observeScheduler = observeScheduler;
  }

  @Override public Subscription bind(ForecastMvp.View view) {
    CompositeSubscription subscription = new CompositeSubscription();

    Observable<List<ForecastMvp.Model>> forecastObservable = getForecastUseCase.stream()
        .subscribeOn(subscribeScheduler)
        .observeOn(observeScheduler)
        .doOnSubscribe(view::showLoading)
        .doOnCompleted(view::hideLoading);

    subscription.add(forecastObservable.subscribe(view::showForecast, throwable -> {
      view.hideLoading();
      view.showError(throwable.getMessage());
    }));

    return subscription;
  }
}
