package com.politicl.feed.aggregated;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.dataclient.retrofit.RetrofitFactory;
import com.politicl.feed.dataclient.FeedClient;
import com.politicl.feed.model.Card;
import com.politicl.feed.model.UtcDate;
import com.politicl.settings.Prefs;
import com.politicl.util.DateUtil;
import com.politicl.util.log.L;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class AggregatedCategoryFeedContentClient implements FeedClient {

    @Nullable
    private Call<AggregatedFeedContent> call;

    @Override
    public void request(@NonNull Context context, @NonNull Site site, RestQueryPara pagination, @NonNull Callback cb) {
        cancel();
        // TODO: Use app retrofit, etc., when feed endpoints are deployed to production
        String endpoint = String.format(Locale.ROOT, Prefs.getRestbaseUriFormat(), "http", site.authority());
        Retrofit retrofit = RetrofitFactory.newInstance(site, endpoint);
        AggregatedCategoryFeedContentClient.Service service = retrofit.create(Service.class);
        call = service.get(age, 1);
        call.enqueue(new CallbackAdapter(cb, site, age));
    }

    @Override
    public void cancel() {
        if (call == null) {
            return;
        }
        call.cancel();
        call = null;
    }

    private interface Service {

        /**
         * Gets aggregated content for the feed for the date provided.
         * <p/>
         * like: http://www.politicl.com/api/get_recent_posts/?page={num}
         *
         * @param num the index of the pagination
         */
        @NonNull
        @GET("get_category_posts")
        Call<AggregatedFeedContent> get(@Query("page") int num, @Query("category_id") int category_id);
    }

    private static class CallbackAdapter implements retrofit2.Callback<AggregatedFeedContent> {
        @NonNull
        private final Callback cb;
        @NonNull
        private final Site site;
        private final int age;

        CallbackAdapter(@NonNull Callback cb, @NonNull Site site, int age) {
            this.cb = cb;
            this.site = site;
            this.age = age;
        }

        @Override
        public void onResponse(Call<AggregatedFeedContent> call,
                               Response<AggregatedFeedContent> response) {
            if (response.isSuccessful()) {
                UtcDate date = DateUtil.getUtcRequestDateFor(age);
                List<Card> cards = new ArrayList<>();
                AggregatedFeedContent content = response.body();
                if (content != null) {
                    content.appendPostToCard(cards, this.site);
                }
                cb.success(cards);
            } else {
                L.v(response.message());
                cb.error(new IOException(response.message()));
            }
        }

        @Override
        public void onFailure(Call<AggregatedFeedContent> call, Throwable caught) {
            L.v(caught);
            cb.error(caught);
        }
    }
}
