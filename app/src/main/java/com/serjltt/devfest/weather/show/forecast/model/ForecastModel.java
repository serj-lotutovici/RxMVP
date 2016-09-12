package com.serjltt.devfest.weather.show.forecast.model;

import android.os.Parcelable;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import java.io.Serializable;

/**
 * Model that will be used to present the data. This class is not overloaded by extra data that is
 * received originally from the backend.
 * <p>
 * <b>DISCLAIMER: </b> Implements {@linkplain Serializable} for convenience, normally it would be
 * {@linkplain Parcelable} which delegation of the whole boilerplate to AutoValue.
 */
public final class ForecastModel implements ForecastMvp.Model, Serializable {
  private final String date;
  private final String low;
  private final String high;

  public ForecastModel(String date, String low, String high) {
    this.date = date;
    this.low = low;
    this.high = high;
  }

  @Override public String toString() {
    return "ForecastModel{" +
        "date='" + date + '\'' +
        ", lowestTemperature='" + low + '\'' +
        ", highestTemperature='" + high + '\'' +
        '}';
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;

    ForecastModel other = (ForecastModel) obj;

    return date != null ? date.equals(other.date) : other.date == null
        && (low != null ? low.equals(other.low) : other.low == null
        && (high != null ? high.equals(other.high) : other.high == null));
  }

  @Override public int hashCode() {
    int result = date != null ? date.hashCode() : 0;
    result = 31 * result + (low != null ? low.hashCode() : 0);
    result = 31 * result + (high != null ? high.hashCode() : 0);
    return result;
  }

  @Override public String date() {
    return date;
  }

  @Override public String lowestTemperature() {
    return low;
  }

  @Override public String highestTemperature() {
    return high;
  }
}
