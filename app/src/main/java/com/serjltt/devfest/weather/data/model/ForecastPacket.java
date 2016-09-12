package com.serjltt.devfest.weather.data.model;

import com.serjltt.moshi.adapters.UnwrapJson;
import com.squareup.moshi.Json;
import java.util.List;

public final class ForecastPacket {
  @Json(name = "title") public final String title;
  @Json(name = "units") public final Units units;
  @Json(name = "astronomy") public final Astronomy astronomy;
  @UnwrapJson({ "forecast" }) @Json(name = "item") public final List<Forecast> forecast;

  public ForecastPacket(String title, Units units, Astronomy astronomy, List<Forecast> forecast) {
    this.title = title;
    this.units = units;
    this.astronomy = astronomy;
    this.forecast = forecast;
  }

  @Override public String toString() {
    return "ForecastPacket{" +
        "title='" + title + '\'' +
        ", units=" + units +
        ", astronomy=" + astronomy +
        ", forecast=" + forecast +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ForecastPacket packet = (ForecastPacket) o;

    return title != null ? title.equals(packet.title) : packet.title == null
        && (units != null ? units.equals(packet.units) : packet.units == null
        && (astronomy != null ? astronomy.equals(packet.astronomy) : packet.astronomy == null
        && (forecast != null ? forecast.equals(packet.forecast) : packet.forecast == null)));
  }

  @Override public int hashCode() {
    int result = title != null ? title.hashCode() : 0;
    result = 31 * result + (units != null ? units.hashCode() : 0);
    result = 31 * result + (astronomy != null ? astronomy.hashCode() : 0);
    result = 31 * result + (forecast != null ? forecast.hashCode() : 0);
    return result;
  }
}
