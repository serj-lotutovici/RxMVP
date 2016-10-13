package com.serjltt.devfest.weather.show.forecast.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.f2prateek.rx.preferences.Preference;
import com.pedrogomez.renderers.ListAdapteeCollection;
import com.pedrogomez.renderers.RVRendererAdapter;
import com.pedrogomez.renderers.RendererBuilder;
import com.serjltt.devfest.weather.R;
import com.serjltt.devfest.weather.di.Injector;
import com.serjltt.devfest.weather.di.InjectorActivity;
import com.serjltt.devfest.weather.mvp.Presenter;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscription;

public class ForecastActivity extends InjectorActivity implements ForecastMvp.View {
  static final String[] CITIES = { "amsterdam", "hamburg", "barcelona" };

  @BindView(R.id.forecast_list) RecyclerView recyclerView;
  @BindView(R.id.forecast_progress) ProgressBar progressView;
  @BindView(R.id.forecast_error) TextView errorView;
  @BindView(R.id.forecast_toolbar) Toolbar toolbarView;

  @Inject Presenter<ForecastMvp.View> presenter;
  @Inject Preference<String> cityNamePref;

  RVRendererAdapter<ForecastMvp.Model> adapter;
  Subscription subscription;
  Unbinder unbinder;

  @Override protected void onInject(Injector injector) {
    DaggerForecastUiComponent.builder()
        .injector(injector)
        .build()
        .inject(this);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_forecast);
    unbinder = ButterKnife.bind(this);

    setSupportActionBar(toolbarView);
    setCity(cityNamePref.get());

    RendererBuilder<ForecastMvp.Model> rendererBuilder = new RendererBuilder<>(new ModelRenderer());
    adapter = new RVRendererAdapter<>(rendererBuilder, new ListAdapteeCollection<>());
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(adapter);

    subscription = presenter.bind(this);
  }

  @Override protected void onDestroy() {
    subscription.unsubscribe();
    unbinder.unbind();
    super.onDestroy();
  }

  @Override public void showLoading() {
    progressView.setVisibility(View.VISIBLE);
    errorView.setVisibility(View.GONE);
  }

  @Override public void hideLoading() {
    progressView.setVisibility(View.GONE);
  }

  @Override public void showError(String error) {
    errorView.setVisibility(View.VISIBLE);
    errorView.setText(error);
  }

  @Override public void showForecast(List<ForecastMvp.Model> forecasts) {
    adapter.clear();
    adapter.addAll(forecasts);
    adapter.notifyDataSetChanged();
  }

  @Override public Observable<String> cityName() {
    return cityNamePref.asObservable()
        .share();
  }

  @OnClick(R.id.forecast_fab) void showChangeCityDialog() {
    new AlertDialog.Builder(this)
        .setItems(CITIES, (dialog, which) -> {
          String name = CITIES[which];
          setCity(name);
          cityNamePref.set(name);
        })
        .create()
        .show();
  }

  void setCity(String name) {
    getSupportActionBar().setTitle(getString(R.string.screen_name, name));
  }
}
