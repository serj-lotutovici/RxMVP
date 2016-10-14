package com.serjltt.devfest.weather.show.forecast.usecase;

import com.fernandocejas.frodo.annotation.RxLogObservable;
import com.serjltt.devfest.weather.data.WeatherService;
import com.serjltt.devfest.weather.data.model.Forecast;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import com.serjltt.devfest.weather.show.forecast.model.ForecastModel;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.functions.Func1;

/**
 * This interactor like class is aware of all necessary parameters that the original data
 * {@linkplain WeatherService emitter} requires, others should not be concerned about that.
 */
public final class GetForecastUseCase implements RxUseCase<List<ForecastMvp.Model>, String> {
  private static final Func1<Forecast, ForecastMvp.Model> MAPPER =
      forecast -> new ForecastModel(forecast.date, forecast.low, forecast.high);

  private static String querySelect(String city) {
    return "select * from weather.forecast "
        + "where woeid in (select woeid from geo.places(1) where text=\""
        + city + "\")";
  }

  private static final String QUERY_FORMAT = "json";
  private static final String QUERY_ENV = "store://datatables.org/alltableswithkeys";

  private final WeatherService weatherService;

  @Inject GetForecastUseCase(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @RxLogObservable(RxLogObservable.Scope.EVERYTHING)
  @Override public Observable<List<ForecastMvp.Model>> stream(String city) {
    return weatherService.getForecast(querySelect(city), QUERY_FORMAT, QUERY_ENV)
        .toObservable()
        .flatMap(packet -> Observable.just(packet.forecast)
            .flatMapIterable(list -> list)
            .map(MAPPER)
            .toList());
  }
}
