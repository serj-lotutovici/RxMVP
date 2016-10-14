package com.serjltt.devfest.weather.show.forecast.usecase;

import com.serjltt.devfest.weather.data.WeatherService;
import com.serjltt.devfest.weather.data.model.Forecast;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import com.serjltt.devfest.weather.show.forecast.model.ForecastModel;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import java.util.List;
import javax.inject.Inject;

/**
 * This interactor like class is aware of all necessary parameters that the original data
 * {@linkplain WeatherService emitter} requires, others should not be concerned about that.
 */
public final class GetForecastUseCase implements RxUseCase<List<ForecastMvp.Model>, String> {
  private static final Function<Forecast, ForecastMvp.Model> MAPPER =
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

  @Override public Observable<List<ForecastMvp.Model>> stream(String city) {
    return weatherService.getForecast(querySelect(city), QUERY_FORMAT, QUERY_ENV)
        .flatMapSingle(packet ->
            Observable.fromIterable(packet.forecast)
                .map(MAPPER)
                .toList());
  }
}
