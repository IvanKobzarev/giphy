package org.ivankobzarev.signalgiphy.api;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GifsResponse {
  @SerializedName("data")
  public final List<Gif> data;
  @SerializedName("pagination")
  public final PaginationData pagination;

  public GifsResponse(List<Gif> data, PaginationData pagination) {
    this.data = data;
    this.pagination = pagination;
  }
}
