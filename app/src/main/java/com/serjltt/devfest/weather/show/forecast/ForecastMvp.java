package com.serjltt.devfest.weather.show.forecast;

import java.util.List;
import rx.Subscription;

/** General contracts for show forecast feature. */
public interface ForecastMvp {
  interface Model {
    String date();

    String lowestTemperature();

    String highestTemperature();
  }

  interface View {
    void showLoading();

    void hideLoading();

    void showError(String error);

    void showForecast(List<Model> data);
  }

  interface Presenter {
    Subscription bind(View view);
  }
}
