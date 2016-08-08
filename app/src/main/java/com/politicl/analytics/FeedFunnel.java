package com.politicl.analytics;


import com.politicl.PoliticlApp;

public class FeedFunnel extends Funnel {
    private static final String SCHEMA_NAME = "PoliticlAppFeed";
    private static final int REVISION = 15734713;

    public FeedFunnel(PoliticlApp app) {
        super(app, SCHEMA_NAME, REVISION, Funnel.SAMPLE_LOG_100);
    }

    public void requestMore(int age) {
        log(
                "action", "more",
                "age", age
        );
    }

    public void refresh(int age) {
        log(
                "action", "refresh",
                "age", age
        );
    }

    public void dismissCard(int cardType, int position) {
        log(
                "action", "dismiss",
                "cardType", cardType,
                "position", position
        );
    }
}
