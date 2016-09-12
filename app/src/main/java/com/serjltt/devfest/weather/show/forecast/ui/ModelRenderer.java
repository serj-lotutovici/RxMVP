package com.serjltt.devfest.weather.show.forecast.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pedrogomez.renderers.Renderer;
import com.serjltt.devfest.weather.R;
import com.serjltt.devfest.weather.show.forecast.ForecastMvp;

final class ModelRenderer extends Renderer<ForecastMvp.Model> {
  @BindView(R.id.item_date) TextView dateView;
  @BindView(R.id.item_low) TextView lowView;
  @BindView(R.id.item_high) TextView highView;

  @Override protected View inflate(LayoutInflater inflater, ViewGroup parent) {
    View itemView = inflater.inflate(R.layout.list_item_forecast, parent, false);
    ButterKnife.bind(this, itemView);
    return itemView;
  }

  @Override public void render() {
    ForecastMvp.Model model = getContent();
    dateView.setText(model.date());
    lowView.setText(model.lowestTemperature());
    highView.setText(model.highestTemperature());
  }

  @Override protected void setUpView(View rootView) {

  }

  @Override protected void hookListeners(View rootView) {

  }
}
