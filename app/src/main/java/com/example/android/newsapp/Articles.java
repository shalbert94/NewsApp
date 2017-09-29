package com.example.android.newsapp;

import android.graphics.drawable.Drawable;

/**
 * Created by shalom on 2017-04-20.
 */

public class Articles {
    private String title;
    private String timeStamp;
    private String thumbnailUrl;
    private Drawable thumbnailDrawable;

    public Articles(String title, String timeStamp, String thumbnailUrl, Drawable thumbnailDrawable) {
        this.title = title;
        this.timeStamp = timeStamp;
        this.thumbnailUrl = thumbnailUrl;
        this.thumbnailDrawable = thumbnailDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public Drawable getThumbnailDrawable() {
        return thumbnailDrawable;
    }
}
