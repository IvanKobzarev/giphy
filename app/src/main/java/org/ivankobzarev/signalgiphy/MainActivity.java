package org.ivankobzarev.signalgiphy;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import org.ivankobzarev.signalgiphy.ui.GifListFragment;
import org.ivankobzarev.signalgiphy.ui.ViewModelFactory;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

  @Inject
  DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
  @Inject
  ViewModelFactory mViewModelFactory;

  @Override
  public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
    return dispatchingAndroidInjector;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      GifListFragment fragment = new GifListFragment();

      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, fragment, GifListFragment.TAG).commit();
    }
  }

  public ViewModelFactory getViewModelFactory() {
    return mViewModelFactory;
  }
}