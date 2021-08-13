package org.ivankobzarev.signalgiphy.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.ivankobzarev.signalgiphy.App;
import org.ivankobzarev.signalgiphy.ui.GifListViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

  private final App mApp;

  public ViewModelFactory(Application application) {
    this.mApp = (App) application;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(GifListViewModel.class)) {
      return (T) new GifListViewModel(mApp.getGifsRepository());
    }
    throw new IllegalStateException("Unknown ViewModel class");
  }
}
