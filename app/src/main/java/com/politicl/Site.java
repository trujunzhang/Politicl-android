package com.politicl;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.politicl.settings.Prefs;

import java.util.Locale;

/**
 * The base URL and Wikipedia language code for a wiki site. Examples:
 * <p>
 * <ul>
 * <lh>Name: scheme / authority / language code</lh>
 * <li>English Wikipedia: HTTPS / en.wikipedia.org / en</li>
 * <li>Chinese Wikipedia: HTTPS / zh.wikipedia.org / zh-hans or zh-hant</li>
 * <li>Meta-Wiki: HTTPS / meta.wikimedia.org / (none)</li>
 * <li>Test Wikipedia: HTTPS / test.wikipedia.org / test</li>
 * <li>Võro Wikipedia: HTTPS / fiu-vro.wikipedia.org / fiu-vro</li>
 * <li>Simple English Wikipedia: HTTPS / simple.wikipedia.org / simple</li>
 * <li>Simple English Wikipedia (beta cluster mirror): HTTP / simple.wikipedia.beta.wmflabs.org / simple</li>
 * <li>Development: HTTP / 192.168.1.11:8080 / (none)</li>
 * </ul>
 */
public class Site implements Parcelable {
    public static final Creator<Site> CREATOR = new Creator<Site>() {
        @Override
        public Site createFromParcel(Parcel in) {
            return new Site(in);
        }

        @Override
        public Site[] newArray(int size) {
            return new Site[size];
        }
    };

    @SerializedName("domain")
    @NonNull
    private final Uri uri;
    @NonNull
    private final String languageCode; // possibly empty

    public static Site forLanguageCode(@NonNull String languageCode) {
        Uri uri = Prefs.getMediaWikiBaseUri();
        boolean secureSchema = uri.getScheme().equals("https");
        return new Site(secureSchema,
                "" + uri.getAuthority(),
                languageCode);
    }

    /**
     * This method cannot resolve multi-dialect wikis like Simplified and Traditional Chinese.
     */
    public Site(@NonNull String authority) {
        this(authority, authorityToLanguageCode(authority));
    }

    public Site(@NonNull String authority, @NonNull String languageCode) {
        this(true, authority, languageCode);
    }

    public Site(boolean secureScheme, @NonNull String authority, @NonNull String languageCode) {
        this(new Uri.Builder()
                .scheme(secureScheme ? "https" : "http")
                // TODO: verify no one is passing in mobile authorities and remove authorityToDesktop().
                .encodedAuthority(authorityToDesktop(authority))
                .build(), languageCode);
    }

    public Site(@NonNull Parcel in) {
        this(in.<Uri>readParcelable(Uri.class.getClassLoader()), in.readString());
    }

    public Site(@NonNull Uri uri, @NonNull String languageCode) {
        this.uri = uri;
        this.languageCode = languageCode;
    }

    /**
     * @return True if the URL scheme is secure. Examples:
     * <p>
     * <ul>
     * <lh>Scheme: return value</lh>
     * <li>HTTPS: true</li>
     * <li>HTTP: false</li>
     * </ul>
     */
    public boolean secureScheme() {
        return uri.getScheme().equals("https");
    }

    @NonNull
    public String scheme() {
        return uri.getScheme();
    }

    /**
     * @return The complete wiki authority including language subdomain but not including scheme,
     * authentication, port, nor trailing slash.
     * @see <a href='https://en.wikipedia.org/wiki/Uniform_Resource_Locator#Syntax'>URL syntax</a>
     */
    @NonNull
    public String authority() {
        return uri.getAuthority();
    }

    @NonNull
    public String mobileAuthority() {
        return authorityToMobile(authority());
    }

    @NonNull
    public String host() {
        return uri.getHost();
    }

    /**
     * Like {@link #host} but with a "m." between the language subdomain and the rest of the host.
     * Examples:
     * <p>
     * <ul>
     * <li>English Wikipedia: en.m.wikipedia.org</li>
     * <li>Chinese Wikipedia: zh.m.wikipedia.org</li>
     * <li>Meta-Wiki: meta.m.wikimedia.org</li>
     * <li>Test Wikipedia: test.m.wikipedia.org</li>
     * <li>Võro Wikipedia: fiu-vro.m.wikipedia.org</li>
     * <li>Simple English Wikipedia: simple.m.wikipedia.org</li>
     * <li>Simple English Wikipedia (beta cluster mirror): simple.m.wikipedia.beta.wmflabs.org</li>
     * <li>Development: m.192.168.1.11:8080</li>
     * </ul>
     */
    @NonNull
    public String mobileHost() {
        return authorityToMobile(host());
    }

    public int port() {
        return uri.getPort();
    }

    /**
     * @return A path without an authority for the segment including a leading "/".
     */
    @NonNull
    public String path(@NonNull String segment) {
        return "/w/" + segment;
    }

    /**
     * @return The canonical URL. e.g., https://en.wikipedia.org.
     */
    public String url() {
        return scheme() + "://" + authority();
    }

    /**
     * @return The canonical URL for segment. e.g., https://en.wikipedia.org/w/foo.
     */
    public String url(@NonNull String segment) {
        return url() + path(segment);
    }

    // Auto-generated
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Site site = (Site) o;

        if (!uri.equals(site.uri)) {
            return false;
        }
        return languageCode.equals(site.languageCode);
    }

    // Auto-generated
    @Override
    public int hashCode() {
        int result = uri.hashCode();
        result = 31 * result + languageCode.hashCode();
        return result;
    }

    // Auto-generated
    @Override
    public String toString() {
        return "Site{"
                + "uri=" + uri
                + ", languageCode='" + languageCode + '\''
                + '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeParcelable(uri, 0);
        dest.writeString(languageCode);
    }

    @NonNull
    private static String languageCodeToSubdomain(@NonNull String languageCode) {
        return "";
    }

    @NonNull
    private static String authorityToLanguageCode(@NonNull String authority) {
        return authority.split("\\.")[0];
    }

    @NonNull
    private static String authorityToDesktop(@NonNull String authority) {
        return authority.replaceFirst("\\.m\\.", ".");
    }

    /**
     * @param authority Host and optional port.
     */
    @NonNull
    private String authorityToMobile(@NonNull String authority) {
        String subdomain = languageCodeToSubdomain(languageCode);
        return authority.replaceFirst("^" + subdomain + "\\.?", "$0m.");
    }
}
