package com.serjltt.devfest.weather.mvp;

import io.reactivex.disposables.Disposable;

/** Base contract for a Presenter in a MVP implementation. */
public interface Presenter<T> {
  /** Binds a view and returns a subscription. */
  Disposable bind(T view);
}
