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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class ForecastPresenterTest {
  @Mock RxUseCase<List<ForecastMvp.Model>> useCase;
  @Mock ForecastMvp.View view;
  private Presenter<ForecastMvp.View> presenter;

  @Before public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    presenter = new ForecastPresenter(useCase, Schedulers.immediate(), Schedulers.immediate());
  }

  @Test public void propagatesError() throws Exception {
    when(useCase.stream()).thenReturn(Observable.fromCallable(() -> {
      throw new IOException("Error");
    }));

    presenter.bind(view);

    verify(view, times(1)).showLoading();
    verify(view, times(1)).hideLoading();
    verify(view, times(1)).showError("Error");
  }

  @Test public void propagatesSuccess() throws Exception {
    ForecastMvp.Model oneForecast = mock(ForecastMvp.Model.class);
    when(useCase.stream()).thenReturn(
        Observable.fromCallable(() -> Collections.singletonList(oneForecast)));

    presenter.bind(view);

    verify(view, times(1)).showLoading();
    verify(view, times(1)).hideLoading();
    verify(view, times(1)).showForecast(Collections.singletonList(oneForecast));
  }
}