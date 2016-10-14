package com.serjltt.devfest.weather.data;

import com.serjltt.devfest.weather.WeatherAppTestRunner;
import com.serjltt.devfest.weather.data.model.Astronomy;
import com.serjltt.devfest.weather.data.model.Forecast;
import com.serjltt.devfest.weather.data.model.ForecastPacket;
import com.serjltt.devfest.weather.data.model.Units;
import com.serjltt.devfest.weather.di.Consumer;
import com.serjltt.devfest.weather.di.Injector;
import dagger.Component;
import io.reactivex.observers.TestObserver;
import javax.inject.Inject;
import okhttp3.mockwebserver.MockResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static com.serjltt.devfest.weather.TestUtils.fromResource;
import static com.serjltt.devfest.weather.WeatherAppTestRunner.testApp;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WeatherAppTestRunner.class)
public final class WeatherServiceTest {
  @Inject WeatherService weatherService;

  @Before public void setUp() throws Exception {
    DaggerWeatherServiceTest_TestComponent.builder()
        .injector(testApp().injector())
        .build()
        .inject(this);
  }

  @Test public void serviceReturnsExpectedData() throws Exception {
    testApp().mockWebServer()
        .enqueue(
            new MockResponse().setBody(fromResource("yahoo_response.json"))
        );

    TestObserver<ForecastPacket> testObserver =
        weatherService.getForecast("q", "f", "e")
            .test();
    testApp().testScheduler()
        .triggerActions();

    ForecastPacket packet =
        testObserver.assertNoErrors()
            .values()
            .get(0);

    // Just copy pasting expected values form the file
    assertThat(packet.title).isEqualTo("Yahoo! Weather - Amsterdam, NH, NL");
    assertThat(packet.astronomy).isEqualTo(new Astronomy("6:0 am", "9:33 pm"));
    assertThat(packet.units).isEqualTo(new Units("mi", "in", "mph", "F"));
    // Not testing the whole list since the library under the hood is already tested
    assertThat(packet.forecast).hasSize(10);

    Forecast expected = new Forecast("30", "01 Aug 2016", "Mon", "69", "55", "Partly Cloudy");
    assertThat(packet.forecast.get(2)
        .equals(expected)).isTrue();
    assertThat(packet.forecast.get(2)
        .hashCode()).isEqualTo(expected.hashCode());
  }

  /** Wee need a test component to leverage from all the setup. */
  @Consumer
  @Component(dependencies = Injector.class)
  interface TestComponent {
    void inject(WeatherServiceTest test);
  }
}