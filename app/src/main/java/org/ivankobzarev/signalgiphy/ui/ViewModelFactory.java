package org.ivankobzarev.signalgiphy.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.ivankobzarev.signalgiphy.repository.GifsRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ViewModelFactory implements ViewModelProvider.Factory {

  private final GifsRepository mGifsRepository;

  @Inject
  public ViewModelFactory(GifsRepository gifsRepository) {
    this.mGifsRepository = gifsRepository;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(GifListViewModel.class)) {
      return (T) new GifListViewModel(mGifsRepository);
    }
    throw new IllegalStateException("Unknown ViewModel class");
  }
}
