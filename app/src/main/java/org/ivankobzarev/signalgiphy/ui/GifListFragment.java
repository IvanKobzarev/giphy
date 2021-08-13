package org.ivankobzarev.signalgiphy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.ivankobzarev.signalgiphy.R;
import org.ivankobzarev.signalgiphy.api.Gif;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GifListFragment extends Fragment {

  public static final String TAG = "gifs-list";
  private static final int PAGE_LOAD_OFFSET = 3;
  private RecyclerView mRecyclerView;
  private GifListViewModel mViewModel;
  private GifsListAdapter mAdapter;
  private final Observer<List<Gif>> mAdapterGifsObserver = gifs -> {
    mAdapter.setItems(gifs);
  };
  private String mSearchQuery;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setHasOptionsMenu(true);
  }

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    final View view = LayoutInflater.from(getContext())
        .inflate(R.layout.fragment_gifs_list, container, false);

    mRecyclerView = view.findViewById(R.id.recycler);
    mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
    mAdapter = new GifsListAdapter();
    mRecyclerView.setAdapter(mAdapter);
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mViewModel = new ViewModelProvider(getActivity(), new ViewModelFactory(getActivity().getApplication())).get(GifListViewModel.class);
    uiStateTrending();
  }

  private class GifsListDiffCallback extends DiffUtil.Callback {

    private final List<Gif> mOldList;
    private final List<Gif> mNewList;

    private GifsListDiffCallback(List<Gif> oldList, List<Gif> newList) {
      this.mOldList = oldList;
      this.mNewList = newList;
    }

    @Override
    public int getOldListSize() {
      return mOldList.size();
    }

    @Override
    public int getNewListSize() {
      return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      return mOldList.get(oldItemPosition).url.equals(mNewList.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      return areItemsTheSame(oldItemPosition, newItemPosition);
    }
  }

  private class GifsListAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private List<Gif> mItems = new ArrayList<>();

    public void setItems(@Nullable List<Gif> items) {
      final List<Gif> newItems = items != null ? items : Collections.emptyList();
      final GifsListDiffCallback diffCallback = new GifsListDiffCallback(this.mItems, newItems);
      final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
      this.mItems.clear();
      this.mItems.addAll(newItems);
      diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public void onClick(View v) {
      final String url = (String) v.getTag();
      final Intent intent = new Intent(getContext(), GifActivity.class);
      intent.putExtra(GifFragment.INTENT_URL, url);
      startActivity(intent);
    }

    private class GifViewHolder extends RecyclerView.ViewHolder {
      public final ImageView imageView;
      private final TextView textView;

      public GifViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.image);
        textView = itemView.findViewById(R.id.text);
      }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gifs_list, parent, false);
      view.setOnClickListener(this);
      return new GifViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      final GifViewHolder gifViewHolder = (GifViewHolder) holder;
      final Gif item = mItems.get(position);
      String url = item.images.fixed_height.url;
      gifViewHolder.itemView.setTag(item.images.original.url);
      Glide.with(gifViewHolder.imageView.getContext())
          .asGif()
          .load(url)
          .into(gifViewHolder.imageView);

      if (getItemCount() - position < PAGE_LOAD_OFFSET) {
        if (mSearchQuery == null) {
          mViewModel.loadTrendingGifs();
        } else {
          mViewModel.loadSearchGifs(mSearchQuery);
        }
      }
    }

    @Override
    public int getItemCount() {
      return mItems == null ? 0 : mItems.size();
    }

  }

  void uiStateSearch(String query) {
    mViewModel.getTrendingGifs().removeObserver(mAdapterGifsObserver);
    if (mSearchQuery != null) {
      mViewModel.getSearchGifs(mSearchQuery).removeObservers(getViewLifecycleOwner());
    }
    final LiveData<List<Gif>> searchGifs = mViewModel.getSearchGifs(query);
    searchGifs.observe(getViewLifecycleOwner(), mAdapterGifsObserver);

    List<Gif> value = searchGifs.getValue();
    mAdapter.setItems(value);
    if (value == null) {
      mViewModel.loadSearchGifs(query);
    }
    mSearchQuery = query;
  }

  void uiStateTrending() {
    mViewModel.getSearchGifs(mSearchQuery).removeObservers(getViewLifecycleOwner());
    final LiveData<List<Gif>> trendingGifs = mViewModel.getTrendingGifs();
    trendingGifs.observe(getViewLifecycleOwner(), mAdapterGifsObserver);
    List<Gif> value = trendingGifs.getValue();
    mAdapter.setItems(value);
    if (value == null) {
      mViewModel.loadTrendingGifs();
    }
    mSearchQuery = null;
  }

  @Override
  public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
    super.onCreateOptionsMenu(menu, inflater);
    inflater.inflate(R.menu.menu, menu);
    MenuItem menuItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) menuItem.getActionView();
    searchView.setQueryHint("Type here to search");
    searchView.setOnCloseListener(() -> {
      uiStateTrending();
      return true;
    });
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        uiStateSearch(query);
        return true;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

    searchView.setOnQueryTextFocusChangeListener((v, hasFocus) -> {
      if (!hasFocus) {
        uiStateTrending();
      }
    });
  }
}
