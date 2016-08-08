package com.politicl.feed.model;

import android.net.Uri;
import android.support.annotation.NonNull;

public final class Thumbnail {
    @SuppressWarnings("unused,NullableProblems") @NonNull private String url;
    @SuppressWarnings("unused") private int height;
    @SuppressWarnings("unused") private int width;

    @NonNull
    public String  source() {
        return url;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }
}
