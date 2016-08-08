package com.politicl.feed.model;

import android.net.LocalSocketAddress;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.JsonAdapter;
import com.politicl.util.StringUtil;


public final class CardPageItem {
    @SuppressWarnings("unused,NullableProblems") @NonNull private String title;
    @SuppressWarnings("unused,NullableProblems") @NonNull private String normalizedtitle;
    @SuppressWarnings("unused") @Nullable private Thumbnail thumbnail;
    @SuppressWarnings("unused") @Nullable private String description;
    @SuppressWarnings("unused") @Nullable private String extract;
    @SuppressWarnings("unused") @Nullable  private LocalSocketAddress.Namespace namespace;

    @NonNull
    public String title() {
        return title;
    }

    @NonNull
    public String normalizedTitle() {
        return normalizedtitle;
    }

    @Nullable
    public String description() {
        return description;
    }

    @Nullable
    public String extract() {
        // todo: the service should strip IPA.
        return extract == null ? null : StringUtil.sanitizeText(extract);
    }

    @Nullable
    public LocalSocketAddress.Namespace namespace() {
        return namespace;
    }

    @Nullable
    public String thumbnail() {
        return thumbnail != null ? thumbnail.source() : null;
    }
}
