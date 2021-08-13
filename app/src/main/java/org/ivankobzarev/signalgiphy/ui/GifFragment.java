package org.ivankobzarev.signalgiphy.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import org.ivankobzarev.signalgiphy.R;

public class GifFragment extends Fragment {
  public static final String TAG = "gif";
  private ImageView mImageView;
  public static final String INTENT_URL = "url";

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    final View view = LayoutInflater.from(getContext())
        .inflate(R.layout.fragment_gif, container, false);

    mImageView = view.findViewById(R.id.image);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    final String url = getActivity().getIntent().getStringExtra(INTENT_URL);
    Glide.with(getContext())
        .asGif()
        .load(url)
        .into(mImageView);
  }
}
