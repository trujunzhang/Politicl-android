package com.politicl.feed.dataclient;

import android.content.Context;
import android.support.annotation.NonNull;

import com.politicl.Site;
import com.politicl.feed.category.MenuCategoryItem;
import com.politicl.feed.model.Card;

import java.util.List;

public interface FeedClient {
    void request(@NonNull Context context, @NonNull Site site, int age, @NonNull final Callback cb);
    void cancel();

    interface Callback {
        void success(@NonNull List<? extends Card> cards);
        void successForMenu(@NonNull List<MenuCategoryItem> cards);
        void error(@NonNull Throwable caught);
    }
}
