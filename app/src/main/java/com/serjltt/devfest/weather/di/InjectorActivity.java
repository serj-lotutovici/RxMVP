package com.serjltt.devfest.weather.di;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/** Base contract for activities that consume the main di component. */
public abstract class InjectorActivity extends AppCompatActivity {
  @CallSuper @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Injector injector = InjectorApp.get(this).injector();
    onInject(injector);
  }

  /** Called when the activity has obtained the injector. */
  protected abstract void onInject(Injector injector);
}
