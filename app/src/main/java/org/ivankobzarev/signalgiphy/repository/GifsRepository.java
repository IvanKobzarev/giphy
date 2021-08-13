package org.ivankobzarev.signalgiphy.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.ivankobzarev.signalgiphy.AppExecutors;
import org.ivankobzarev.signalgiphy.api.Gif;
import org.ivankobzarev.signalgiphy.api.GifsResponse;
import org.ivankobzarev.signalgiphy.api.GiphyApi;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class GifsRepository {

  private static final String TAG = "gifs-repo";
  private static final int NETWORK_FETCH_COUNT = 20;

  private final GiphyApi mGiphyApi;
  private final AppExecutors mAppExecutors;

  private int nextTrendingOffsetToLoad = 0;
  private final List<Gif> mTrendingGifs = new CopyOnWriteArrayList<>();

  private final ConcurrentHashMap<String, CopyOnWriteArrayList<Gif>> mSearchGifsMap = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Integer> mSearchGifsNextOffsetToLoad = new ConcurrentHashMap<>();

  @Inject
  public GifsRepository(GiphyApi mGiphyApi, AppExecutors appExecutors) {
    this.mGiphyApi = mGiphyApi;
    this.mAppExecutors = appExecutors;
  }

  public void loadTrendingGifs(RepositoryCallback<List<Gif>> callback) {
    mAppExecutors.networkIO.execute(() -> {
      try {
        final Response<GifsResponse> response = mGiphyApi.getTrending(
            GiphyApi.API_KEY,
            NETWORK_FETCH_COUNT,
            nextTrendingOffsetToLoad).execute();
        mTrendingGifs.addAll(response.body().data);
        nextTrendingOffsetToLoad = nextTrendingOffsetToLoad + NETWORK_FETCH_COUNT;
        callback.onComplete(new Result.Success<>(mTrendingGifs));
      } catch (IOException e) {
        Log.e(TAG, "Error loading trending gifs", e);
        final Result<List<Gif>> errorResult = new Result.Error<>(e);
        callback.onComplete(errorResult);
      }
    });
  }

  public void loadSearchGifs(final String query, final RepositoryCallback<List<Gif>> callback) {
    mAppExecutors.networkIO.execute(() -> {
      CopyOnWriteArrayList<Gif> list = mSearchGifsMap.get(query);
      if (list == null) {
        list = new CopyOnWriteArrayList<>();
        mSearchGifsMap.put(query, list);
      }
      Integer nextOffsetToLoad = mSearchGifsNextOffsetToLoad.get(query);
      if (nextOffsetToLoad == null) {
        nextOffsetToLoad = 0;
      }

      try {
        final Response<GifsResponse> response = mGiphyApi.search(
            GiphyApi.API_KEY,
            query,
            NETWORK_FETCH_COUNT,
            nextOffsetToLoad).execute();
        list.addAll(response.body().data);
        mSearchGifsNextOffsetToLoad.put(query, nextOffsetToLoad + NETWORK_FETCH_COUNT);
        callback.onComplete(new Result.Success<>(list));
      } catch (IOException e) {
        Log.e(TAG, "Error loading search gifs", e);
        final Result<List<Gif>> errorResult = new Result.Error<>(e);
        callback.onComplete(errorResult);
      }
    });
  }


}
