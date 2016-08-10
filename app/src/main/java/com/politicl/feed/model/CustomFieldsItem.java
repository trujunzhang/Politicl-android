package com.politicl.feed.model;

import android.net.LocalSocketAddress;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.feed.featured.FeaturedArticleCard;
import com.politicl.util.StringUtil;

import java.util.List;

public class CustomFieldsItem {

    @SuppressWarnings("unused,NullableProblems") @NonNull private List<String>  custom_source_url;

    @SuppressWarnings("unused,NullableProblems") @NonNull private List<Integer> post_views_count;


    public String customSourceUrl(){
        if (custom_source_url.size()>0)
            return custom_source_url.get(0);
        return "";
    }

    public int postViewsCount(){
        if (post_views_count.size()>0)
            return post_views_count.get(0);
        return 0;
    }

}
