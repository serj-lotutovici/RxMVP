package com.serjltt.devfest.weather.show.forecast.presenter;

import com.serjltt.devfest.weather.mvp.Presenter;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ForecastPresenterTest {
  @Mock RxUseCase<List<ForecastMvp.Model>, String> useCase;
  @Mock ForecastMvp.View view;

  private final BehaviorSubject<String> subject = BehaviorSubject.create();
  private Presenter<ForecastMvp.View> presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(view.cityName()).thenReturn(subject);

    presenter = new ForecastPresenter(useCase, Schedulers.immediate(),
        Schedulers.immediate());
  }

  @Test public void propagatesError() throws Exception {
    when(useCase.stream(anyString())).thenReturn(Observable.fromCallable(() -> {
      throw new IOException("Error");
    }));

    presenter.bind(view);
    subject.onNext("test");

    verify(view).showLoading();
    verify(view).hideLoading();
    verify(view).showError("Error");
  }

  @Test public void propagatesSuccess() throws Exception {
    ForecastMvp.Model oneForecast = mock(ForecastMvp.Model.class);
    when(useCase.stream(anyString())).thenReturn(
        Observable.fromCallable(() -> Collections.singletonList(oneForecast)));

    presenter.bind(view);
    subject.onNext("test1");
    subject.onNext("test2");

    verify(view, times(2)).showLoading();
    verify(view, times(2)).hideLoading();
    verify(view, times(2)).showForecast(Collections.singletonList(oneForecast));
  }

  @Test public void doesNotPropagateIfUnsubscribed() throws Exception {
    when(useCase.stream(anyString())).thenReturn(
        Observable.fromCallable(Collections::emptyList));

    presenter.bind(view)
        .unsubscribe();
    subject.onNext("test1");

    verify(view, never()).showLoading();
    verify(view, never()).hideLoading();
    verify(view, never()).showForecast(anyListOf(ForecastMvp.Model.class));
  }
}
