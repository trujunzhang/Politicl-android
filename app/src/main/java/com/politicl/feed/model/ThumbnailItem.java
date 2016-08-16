package com.politicl.feed.model;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ThumbnailItem {

    @SuppressWarnings("unused,NullableProblems") @NonNull private MediumItem full;
    @SuppressWarnings("unused,NullableProblems") @NonNull private MediumItem thumbnail;
    @SuppressWarnings("unused,NullableProblems") @NonNull private MediumItem medium;

    @Nullable
    public String thumbnail() {
        if(full!= null)
            return full.source();
        return null;
    }
}
