package com.serjltt.devfest.weather.show.forecast.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
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
  @BindView(R.id.forecast_list) RecyclerView recyclerView;
  @BindView(R.id.forecast_progress) ProgressBar progressView;
  @BindView(R.id.forecast_error) TextView errorView;
  @BindView(R.id.forecast_toolbar) Toolbar toolbarView;
  @BindView(R.id.forecast_search) SearchView searchView;

  @Inject Presenter<ForecastMvp.View> presenter;

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

  @Override public Observable<CharSequence> queryChanged() {
    return RxSearchView.queryTextChanges(searchView);
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
    adapter.addAll(forecasts);
    adapter.notifyDataSetChanged();
  }
}
