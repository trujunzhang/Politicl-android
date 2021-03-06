package com.politicl.feed;

import android.content.Context;
import android.support.annotation.NonNull;

import com.politicl.feed.aggregated.AggregatedFeedContentClient;
import com.politicl.feed.aggregated.RestQueryPara;


public class FeedCoordinator extends FeedCoordinatorBase {

    public FeedCoordinator(@NonNull Context context, RestQueryPara para) {
        super(context, para);
    }

    @Override
    protected void buildScript(int age) {

        // hard-coded list of card types to load when continuing the feed
        if (age == 0) {
            init();
        }

        addPendingClient(new AggregatedFeedContentClient());
    }

    private void init() {
    }
}
