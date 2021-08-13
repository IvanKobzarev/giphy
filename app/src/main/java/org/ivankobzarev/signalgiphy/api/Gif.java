package org.ivankobzarev.signalgiphy.api;

import com.google.gson.annotations.SerializedName;

public class Gif {
  @SerializedName("id")
  public final String id;
  @SerializedName("url")
  public final String url;
  @SerializedName("embed_url")
  public final String embed_url;
  @SerializedName("title")
  public final String title;
  @SerializedName("images")
  public final Images images;

  public Gif(String id, String url, String embed_url, String title, Images images) {
    this.id = id;
    this.url = url;
    this.embed_url = embed_url;
    this.title = title;
    this.images = images;
  }
}
