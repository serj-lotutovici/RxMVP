package com.serjltt.devfest.weather.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Global @Module
public final class PlatformModule {
  public interface Component {
    Context applicationContext();
  }

  final Context context;

  public PlatformModule(Context context) {
    this.context = context;
  }

  @Provides Context provideContext() {
    return context;
  }
}
