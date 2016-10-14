package com.serjltt.devfest.weather.data;

import com.serjltt.devfest.weather.data.model.ForecastPacket;
import com.serjltt.moshi.adapters.UnwrapJson;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/** Main service that provides the underlying data store. */
public interface WeatherService {
  /*
   *
   * We will need to pass the following query params:
   * q=select * from weather.forecast where woeid in (select woeid from geo.places(1) where text="amsterdam")
   * &format=json&env=store://datatables.org/alltableswithkeys
   *
   */
  @UnwrapJson({ "query", "results", "channel" })
  @GET("/v1/public/yql") Observable<ForecastPacket> getForecast(@Query("q") String query,
      @Query("format") String format, @Query("env") String env);
}
