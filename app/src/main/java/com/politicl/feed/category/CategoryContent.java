package com.politicl.feed.category;

import android.support.annotation.Nullable;


import java.util.List;


public class CategoryContent {

    @SuppressWarnings("unused") @Nullable private String status;
    @SuppressWarnings("unused") @Nullable private int count;

    @SuppressWarnings("unused") @Nullable private List<MenuCategoryItem> categories;

    public List<MenuCategoryItem> categories(){
        return categories;
    }
}
