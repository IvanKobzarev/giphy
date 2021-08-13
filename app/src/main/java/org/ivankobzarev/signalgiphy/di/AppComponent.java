package org.ivankobzarev.signalgiphy.di;

import android.app.Application;

import org.ivankobzarev.signalgiphy.App;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Component(modules = {
    AppModule.class,
    ViewModelModule.class,
    ActivityModule.class,
    FragmentModule.class,
    AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent {

  @Component.Builder
  interface Builder {

    @BindsInstance
    Builder application(Application application);

    AppComponent build();
  }

  void inject(App app);
}