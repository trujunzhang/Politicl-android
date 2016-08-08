package com.politicl.feed.category;

import static org.junit.Assert.*;


import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.politicl.MainActivity;
import com.politicl.PoliticlApp;
import com.politicl.Site;
import com.politicl.feed.dataclient.FeedClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.wikipedia.testlib.TestLatch;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static android.support.test.InstrumentationRegistry.getInstrumentation;

import com.politicl.feed.dataclient.FeedClient;
import com.politicl.feed.model.Card;

import java.util.List;

public class CategoryClientTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private static final String TEST_POLITICL = "test.wikipedia.org";

    private final TestLatch completionLatch = new TestLatch();
    private MainActivity mActivity;

    public CategoryClientTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        mActivity = getActivity();
    }

    public void testFetcher() {

        CategoryClient client = new CategoryClient();

        Site site = PoliticlApp.getInstance().getSite();
        client.request(mActivity.getApplicationContext(), site, -1, new FeedClient.Callback() {
            @Override
            public void success(@NonNull List<? extends Card> cards) {
                completionLatch.countDown();
            }

            @Override
            public void successForMenu(@NonNull List<MenuCategoryItem> cards) {
                completionLatch.countDown();
            }

            @Override
            public void error(@NonNull Throwable caught) {
                completionLatch.countDown();
            }
        });

        completionLatch.await();
    }
}