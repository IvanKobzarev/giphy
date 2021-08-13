package org.ivankobzarev.signalgiphy.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.ivankobzarev.signalgiphy.ui.GifListViewModel;
import org.ivankobzarev.signalgiphy.ui.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

  @Binds
  abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

  @Binds
  @IntoMap
  @ViewModelKey(GifListViewModel.class)
  protected abstract ViewModel movieListViewModel(GifListViewModel gifsListViewModel);

}
