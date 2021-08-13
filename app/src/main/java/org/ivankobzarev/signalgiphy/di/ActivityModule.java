package org.ivankobzarev.signalgiphy.di;

import org.ivankobzarev.signalgiphy.MainActivity;
import org.ivankobzarev.signalgiphy.di.FragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

  @ContributesAndroidInjector(modules = FragmentModule.class)
  abstract MainActivity contributeMainActivity();
}