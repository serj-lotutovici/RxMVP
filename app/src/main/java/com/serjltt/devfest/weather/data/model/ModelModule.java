package com.serjltt.devfest.weather.data.model;

import com.serjltt.moshi.adapters.UnwrapJsonAdapter;
import com.squareup.moshi.Moshi;
import com.serjltt.devfest.weather.di.Global;
import dagger.Module;
import dagger.Provides;

@SuppressWarnings("MethodMayBeStatic")
@Global @Module
public final class ModelModule {
  @Provides Moshi provideMoshi() {
    return new Moshi.Builder()
        .add(UnwrapJsonAdapter.FACTORY)
        .build();
  }
}
