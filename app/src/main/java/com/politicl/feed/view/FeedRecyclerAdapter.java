package com.politicl.feed.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.politicl.feed.FeedCoordinatorBase;
import com.politicl.feed.featured.FeaturedArticleCard;
import com.politicl.feed.featured.FeaturedArticleCardView;
import com.politicl.feed.model.Card;
import com.politicl.feed.progress.ProgressCard;
import com.politicl.feed.progress.ProgressCardView;
import com.politicl.widgets.DefaultRecyclerAdapter;
import com.politicl.widgets.DefaultViewHolder;


public class FeedRecyclerAdapter extends DefaultRecyclerAdapter<Card, View> {
    private static final int VIEW_TYPE_SEARCH_BAR = 0;
    private static final int VIEW_TYPE_CONTINUE_READING = 1;
    private static final int VIEW_TYPE_BECAUSE_YOU_READ = 2;
    private static final int VIEW_TYPE_MOST_READ = 3;
    private static final int VIEW_TYPE_FEATURED_ARTICLE = 4;
    private static final int VIEW_TYPE_RANDOM = 5;
    private static final int VIEW_TYPE_MAIN_PAGE = 6;
    private static final int VIEW_TYPE_NEWS = 7;
    private static final int VIEW_TYPE_FEATURED_IMAGE = 8;
    private static final int VIEW_TYPE_PROGRESS = 99;

    @NonNull
    private FeedCoordinatorBase coordinator;
    @Nullable
    private FeedViewCallback callback;

    public static int getCardType(Card card) {
        if (card instanceof ProgressCard) {
            return VIEW_TYPE_PROGRESS;
        } else if (card instanceof FeaturedArticleCard) {
            return VIEW_TYPE_FEATURED_ARTICLE;
        } else {
            throw new IllegalStateException("Unknown type=" + card.getClass());
        }
    }

    public FeedRecyclerAdapter(@NonNull FeedCoordinatorBase coordinator, @Nullable FeedViewCallback callback) {
        super(coordinator.getCards());
        this.coordinator = coordinator;
        this.callback = callback;
    }

    @Override
    public DefaultViewHolder<View> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DefaultViewHolder<>(newView(parent.getContext(), viewType));
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder<View> holder, int position) {
        Card item = item(position);
        View view = holder.getView();

        if (coordinator.finished()
                && position == getItemCount() - 1
                && callback != null) {
            callback.onRequestMore();
        }

        if (isCardAssociatedWithView(view, item)) {
            // Don't bother reloading the same card into the same view
            return;
        }
        associateCardWithView(view, item);

        if (view instanceof FeaturedArticleCardView) {
            ((FeaturedArticleCardView) view).set((FeaturedArticleCard) item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getCardType(item(position));
    }

    @NonNull
    private View newView(@NonNull Context context, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_PROGRESS:
                return new ProgressCardView(context);
            case VIEW_TYPE_FEATURED_ARTICLE:
                return new FeaturedArticleCardView(context).setCallback(callback);
            default:
                throw new IllegalArgumentException("viewType=" + viewType);
        }
    }

    private boolean isCardAssociatedWithView(@NonNull View view, @NonNull Card card) {
        return card.equals(view.getTag());
    }

    private void associateCardWithView(@NonNull View view, @NonNull Card card) {
        view.setTag(card);
    }
}
