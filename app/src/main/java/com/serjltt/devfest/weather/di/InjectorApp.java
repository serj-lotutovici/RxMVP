package com.serjltt.devfest.weather.di;

import android.app.Application;
import android.content.Context;

/** Main contract of an application that keeps a reference to the di component. */
public abstract class InjectorApp extends Application {
  /** Returns an instance of the underlying application. */
  static InjectorApp get(Context context) {
    return (InjectorApp) context.getApplicationContext();
  }

  Injector injector;

  @Override public void onCreate() {
    super.onCreate();
    injector = buildInjector();
  }

  /** Returns an instance of the injector. */
  public Injector injector() {
    return injector;
  }

  protected abstract Injector buildInjector();
}
