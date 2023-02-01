package com.yuanren.tvinteractions.model;

import java.io.Serializable;

public class XRayItem implements Serializable {
    private long movieId;
    private long itemId;
    private String name;
    private String imageUrl;
    private String link;

    public XRayItem() {
    }

    public XRayItem(long movieId, long itemId, String name, String imageUrl, String link) {
        this.movieId = movieId;
        this.itemId = itemId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    public long getMovieId() {
        return movieId;
    }

    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
