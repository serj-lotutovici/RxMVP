package com.serjltt.devfest.weather.show.forecast.usecase;

import com.serjltt.devfest.weather.data.WeatherService;
import com.serjltt.devfest.weather.data.model.Forecast;
import com.serjltt.devfest.weather.rx.UseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import com.serjltt.devfest.weather.show.forecast.model.ForecastModel;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

/**
 * This interactor like class is aware of all necessary parameters that the original data
 * {@linkplain WeatherService emitter} requires, others should not be concerned about that.
 */
public final class GetForecastUseCase implements UseCase<List<ForecastMvp.Model>> {
  private static final Func1<Forecast, ForecastMvp.Model> MAPPER =
      forecast -> new ForecastModel(forecast.date, forecast.low, forecast.high);

  private static final String QUERY_SELECT = "select * from weather.forecast "
      + "where woeid in (select woeid from geo.places(1) where text=\"amsterdam\")";
  private static final String QUERY_FORMAT = "json";
  private static final String QUERY_ENV = "store://datatables.org/alltableswithkeys";

  private final WeatherService weatherService;

  public GetForecastUseCase(WeatherService weatherService) {
    this.weatherService = weatherService;
  }

  @Override public Observable<List<ForecastMvp.Model>> stream() {
    return weatherService.getForecast(QUERY_SELECT, QUERY_FORMAT, QUERY_ENV)
        .toObservable()
        .map(packet -> packet.forecast)
        .flatMapIterable(list -> list)
        .map(MAPPER)
        .toList();
  }
}
