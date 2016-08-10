package com.politicl.feed.featured;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.feed.model.Card;
import com.politicl.feed.model.CardPageItem;
import com.politicl.feed.model.PostCard;
import com.politicl.feed.model.UtcDate;
import com.politicl.history.HistoryEntry;
import com.politicl.history.PageTitle;
import com.politicl.util.DateUtil;
import com.politicl.util.StringUtil;

import java.net.URI;

public class FeaturedArticleCard extends Card {
    @NonNull
    private UtcDate date;
    @NonNull
    private Site site;
    @NonNull
    private PostCard page;

    public FeaturedArticleCard(@NonNull PostCard page, @NonNull UtcDate date, @NonNull Site site) {
        this.page = page;
        this.site = site;
        this.date = date;
    }

    @Override
    @NonNull
    public String title() {
        return page.author();
    }

    @Override
    @NonNull
    public String subtitle() {
        return DateUtil.getFeedCardDateString(date.baseCalendar());
    }

    @NonNull
    public Site site() {
        return site;
    }

    @NonNull
    public String articleTitle() {
        return page.normalizedTitle();
    }

    @Nullable
    public String articleSubtitle() {
        return null;
    }


    @Override
    @Nullable
    public Uri image() {
        String thumbnail = page.thumbnail();
        if (thumbnail != null)
            return Uri.parse(thumbnail);
        return null;
    }

    @Nullable
    @Override
    public String extract() {
        return page.extract();
    }

    @NonNull
    public HistoryEntry historyEntry(int source) {
        PageTitle title = new PageTitle(articleTitle());
        if (image() != null) {
            title.setThumbUrl(image().toString());
        }
        title.setDescription(articleSubtitle());
        return new HistoryEntry(title, source);
    }
}
