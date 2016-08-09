package com.politicl.feed.category;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.politicl.Site;
import com.politicl.dataclient.retrofit.RetrofitFactory;
import com.politicl.feed.aggregated.RestQueryPara;
import com.politicl.feed.dataclient.FeedClient;
import com.politicl.feed.featured.FeaturedArticleCard;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CategoryClient implements FeedClient {

    @Nullable
    private Call<CategoryContent> call;

    @Override
    public void request(@NonNull Context context, @NonNull Site site, RestQueryPara para, @NonNull Callback cb) {
        cancel();
        // TODO: Use app retrofit, etc., when feed endpoints are deployed to production
        String endpoint = String.format(Locale.ROOT, Prefs.getRestbaseUriFormat(), "http", site.authority());
        Retrofit retrofit = RetrofitFactory.newInstance(site, endpoint);
        CategoryClient.Service service = retrofit.create(Service.class);
        call = service.get();
        call.enqueue(new CallbackAdapter(cb, site));
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
         * <p>
         * like: http://www.politicl.com/api/get_category_index/
         */
        @NonNull
        @GET("get_category_index")
        Call<CategoryContent> get();
    }

    private static class CallbackAdapter implements retrofit2.Callback<CategoryContent> {
        @NonNull
        private final Callback cb;
        @NonNull
        private final Site site;

        CallbackAdapter(@NonNull Callback cb, @NonNull Site site) {
            this.cb = cb;
            this.site = site;
        }

        @Override
        public void onResponse(Call<CategoryContent> call,
                               Response<CategoryContent> response) {
            if (response.isSuccessful()) {
                List<MenuCategoryItem> cards = new ArrayList<>();
                CategoryContent content = response.body();
                if (content != null) {
                    cards = content.categories();
                }
                cb.successForMenu(cards);
            } else {
                L.v(response.message());
                cb.error(new IOException(response.message()));
            }
        }

        @Override
        public void onFailure(Call<CategoryContent> call, Throwable caught) {
            L.v(caught);
            cb.error(caught);
        }
    }
}
