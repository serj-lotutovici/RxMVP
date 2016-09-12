package com.serjltt.devfest.weather.data.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.WorkerThread;

final class RealDeviceNetwork implements DeviceNetwork {
  final Context context;

  /**
   * @param context should be Application context ideally in order to avoid leaks.
   * <a href="https://code.google.com/p/android/issues/detail?id=43945">issue</a>
   */
  public RealDeviceNetwork(Context context) {
    this.context = context;
  }

  @WorkerThread @State @Override public int getCurrentNetworkState() {
    ConnectivityManager manager =
        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = manager.getActiveNetworkInfo();

    // Determine the network state
    if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
      return OFFLINE;
    }

    if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
      return ON_WIFI;
    } else {
      return ON_MOBILE;
    }
  }

  @WorkerThread @Override public boolean isNetworkAvailable() {
    return getCurrentNetworkState() != OFFLINE;
  }
}
