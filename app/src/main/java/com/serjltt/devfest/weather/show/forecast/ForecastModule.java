package com.serjltt.devfest.weather.show.forecast;

import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.mvp.Presenter;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.presenter.ForecastPresenter;
import com.serjltt.devfest.weather.show.forecast.usecase.GetForecastUseCase;
import dagger.Binds;
import dagger.Module;
import java.util.List;

@Consumer @Module public abstract class ForecastModule {
  @Binds abstract Presenter<ForecastMvp.View> bindPresenter(ForecastPresenter presenter);

  @Binds abstract RxUseCase<List<ForecastMvp.Model>> bindUseCase(GetForecastUseCase useCase);
}
