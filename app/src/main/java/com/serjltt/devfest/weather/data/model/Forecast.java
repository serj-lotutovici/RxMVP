package com.serjltt.devfest.weather.data.model;

import com.squareup.moshi.Json;

public final class Forecast {
  @Json(name = "code") public final String code;
  @Json(name = "date") public final String date;
  @Json(name = "day") public final String day;
  @Json(name = "high") public final String high;
  @Json(name = "low") public final String low;
  @Json(name = "text") public final String text;

  public Forecast(String code, String date, String day, String high, String low,
      String text) {
    this.code = code;
    this.date = date;
    this.day = day;
    this.high = high;
    this.low = low;
    this.text = text;
  }

  @Override public String toString() {
    return "Forecast{" +
        "code='" + code + '\'' +
        ", date='" + date + '\'' +
        ", day='" + day + '\'' +
        ", high='" + high + '\'' +
        ", low='" + low + '\'' +
        ", text='" + text + '\'' +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Forecast forecast = (Forecast) o;

    return code != null ? code.equals(forecast.code) : forecast.code == null
        && (date != null ? date.equals(forecast.date) : forecast.date == null
        && (day != null ? day.equals(forecast.day) : forecast.day == null
        && (high != null ? high.equals(forecast.high) : forecast.high == null
        && (low != null ? low.equals(forecast.low) : forecast.low == null
        && (text != null ? text.equals(forecast.text) : forecast.text == null)))));
  }

  @Override public int hashCode() {
    int result = code != null ? code.hashCode() : 0;
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (day != null ? day.hashCode() : 0);
    result = 31 * result + (high != null ? high.hashCode() : 0);
    result = 31 * result + (low != null ? low.hashCode() : 0);
    result = 31 * result + (text != null ? text.hashCode() : 0);
    return result;
  }
}
