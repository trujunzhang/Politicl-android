package com.politicl.feed.aggregated;

import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.feed.featured.FeaturedArticleCard;
import com.politicl.feed.model.Card;
import com.politicl.feed.model.CardPageItem;
import com.politicl.feed.model.PostCard;
import com.politicl.feed.model.PostCategoryItem;
import com.politicl.feed.model.UtcDate;


import java.util.List;
import android.net.LocalSocketAddress;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.feed.featured.FeaturedArticleCard;
import com.politicl.util.StringUtil;

import java.util.List;

public class AggregatedFeedContent {

    @SuppressWarnings("unused") @Nullable private String status;
    @SuppressWarnings("unused") @Nullable private int count;
    @SuppressWarnings("unused") @Nullable private int count_total;
    @SuppressWarnings("unused") @Nullable private int pages;

    @SuppressWarnings("unused") @Nullable private List<PostCard> posts;

    @SuppressWarnings("unused,NullableProblems") @NonNull private PostCategoryItem category;

    public void appendPostToCard(List<Card> cards, Site site){
        if (posts!= null){
            for(PostCard item : posts){
                cards.add(item.getFeaturedArticleCard(site));
            }
        }
    }
}