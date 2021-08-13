package org.ivankobzarev.signalgiphy;

import android.app.Activity;
import android.app.Application;

import org.ivankobzarev.signalgiphy.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {

  @Inject
  DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

  @Override
  public DispatchingAndroidInjector<Activity> activityInjector() {
    return dispatchingAndroidInjector;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    DaggerAppComponent.builder()
        .application(this)
        .build()
        .inject(this);
  }
}
