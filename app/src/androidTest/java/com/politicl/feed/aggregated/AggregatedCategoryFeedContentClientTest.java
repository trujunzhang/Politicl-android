package com.politicl.feed.aggregated;


import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;

import com.politicl.MainActivity;
import com.politicl.PoliticlApp;
import com.politicl.feed.category.MenuCategoryItem;
import com.politicl.feed.dataclient.FeedClient;

import org.wikipedia.testlib.TestLatch;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.politicl.feed.model.Card;

import java.util.List;

public class AggregatedCategoryFeedContentClientTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final String TEST_POLITICL = "test.wikipedia.org";

    private final TestLatch completionLatch = new TestLatch();
    private MainActivity mActivity;

    public AggregatedCategoryFeedContentClientTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testFetcher() {

        RestQueryPara pagination = new RestQueryPara(1,"");
        new AggregatedFeedContentClient().request(mActivity.getApplicationContext(), PoliticlApp.getInstance().getSite(), pagination, new FeedClient.Callback() {
            @Override
            public void success(@NonNull List<? extends Card> cards) {
                completionLatch.countDown();
            }

            @Override
            public void successForMenu(@NonNull List<MenuCategoryItem> cards) {

            }

            @Override
            public void error(@NonNull Throwable caught) {
                completionLatch.countDown();
            }
        });

        completionLatch.await();
    }
}