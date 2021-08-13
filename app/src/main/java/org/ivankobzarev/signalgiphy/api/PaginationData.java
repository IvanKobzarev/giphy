package org.ivankobzarev.signalgiphy.api;

import com.google.gson.annotations.SerializedName;

public class PaginationData {
  @SerializedName("offset")
  public final int offset;
  @SerializedName("total_count")
  public final int total_count;
  @SerializedName("count")
  public final int count;

  public PaginationData(int offset, int total_count, int count) {
    this.offset = offset;
    this.total_count = total_count;
    this.count = count;
  }
}
