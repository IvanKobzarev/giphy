package org.ivankobzarev.signalgiphy.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import org.ivankobzarev.signalgiphy.R;
import org.ivankobzarev.signalgiphy.ui.GifFragment;
import org.ivankobzarev.signalgiphy.ui.GifListFragment;

public class GifActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_gif);
    if (savedInstanceState == null) {
      GifFragment fragment = new GifFragment();

      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, fragment, GifFragment.TAG).commit();
    }
  }
}