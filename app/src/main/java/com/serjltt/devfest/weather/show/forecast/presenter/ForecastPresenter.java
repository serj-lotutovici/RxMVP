package com.serjltt.devfest.weather.show.forecast.presenter;

import android.util.Log;
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

public final class ForecastPresenter implements Presenter<ForecastMvp.View> {
  private final RxUseCase<List<ForecastMvp.Model>> getForecastUseCase;
  private final Scheduler subscribeScheduler;
  private final Scheduler observeScheduler;

  @Inject ForecastPresenter(RxUseCase<List<ForecastMvp.Model>> getForecastUseCase,
      @Named(RxModule.IO_SCHEDULER) Scheduler subscribeScheduler,
      @Named(RxModule.MAIN_SCHEDULER) Scheduler observeScheduler) {
    this.getForecastUseCase = getForecastUseCase;
    this.subscribeScheduler = subscribeScheduler;
    this.observeScheduler = observeScheduler;
  }

  @Override public Subscription bind(ForecastMvp.View view) {
    CompositeSubscription subscription = new CompositeSubscription();

    subscription.add(view.queryChanged()
        .filter(cs -> cs.length() > 2)
        // TODO trigger city suggestion and spit it out to the view
        // TODO the view will then trigger a new request if new city will be chosen
        .subscribe(cs -> Log.d("TMP", cs.toString())));

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
