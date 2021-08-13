package org.ivankobzarev.signalgiphy.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.ivankobzarev.signalgiphy.api.Gif;
import org.ivankobzarev.signalgiphy.repository.GifsRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GifListViewModel extends ViewModel {

  private final GifsRepository mGifsRepository;
  private final HashMap<String, MutableLiveData<List<Gif>>> mSearchGifsMap = new HashMap<>();
  private final Map<String, Boolean> searchLoadingMap = new HashMap<>();

  private MutableLiveData<List<Gif>> mTrendingGifs;
  private boolean isTrendingLoading = false;

  @Inject
  public GifListViewModel(GifsRepository gifsRepository) {
    this.mGifsRepository = gifsRepository;
  }

  public LiveData<List<Gif>> getTrendingGifs() {
    if (mTrendingGifs == null) {
      mTrendingGifs = new MutableLiveData<>();
    }
    return mTrendingGifs;
  }

  public LiveData<List<Gif>> getSearchGifs(final String query) {
    MutableLiveData<List<Gif>> gifs = mSearchGifsMap.get(query);
    if (gifs == null) {
      gifs = new MutableLiveData<>();
      mSearchGifsMap.put(query, gifs);
    }
    return gifs;
  }

  private <T> void postValue(LiveData<T> liveData, T value) {
    ((MutableLiveData) liveData).postValue(value);
  }

  public void loadTrendingGifs() {
    if (isTrendingLoading) {
      return;
    }
    isTrendingLoading = true;
    mGifsRepository.loadTrendingGifs(result -> {
      if (result.isOk()) {
        postValue(getTrendingGifs(), result.getData());
      } else {
        // show error state
      }
      isTrendingLoading = false;
    });
  }

  public void loadSearchGifs(final String query) {
    final Boolean isLoading = searchLoadingMap.get(query);
    if (isLoading != null && isLoading) {
      return;
    }

    searchLoadingMap.put(query, true);
    mGifsRepository.loadSearchGifs(query, result -> {
      if (result.isOk()) {
        postValue(getSearchGifs(query), result.getData());
      } else {
        // show error state
      }
      searchLoadingMap.put(query, false);
    });
  }
}
