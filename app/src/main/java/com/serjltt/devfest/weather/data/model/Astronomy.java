package com.serjltt.devfest.weather.data.model;

import com.squareup.moshi.Json;

public final class Astronomy {
  @Json(name = "sunrise") public final String sunrise;
  @Json(name = "sunset") public final String sunset;

  public Astronomy(String sunrise, String sunset) {
    this.sunrise = sunrise;
    this.sunset = sunset;
  }

  @Override public String toString() {
    return "Astronomy{" +
        "sunrise='" + sunrise + '\'' +
        ", sunset='" + sunset + '\'' +
        '}';
  }

  @Override public boolean equals(Object obj) {
    if (this == obj) return true;
    if (!(obj instanceof Astronomy)) return false;

    Astronomy astronomy = (Astronomy) obj;
    return sunrise != null ? sunrise.equals(astronomy.sunrise) : astronomy.sunrise == null
        && (sunset != null ? sunset.equals(astronomy.sunset) : astronomy.sunset == null);
  }

  @Override public int hashCode() {
    int result = sunrise != null ? sunrise.hashCode() : 0;
    result = 31 * result + (sunset != null ? sunset.hashCode() : 0);
    return result;
  }
}
