package com.serjltt.devfest.weather.rx;

import com.serjltt.devfest.weather.di.Global;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import javax.inject.Named;

@Global
@Module
public final class RxModule {
  public static final String IO_SCHEDULER = "io";
  public static final String MAIN_SCHEDULER = "main";

  public interface Component {
    @Named(IO_SCHEDULER) Scheduler ioScheduler();

    @Named(MAIN_SCHEDULER) Scheduler mainScheduler();
  }

  private final Scheduler ioScheduler;
  private final Scheduler mainScheduler;

  public RxModule(Scheduler ioScheduler, Scheduler mainScheduler) {
    this.ioScheduler = ioScheduler;
    this.mainScheduler = mainScheduler;
  }

  @Provides @Named(IO_SCHEDULER) Scheduler provideIoScheduler() {
    return ioScheduler;
  }

  @Provides @Named(MAIN_SCHEDULER) Scheduler provideMainScheduler() {
    return mainScheduler;
  }
}
