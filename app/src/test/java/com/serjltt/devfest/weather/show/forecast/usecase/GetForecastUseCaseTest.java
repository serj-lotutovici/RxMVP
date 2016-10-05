package com.serjltt.devfest.weather.show.forecast.usecase;

import com.serjltt.devfest.weather.WeatherAppTestRunner;
import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.rx.RxUseCase;
import com.serjltt.devfest.weather.show.forecast.ForecastModule;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import com.serjltt.devfest.weather.show.forecast.model.ForecastModel;
import dagger.Component;
import java.util.List;
import javax.inject.Inject;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.observers.TestSubscriber;

import static com.serjltt.devfest.weather.TestUtils.fromResource;
import static com.serjltt.devfest.weather.WeatherAppTestRunner.testApp;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WeatherAppTestRunner.class)
public final class GetForecastUseCaseTest {
  @Inject RxUseCase<List<ForecastMvp.Model>> useCase;

  @Before public void setUp() throws Exception {
    DaggerGetForecastUseCaseTest_TestComponent.builder()
        .injector(testApp().injector())
        .build()
        .inject(this);
  }

  @Test public void mapsPacketToModel() throws Exception {
    testApp().mockWebServer().enqueue(
        new MockResponse().setBody(fromResource("yahoo_response.json"))
    );

    TestSubscriber<List<ForecastMvp.Model>> testSubscriber = new TestSubscriber<>();
    useCase.stream().subscribe(testSubscriber);

    testSubscriber.assertNoErrors();

    List<ForecastMvp.Model> forecast = testSubscriber.getOnNextEvents().get(0);
    assertThat(forecast).hasSize(10);
    assertThat(forecast.get(9)).isEqualToComparingFieldByField(
        new ForecastModel("08 Aug 2016", "59", "70"));
  }

  /** Wee need a test component to leverage from all the setup. */
  @Consumer
  @Component(modules = ForecastModule.class, dependencies = Injector.class)
  interface TestComponent {
    void inject(GetForecastUseCaseTest test);
  }
}