package com.serjltt.devfest.weather.show.forecast;

import io.reactivex.Observable;
import java.util.List;

/** General contracts for show forecast feature. */
public interface ForecastMvp {
  interface Model {
    String date();

    String lowestTemperature();

    String highestTemperature();
  }

  interface View {
    Observable<String> cityName();

    void showLoading();

    void hideLoading();

    void showError(String error);

    void showForecast(List<Model> data);
  }
}
