package com.politicl.feed;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.politicl.BackPressedHandler;
import com.politicl.MainActivityToolbarProvider;
import com.politicl.PoliticlApp;
import com.politicl.R;
import com.politicl.activity.FragmentUtil;
import com.politicl.analytics.FeedFunnel;
import com.politicl.feed.aggregated.RestQueryPara;
import com.politicl.feed.model.Card;
import com.politicl.feed.view.FeedRecyclerAdapter;
import com.politicl.feed.view.FeedView;
import com.politicl.feed.view.FeedViewCallback;
import com.politicl.settings.Prefs;
import com.politicl.util.DimenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FeedFragment extends Fragment implements BackPressedHandler,
        MainActivityToolbarProvider {

    public static final String ARG_REST_PARA = "REST_PARA";
    @BindView(R.id.feed_app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.feed_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.fragment_feed_feed)
    FeedView feedView;
    @BindView(R.id.feed_toolbar)
    Toolbar toolbar;
    private Unbinder unbinder;
    private PoliticlApp app;
    private FeedCoordinator coordinator;
    private FeedFunnel funnel;
    private FeedViewCallback feedCallback = new FeedCallback();
    private FeedHeaderOffsetChangedListener headerOffsetChangedListener = new FeedHeaderOffsetChangedListener();
    private int searchIconShowThresholdPx;
    private boolean searchIconVisible;

    @Nullable
    private RestQueryPara para;

    public interface Callback {
        void onFeedSearchRequested();

        void onFeedVoiceSearchRequested();
//        void onFeedSelectPage(HistoryEntry entry);
//        void onFeedAddPageToList(HistoryEntry entry);
//        void onFeedSharePage(HistoryEntry entry);
//        void onFeedNewsItemSelected(NewsItemCard card);
//        void onFeedShareImage(FeaturedImageCard card);
//        void onFeedDownloadImage(FeaturedImage image);
//        void onFeaturedImageSelected(FeaturedImageCard card);
    }

    @NonNull
    public static FeedFragment newInstance(@Nullable RestQueryPara para) {
        FeedFragment fragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_REST_PARA, para);
        fragment.setArguments(args);
        fragment.setRetainInstance(false);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        para = getArguments().getParcelable(ARG_REST_PARA);
        app = PoliticlApp.getInstance();
        coordinator = new FeedCoordinator(getContext(), para);
        coordinator.more(app.getSite());
        funnel = new FeedFunnel(app);
        Prefs.pageLastShown(0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        unbinder = ButterKnife.bind(this, view);
        feedView.set(coordinator, feedCallback);
        appBarLayout.addOnOffsetChangedListener(headerOffsetChangedListener);
        searchIconShowThresholdPx = (int) getResources().getDimension(R.dimen.view_feed_header_height) - DimenUtil.getContentTopOffsetPx(getContext());

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            // TODO: remove when handled correctly by appcompat.
            swipeRefreshLayout.setFitsSystemWindows(true);
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                funnel.refresh(coordinator.getCurrentPageNumber());
                coordinator.reset(null);
                feedView.update();
                coordinator.more(app.getSite());
            }
        });

        coordinator.setFeedUpdateListener(new FeedCoordinator.FeedUpdateListener() {
            @Override
            public void update(List<Card> cards) {
                if (isAdded()) {
                    swipeRefreshLayout.setRefreshing(false);
                    feedView.update();
                }
            }
        });

        toolbar.setTitle(para.getTitle());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        coordinator.setFeedUpdateListener(null);
        swipeRefreshLayout.setOnRefreshListener(null);
        appBarLayout.removeOnOffsetChangedListener(headerOffsetChangedListener);
        unbinder.unbind();
        unbinder = null;
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        if (searchIconVisible) {
//            inflater.inflate(R.menu.menu_feed, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.menu_feed_search:
//                if (getCallback() != null) {
//                    getCallback().onFeedSearchRequested();
//                }
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }

    public void scrollToTop() {
        feedView.smoothScrollToPosition(0);
    }

    @Nullable
    private Callback getCallback() {
        return FragmentUtil.getCallback(this, Callback.class);
    }

    private class FeedCallback implements FeedViewCallback {
        @Override
        public void onRequestMore() {
            funnel.requestMore(coordinator.getCurrentPageNumber());
            coordinator.more(app.getSite());
        }

//        @Override
//        public void onSelectPage(@NonNull HistoryEntry entry) {
//            if (getCallback() != null) {
//                getCallback().onFeedSelectPage(entry);
//            }
//        }
//
//        @Override
//        public void onAddPageToList(@NonNull HistoryEntry entry) {
//            if (getCallback() != null) {
//                getCallback().onFeedAddPageToList(entry);
//            }
//        }
//
//        @Override
//        public void onSharePage(@NonNull HistoryEntry entry) {
//            if (getCallback() != null) {
//                getCallback().onFeedSharePage(entry);
//            }
//        }

        @Override
        public void onSearchRequested() {
            if (getCallback() != null) {
                getCallback().onFeedSearchRequested();
            }
        }

        @Override
        public void onVoiceSearchRequested() {
            if (getCallback() != null) {
                getCallback().onFeedVoiceSearchRequested();
            }
        }

        @Override
        public boolean onRequestDismissCard(@NonNull Card card) {
            int position = coordinator.dismissCard(card);
            funnel.dismissCard(FeedRecyclerAdapter.getCardType(card), position);
            showDismissCardUndoSnackbar(card, position);
            return true;
        }

        @Override
        public void onSwiped(@IntRange(from = 0) int itemPos) {
            onRequestDismissCard(coordinator.getCards().get(itemPos));
        }

//        @Override
//        public void onNewsItemSelected(@NonNull NewsItemCard card) {
//            if (getCallback() != null) {
//                getCallback().onFeedNewsItemSelected(card);
//            }
//        }
//
//        @Override
//        public void onShareImage(@NonNull FeaturedImageCard card) {
//            if (getCallback() != null) {
//                getCallback().onFeedShareImage(card);
//            }
//        }
//
//        @Override
//        public void onDownloadImage(@NonNull FeaturedImage image) {
//            if (getCallback() != null) {
//                getCallback().onFeedDownloadImage(image);
//            }
//        }
//
//        @Override
//        public void onFeaturedImageSelected(@NonNull FeaturedImageCard card) {
//            if (getCallback() != null) {
//                getCallback().onFeaturedImageSelected(card);
//            }
//        }
    }

    private class FeedHeaderOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            boolean shouldShowSearchIcon = !((searchIconShowThresholdPx + verticalOffset) > 0);
            if (shouldShowSearchIcon != searchIconVisible) {
                searchIconVisible = shouldShowSearchIcon;
                getActivity().supportInvalidateOptionsMenu();
            }
            swipeRefreshLayout.setEnabled(verticalOffset == 0);
        }
    }

    private void showDismissCardUndoSnackbar(final Card card, final int position) {
//        Snackbar snackbar = FeedbackUtil.makeSnackbar(getView(),
//                getString(R.string.menu_feed_card_dismissed),
//                FeedbackUtil.LENGTH_DEFAULT);
//        snackbar.setAction(R.string.feed_undo_dismiss_card, new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                coordinator.insertCard(card, position);
//            }
//        });
//        snackbar.show();
    }

    public void resetFeed(RestQueryPara para) {
        funnel.reset(para);
        toolbar.setTitle(para.getTitle());
        swipeRefreshLayout.setRefreshing(false);
        scrollToTop();
        coordinator.reset(para);
        feedView.update();
        coordinator.more(app.getSite());
    }

}
