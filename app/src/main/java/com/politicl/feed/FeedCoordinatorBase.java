package com.politicl.feed;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.politicl.Site;
import com.politicl.feed.aggregated.RestQueryPara;
import com.politicl.feed.category.MenuCategoryItem;
import com.politicl.feed.dataclient.FeedClient;
import com.politicl.feed.model.Card;
import com.politicl.feed.progress.ProgressCard;

import java.util.ArrayList;
import java.util.List;

public abstract class FeedCoordinatorBase {

    private RestQueryPara para;
    private ProgressDialog dialog;

    public interface FeedUpdateListener {
        void update(List<Card> cards);
    }

    public void showLoadingDialog() {
        this.dialog = new ProgressDialog(this.context); // this = YourActivity
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Loading. Please wait...");
        dialog.setIndeterminate(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void dismissDialog() {
        if (this.dialog != null) {
            this.dialog.dismiss();
            this.dialog = null;
        }
    }

    @NonNull
    private Context context;
    @Nullable
    private Site site;
    @Nullable
    private FeedUpdateListener updateListener;
    @NonNull
    private final List<Card> cards = new ArrayList<>();
    private List<FeedClient> pendingClients = new ArrayList<>();
    private FeedClient.Callback exhaustionClientCallback = new ExhaustionClientCallback();
    private Card progressCard = new ProgressCard();

    public FeedCoordinatorBase(@NonNull Context context, RestQueryPara para) {
        this.context = context;
        this.para = para;
    }

    @NonNull
    public List<Card> getCards() {
        return cards;
    }

    public void setFeedUpdateListener(@Nullable FeedUpdateListener listener) {
        updateListener = listener;
    }

    public void reset(RestQueryPara para) {
        if (para != null) {
            this.para = para;
        }
        site = null;
        this.para.reset();
        for (FeedClient client : pendingClients) {
            client.cancel();
        }
        pendingClients.clear();
        cards.clear();
//        appendProgressCard(cards);
    }

    public void more(@NonNull Site site) {
        this.site = site;
        if (cards.size() > 1) {
            para.nextPagination();
        }

        if (para.shouldShowDialog()) {
            this.showLoadingDialog();
        }

        buildScript(para.getCurrentPageNumber());
        requestNextCard(site);
    }

    public boolean finished() {
        return pendingClients.isEmpty();
    }

    public int getCurrentPageNumber() {
        return para.getCurrentPageNumber();
    }

    public int dismissCard(@NonNull Card card) {
        int position = cards.indexOf(card);
        cards.remove(card);
        if (updateListener != null) {
            updateListener.update(cards);
        }
        return position;
    }

    public void insertCard(@NonNull Card card, int position) {
        cards.add(position, card);
        if (updateListener != null) {
            updateListener.update(cards);
        }
    }

    protected abstract void buildScript(int age);

    protected void addPendingClient(FeedClient client) {
        pendingClients.add(client);
    }

    private void requestNextCard(@NonNull Site site) {
        if (pendingClients.isEmpty()) {
            this.dismissDialog();
            return;
        }
        pendingClients.remove(0).request(context, site, this.para, exhaustionClientCallback);
    }

    private class ExhaustionClientCallback implements FeedClient.Callback {
        @Override
        public void success(@NonNull List<? extends Card> cardList) {
            cards.addAll(cardList);
            appendProgressCard(cards);
            if (updateListener != null) {
                updateListener.update(cards);
            }
            //noinspection ConstantConditions
            requestNextCard(site);
        }

        @Override
        public void successForMenu(@NonNull List<MenuCategoryItem> cards) {
            // This method only use for Menu categories.
        }

        @Override
        public void error(@NonNull Throwable caught) {
            //noinspection ConstantConditions
            requestNextCard(site);
        }
    }

    private void appendProgressCard(List<Card> cards) {
        cards.remove(progressCard);
        cards.add(progressCard);
    }
}
