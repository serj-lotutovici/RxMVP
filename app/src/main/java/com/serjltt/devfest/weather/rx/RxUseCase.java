package com.serjltt.devfest.weather.rx;

import io.reactivex.Observable;

public interface RxUseCase<R, T> {
  Observable<R> stream(T input);
}
