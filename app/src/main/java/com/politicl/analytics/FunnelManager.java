package com.politicl.analytics;


import com.politicl.PoliticlApp;

import java.util.Hashtable;

/**
 * Creates and stores analytics tracking funnels.
 */
public class FunnelManager {
    private final PoliticlApp app;
//    private final Hashtable<PageTitle, EditFunnel> editFunnels = new Hashtable<>();

    public FunnelManager(PoliticlApp app) {
        this.app = app;
    }

//    public EditFunnel getEditFunnel(PageTitle title) {
//        if (!editFunnels.containsKey(title)) {
//            editFunnels.put(title, new EditFunnel(app, title));
//        }
//
//        return editFunnels.get(title);
//    }
}
