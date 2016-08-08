package com.politicl;


import android.app.Application;
import android.support.annotation.NonNull;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.politicl.settings.Prefs;
import com.politicl.util.ReleaseUtil;

import java.util.Map;
import java.util.UUID;


import okhttp3.Headers;
import okhttp3.Request;

public class PoliticlApp extends Application {

    /**
     * Singleton instance of PoliticlApp
     */
    private static PoliticlApp INSTANCE;

    public PoliticlApp() {
        INSTANCE = this;
    }

    private Site site;

    /**
     * Returns the singleton instance of the PoliticlApp
     * <p>
     * This is ok, since android treats it as a singleton anyway.
     */
    public static PoliticlApp getInstance() {
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpConnectionFactory okHttpConnectionFactory = new OkHttpConnectionFactory(this);

        ImagePipelineConfig config = OkHttpImagePipelineConfigFactory
                .newBuilder(this, okHttpConnectionFactory.client())
                .build();
        Fresco.initialize(this, config);
    }

    public boolean isProdRelease() {
        return ReleaseUtil.isProdRelease();
    }

    /**
     * Get this app's unique install ID, which is a UUID that should be unique for each install
     * of the app. Useful for anonymous analytics.
     *
     * @return Unique install ID for this app.
     */
    public String getAppInstallID() {
        String id = Prefs.getAppInstallId();
        if (id == null) {
            id = UUID.randomUUID().toString();
            Prefs.setAppInstallId(id);
        }
        return id;
    }

    public boolean isDevRelease() {
        return ReleaseUtil.isDevRelease();
    }

    /**
     * Default site for the app
     * You should use PageTitle.getSite() to get the article site
     */
    @NonNull
    public Site getSite() {
        // TODO: why don't we ensure that the app language hasn't changed here instead of the client?
        if (site == null) {
//            String lang = Prefs.getMediaWikiBaseUriSupportsLangCode() ? getAppOrSystemLanguageCode() : "";
            site = Site.forLanguageCode("");
        }
        return site;
    }

}
