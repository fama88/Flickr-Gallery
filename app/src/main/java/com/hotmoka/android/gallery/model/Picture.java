package com.hotmoka.android.gallery.model;

public class Picture {
    public final String title;
    public final String url;
    public final String thumbnailUrl;

    public Picture(String title, String url, String thumbnailUrl) {
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }
}
