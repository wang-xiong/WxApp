package com.example.app_mvvm_livedata.http;

import java.net.CookiePolicy;
import java.net.URI;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class WXCookieManager {

    private WXCookieStore cookieStore;

    private CookiePolicy cookiePolicy;

    /**
     * Constructs a new cookie manager using a specified cookie store and a
     * cookie policy.
     *
     * @param cookieStore  a CookieStore to be used by cookie manager. The manager will
     *                     use a default one if the arg is null.
     * @param cookiePolicy a CookiePolicy to be used by cookie manager
     *                     ACCEPT_ORIGINAL_SERVER will be used if the arg is null.
     */
    public WXCookieManager(WXCookieStore cookieStore, CookiePolicy cookiePolicy) {
        this.cookieStore = cookieStore;
        this.cookiePolicy = cookiePolicy == null ? CookiePolicy.ACCEPT_ORIGINAL_SERVER : cookiePolicy;
    }

    /**
     * Sets cookies according to uri and responseHeaders
     *
     * @param uri     the specified uri
     * @param cookies a list of request headers
     */
    public void put(URI uri, List<Cookie> cookies) {
        if (uri == null || cookies == null) {
            return;
        }
        // parse and construct cookies according to the map
        for (Cookie cookie : cookies) {
            cookieStore.add(HttpUrl.get(uri), cookie);
        }
    }

    /**
     * Searches and gets all cookies in the cache by the specified uri in the
     * request header.
     *
     * @param uri the specified uri to search for
     *            <p/>
     *            a list of request headers
     * @return a map that record all such cookies, the map is unchangeable
     */

    public List<Cookie> get(URI uri) {
        if (uri == null) {
            return null;
        }
        return cookieStore.get(HttpUrl.get(uri));
    }
}
