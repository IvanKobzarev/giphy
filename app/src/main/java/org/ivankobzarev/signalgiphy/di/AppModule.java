package org.ivankobzarev.signalgiphy.di;

import org.ivankobzarev.signalgiphy.AppExecutors;
import org.ivankobzarev.signalgiphy.api.GiphyApi;
import org.ivankobzarev.signalgiphy.repository.GifsRepository;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

  @Provides
  @Singleton
  AppExecutors providesExecutors() {
    return new AppExecutors(
        Executors.newFixedThreadPool(3),
        new AppExecutors.MainThreadExecutor());
  }

  @Provides
  @Singleton
  Retrofit provideRetrofit(AppExecutors appExecutors) {
    return new Retrofit.Builder()
        .baseUrl("http://api.giphy.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .callbackExecutor(appExecutors.mainThread)
        .build();
  }

  @Provides
  @Singleton
  GiphyApi provideGiphyApi(Retrofit retrofit) {
    return retrofit.create(GiphyApi.class);
  }

  @Provides
  @Singleton
  GifsRepository provideGifsRepository(GiphyApi giphyApi, AppExecutors appExecutors) {
    return new GifsRepository(giphyApi, appExecutors);
  }
}
