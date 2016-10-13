package com.serjltt.devfest.weather.rx;

import rx.Observable;

public interface RxUseCase<R, T> {
  Observable<R> stream(T input);
}
