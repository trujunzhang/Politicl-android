package com.politicl.feed;

import android.content.Context;
import android.support.annotation.NonNull;

import com.politicl.feed.aggregated.AggregatedFeedContentClient;


public class FeedCoordinator extends FeedCoordinatorBase {

    public FeedCoordinator(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void buildScript(int age) {

        // hard-coded list of card types to load when continuing the feed
        if (age == 0) {
            init();
        }

        addPendingClient(new AggregatedFeedContentClient());
//        addPendingClient(new ContinueReadingClient());
//        if (age == 0) {
//            addPendingClient(new MainPageClient());
//        }
//        addPendingClient(new BecauseYouReadClient());
//        if (age == 0) {
//            addPendingClient(new RandomClient());
//        }
    }

    private void init() {
//        addPendingClient(new SearchClient());
    }
}
