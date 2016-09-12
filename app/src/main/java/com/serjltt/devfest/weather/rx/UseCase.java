package com.serjltt.devfest.weather.rx;

import rx.Observable;

public interface UseCase<T> {
  Observable<T> stream();
}
