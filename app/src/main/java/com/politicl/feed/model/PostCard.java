package com.politicl.feed.model;

import android.net.LocalSocketAddress;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.feed.featured.FeaturedArticleCard;
import com.politicl.util.StringUtil;

import java.util.List;

public class PostCard{

    @SuppressWarnings("unused,NullableProblems") @NonNull private int id;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String type;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String slug;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String url;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String status;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String title;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String title_plain;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String content;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String excerpt;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String date;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String modified;
    @SuppressWarnings("unused,NullableProblems") @NonNull private int comment_count;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String comment_status;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String thumbnail;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String thumbnail_size;

    @SuppressWarnings("unused,NullableProblems") @NonNull private AuthorItem author;

    @SuppressWarnings("unused,NullableProblems") @NonNull private ThumbnailItem thumbnail_images;

    @SuppressWarnings("unused,NullableProblems") @NonNull private List<TagItem> tags;
    @SuppressWarnings("unused,NullableProblems") @NonNull private List<PostCategoryItem> categories;

    @SuppressWarnings("unused,NullableProblems") @NonNull private CustomFieldsItem custom_fields;

    @NonNull
    public String title() {
        return title;
    }

    @NonNull
    public String normalizedTitle() {
        return title_plain;
    }

    @Nullable
    public String description() {
        return content;
    }

    @Nullable
    public String extract() {
        // todo: the service should strip IPA.
        return StringUtil.capitalizeFirstChar(content);
    }

    @NonNull
    public String author() {
        return author.author();
    }
    @NonNull
    public String customSourceUrl() {
        return custom_fields.customSourceUrl();
    }

    @Nullable
    public LocalSocketAddress.Namespace namespace() {
        return null;
    }

    @Nullable
    public String thumbnail() {
        return thumbnail_images.thumbnail();
    }


    public Card getFeaturedArticleCard(Site site) {
        CardPageItem page = new CardPageItem();
        UtcDate date = new UtcDate(10);
        return new FeaturedArticleCard(this,date,site);
    }
}
