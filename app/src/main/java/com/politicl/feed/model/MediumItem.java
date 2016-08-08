package com.politicl.feed.model;

import android.support.annotation.NonNull;

public final class MediumItem {
    @SuppressWarnings("unused,NullableProblems") @NonNull private String url;

    @SuppressWarnings("unused,NullableProblems") @NonNull private int width;
    @SuppressWarnings("unused,NullableProblems") @NonNull private int height;

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
