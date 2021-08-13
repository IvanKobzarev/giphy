package org.ivankobzarev.signalgiphy.api;

import com.google.gson.annotations.SerializedName;

public class Images {
  @SerializedName("original")
  public final Image original;
  @SerializedName("fixed_height")
  public final Image fixed_height;

  public Images(Image original, Image fixed_height) {
    this.original = original;
    this.fixed_height = fixed_height;
  }
}
