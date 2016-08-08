package com.politicl;

import android.content.Context;
import android.support.annotation.NonNull;
import com.github.kevinsawicki.http.HttpRequest;
import com.politicl.settings.Prefs;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;

import okhttp3.Cache;
import okhttp3.CookieJar;
import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;
import okhttp3.logging.HttpLoggingInterceptor;

public class OkHttpConnectionFactory implements HttpRequest.ConnectionFactory {
    private static final long HTTP_CACHE_SIZE = 16 * 1024 * 1024;
    private static final Cache HTTP_CACHE = new Cache(PoliticlApp.getInstance().getCacheDir(), HTTP_CACHE_SIZE);

    private final OkHttpClient client;

    public OkHttpConnectionFactory(@NonNull Context context) {
        client = createClient(context).build();
    }

    @Override
    public HttpURLConnection create(URL url) throws IOException {
        return new OkUrlFactory(client).open(url); // TODO: update to newer API
    }

    @Override
    public HttpURLConnection create(URL url, Proxy proxy) throws IOException {
        throw new UnsupportedOperationException(
                "Per-connection proxy is not supported. Use OkHttpClient's setProxy instead.");
    }

    public static OkHttpClient.Builder createClient(@NonNull Context context) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(Prefs.getRetrofitLogLevel());

        // TODO: switch to using a single instance of OkHttpClient throughout the app.
        return new OkHttpClient.Builder()
                .cache(HTTP_CACHE)
                .addInterceptor(loggingInterceptor);
    }

    public OkHttpClient client() {
        return client;
    }
}
