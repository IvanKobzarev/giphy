package org.ivankobzarev.signalgiphy;

import android.app.Application;

import org.ivankobzarev.signalgiphy.api.GiphyApi;
import org.ivankobzarev.signalgiphy.repository.GifsRepository;

import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

  //TODO: move to dagger di

  private GiphyApi mGiphyApi;
  private GifsRepository mGifsRepository;
  private AppExecutors mAppExecutors;

  public GifsRepository getGifsRepository() {
    return mGifsRepository;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mAppExecutors = new AppExecutors(Executors.newFixedThreadPool(3), new AppExecutors.MainThreadExecutor());

    mGiphyApi = new Retrofit.Builder()
        .baseUrl("http://api.giphy.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .callbackExecutor(mAppExecutors.mainThread)
        .build()
        .create(GiphyApi.class);

    mGifsRepository = new GifsRepository(mGiphyApi, mAppExecutors);
  }
}
