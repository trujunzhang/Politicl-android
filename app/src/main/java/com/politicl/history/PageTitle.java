package com.politicl.history;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.politicl.Site;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;


/**
 * Represents certain vital information about a page, including the title, namespace,
 * and fragment (section anchor target).  It can also contain a thumbnail URL for the
 * page, and a short description retrieved from Wikidata.
 * <p>
 * WARNING: This class is not immutable! Specifically, the thumbnail URL and the Wikidata
 * description can be altered after construction. Therefore do NOT rely on all the fields
 * of a PageTitle to remain constant for the lifetime of the object.
 */
public class PageTitle implements Parcelable {
    private static final String LANGUAGE_CODE_KEY = "languageCode";

    /**
     * The localised namespace of the page as a string, or null if the page is in mainspace.
     * <p>
     * This field contains the prefix of the page's title, as opposed to the namespace ID used by
     * MediaWiki. Therefore, mainspace pages always have a null namespace, as they have no prefix,
     * and the namespace of a page will depend on the language of the wiki the user is currently
     * looking at.
     * <p>
     * Examples:
     * * [[Manchester]] on enwiki will have a namespace of null
     * * [[Deutschland]] on dewiki will have a namespace of null
     * * [[User:Deskana]] on enwiki will have a namespace of "User"
     * * [[Utilisateur:Deskana]] on frwiki will have a namespace of "Utilisateur", even if you got
     * to the page by going to [[User:Deskana]] and having MediaWiki automatically redirect you.
     */
    // TODO: remove. This legacy code is the localized namespace name (File, Special, Talk, etc) but
    //       isn't consistent across titles. e.g., articles with colons, such as RTÉ News: Six One,
    //       are broken.
    @NonNull
    private final String text;
    @Nullable
    private String thumbUrl;
    @Nullable
    private String description;

    public PageTitle(@Nullable String text) {
        this(text, null, null);
    }

    public PageTitle(@Nullable String text, @Nullable String thumbUrl, @Nullable String description) {
        this.text = text;
        this.thumbUrl = thumbUrl;
        this.description = description;
    }

    public String getText() {
        return text.replace(" ", "_");
    }

    @Nullable
    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(@Nullable String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PageTitle> CREATOR
            = new Creator<PageTitle>() {
        @Override
        public PageTitle createFromParcel(Parcel in) {
            return new PageTitle(in);
        }

        @Override
        public PageTitle[] newArray(int size) {
            return new PageTitle[size];
        }
    };

    private PageTitle(Parcel in) {
//        namespace = in.readString();
        text = in.readString();
//        fragment = in.readString();
//        site = in.readParcelable(Site.class.getClassLoader());
//        properties = in.readParcelable(PageProperties.class.getClassLoader());
//        thumbUrl = in.readString();
//        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
//        parcel.writeString(namespace);
        parcel.writeString(text);
//        parcel.writeString(fragment);
//        parcel.writeParcelable(site, flags);
//        parcel.writeParcelable(properties, flags);
//        parcel.writeString(thumbUrl);
//        parcel.writeString(description);
    }


}
