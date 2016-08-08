package com.politicl.feed.category;

import android.support.annotation.Nullable;


public class MenuCategoryItem {
    @SuppressWarnings("unused") @Nullable private int id;
    @SuppressWarnings("unused") @Nullable private String slug;

    @SuppressWarnings("unused") @Nullable private String title;
    @SuppressWarnings("unused") @Nullable private String description;

    @SuppressWarnings("unused") @Nullable private int parent;
    @SuppressWarnings("unused") @Nullable private int post_count;


    public int id(){
        return id;
    }
    public String title(){
        return title;
    }
}
