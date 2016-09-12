package com.serjltt.devfest.weather.data.network;

import android.support.annotation.IntDef;
import android.support.annotation.WorkerThread;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Allows the consumer to track the network state of the device. */
public interface DeviceNetwork {
  int OFFLINE = 1;
  int ON_WIFI = 2;
  int ON_MOBILE = 3;

  /** Returns the current network state of the device. */
  @WorkerThread @State int getCurrentNetworkState();

  /** Checks network availability. */
  @WorkerThread boolean isNetworkAvailable();

  @Documented
  @IntDef({ OFFLINE, ON_WIFI, ON_MOBILE })
  @Retention(RetentionPolicy.SOURCE) @interface State {
  }
}
