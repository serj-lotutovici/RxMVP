package com.serjltt.devfest.weather.rx;

import rx.Observable;

public interface RxUseCase<T> {
  Observable<T> stream();
}
