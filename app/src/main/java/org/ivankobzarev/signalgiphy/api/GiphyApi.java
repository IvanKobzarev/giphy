package org.ivankobzarev.signalgiphy.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApi {
  String API_KEY = "57gPL65mzFw0GA8rvvdvOb1WY93I6l8I";

  @GET("/v1/gifs/trending")
  Call<GifsResponse> getTrending(
      @Query("api_key") String api_key,
      @Query("limit") int limit,
      @Query("offset") int offset);

  @GET("/v1/gifs/search")
  Call<GifsResponse> search(
      @Query("api_key") String api_key,
      @Query("q") String query,
      @Query("limit") int limit,
      @Query("offset") int offset);

}
