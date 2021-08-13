package org.ivankobzarev.signalgiphy.di;

import org.ivankobzarev.signalgiphy.ui.GifListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
  @ContributesAndroidInjector
  abstract GifListFragment contributeMovieListFragment();
}
