package com.serjltt.devfest.weather.data.model;

import com.squareup.moshi.Json;

public final class Units {
  @Json(name = "distance") public final String distance;
  @Json(name = "pressure") public final String pressure;
  @Json(name = "speed") public final String speed;
  @Json(name = "temperature") public final String temperature;

  public Units(String distance, String pressure, String speed, String temperature) {
    this.distance = distance;
    this.pressure = pressure;
    this.speed = speed;
    this.temperature = temperature;
  }

  @Override public String toString() {
    return "Units{" +
        "distance='" + distance + '\'' +
        ", pressure='" + pressure + '\'' +
        ", speed='" + speed + '\'' +
        ", temperature='" + temperature + '\'' +
        '}';
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Units units = (Units) o;

    return distance != null ? distance.equals(units.distance) : units.distance == null
        && (pressure != null ? pressure.equals(units.pressure) : units.pressure == null
        && (speed != null ? speed.equals(units.speed) : units.speed == null
        && (temperature != null ? temperature.equals(units.temperature)
        : units.temperature == null)));
  }

  @Override public int hashCode() {
    int result = distance != null ? distance.hashCode() : 0;
    result = 31 * result + (pressure != null ? pressure.hashCode() : 0);
    result = 31 * result + (speed != null ? speed.hashCode() : 0);
    result = 31 * result + (temperature != null ? temperature.hashCode() : 0);
    return result;
  }
}
