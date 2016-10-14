package com.serjltt.devfest.weather.show.forecast.presenter;

import com.serjltt.devfest.weather.mvp.Presenter;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import io.reactivex.Observable;
import io.reactivex.schedulers.TestScheduler;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ForecastPresenterTest {
  @Mock RxUseCase<List<ForecastMvp.Model>, String> useCase;
  @Mock ForecastMvp.View view;

  private TestScheduler testScheduler;
  private Presenter<ForecastMvp.View> presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    when(view.cityName()).thenReturn(Observable.fromCallable(() -> "test"));

    testScheduler = new TestScheduler();
    presenter = new ForecastPresenter(useCase, testScheduler, testScheduler);
  }

  @Test public void propagatesError() throws Exception {
    when(useCase.stream(anyString())).thenReturn(Observable.fromCallable(() -> {
      throw new IOException("Error");
    }));

    presenter.bind(view);
    testScheduler.triggerActions();

    verify(view, times(1)).showLoading();
    verify(view, times(1)).hideLoading();
    verify(view, times(1)).showError("Error");
  }

  @Test public void propagatesSuccess() throws Exception {
    ForecastMvp.Model oneForecast = mock(ForecastMvp.Model.class);
    when(useCase.stream(anyString())).thenReturn(
        Observable.fromCallable(() -> Collections.singletonList(oneForecast)));

    presenter.bind(view);
    testScheduler.triggerActions();

    verify(view, times(1)).showLoading();
    verify(view, times(1)).hideLoading();
    verify(view, times(1)).showForecast(Collections.singletonList(oneForecast));
  }
}
