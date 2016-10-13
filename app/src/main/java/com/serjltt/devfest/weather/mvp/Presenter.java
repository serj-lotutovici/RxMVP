package com.serjltt.devfest.weather.mvp;

import rx.Subscription;

/** Base contract for a Presenter in a MVP implementation. */
public interface Presenter<T> {
  /** Binds a view and returns a subscription. */
  Subscription bind(T view);
}
