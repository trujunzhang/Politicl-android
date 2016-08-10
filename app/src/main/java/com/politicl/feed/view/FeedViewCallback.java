package com.politicl.feed.view;

import android.support.annotation.NonNull;

import com.politicl.PageTitleListCardItemCallback;
import com.politicl.feed.model.Card;
import com.politicl.widgets.ItemTouchHelperSwipeAdapter;


public interface FeedViewCallback extends ItemTouchHelperSwipeAdapter.Callback,
        PageTitleListCardItemCallback {
    void onRequestMore();
    void onSearchRequested();
    void onVoiceSearchRequested();
    boolean onRequestDismissCard(@NonNull Card card);
//    void onNewsItemSelected(@NonNull NewsItemCard card);
//    void onShareImage(@NonNull FeaturedImageCard card);
//    void onDownloadImage(@NonNull FeaturedImage image);
//    void onFeaturedImageSelected(@NonNull FeaturedImageCard card);
}
