package com.politicl.analytics;


import com.politicl.PoliticlApp;
import com.politicl.feed.aggregated.RestQueryPara;

public class FeedFunnel extends Funnel {
    private static final String SCHEMA_NAME = "PoliticlAppFeed";
    private static final int REVISION = 15734713;

    public FeedFunnel(PoliticlApp app) {
        super(app, SCHEMA_NAME, REVISION, Funnel.SAMPLE_LOG_100);
    }

    public void requestMore(int currentPageNumber) {
        log(
                "action", "more",
                "currentPageNumber", currentPageNumber
        );
    }

    public void refresh(int currentPageNumber) {
        log(
                "action", "refresh",
                "currentPageNumber", currentPageNumber
        );
    }

    public void reset(int currentPageNumber, int category_id, String title) {
        log(
                "action", "reset",
                "title", title,
                "currentPageNumber", currentPageNumber,
                "category_id", category_id
        );
    }

    public void dismissCard(int cardType, int position) {
        log(
                "action", "dismiss",
                "cardType", cardType,
                "position", position
        );
    }

    public void logRestQueryPara(RestQueryPara para) {
        log(
                "currentPageNumber", para.getCurrentPageNumber(),
                "totalPages", para.getTotalPages(),
                "category_id", para.getCategory_id()
        );
    }
}
