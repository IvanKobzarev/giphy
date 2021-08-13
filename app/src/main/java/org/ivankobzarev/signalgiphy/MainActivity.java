package org.ivankobzarev.signalgiphy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import org.ivankobzarev.signalgiphy.R;
import org.ivankobzarev.signalgiphy.ui.GifListFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    if (savedInstanceState == null) {
      GifListFragment fragment = new GifListFragment();

      getSupportFragmentManager().beginTransaction()
          .add(R.id.fragment_container, fragment, GifListFragment.TAG).commit();
    }
  }
}