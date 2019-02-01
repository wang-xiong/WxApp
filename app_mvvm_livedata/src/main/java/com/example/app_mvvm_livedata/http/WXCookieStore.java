package com.example.app_mvvm_livedata.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.internal.http.HttpDate;

public class WXCookieStore {

    private final SharedPreferences cookiePrefs;
    private static final String COOKIE_PREFS = "Cookies_Prefs";
    private static WXCookieStore mInstance;
    private final Map<String, ConcurrentHashMap<String, Cookie>> cookies = new HashMap<>();

    public WXCookieStore(Context context) {
        cookiePrefs = context.getSharedPreferences(COOKIE_PREFS, Context.MODE_PRIVATE);

        //将持久化的cookies缓存到内存中 即map cookies
        Map<String, ?> prefsMap = cookiePrefs.getAll();
        for (Map.Entry<String, ?> entry : prefsMap.entrySet()) {
            String[] cookieNames = TextUtils.split((String) entry.getValue(), ",");
            for (String name : cookieNames) {
                String encodedCookie = cookiePrefs.getString(name, null);
                if (encodedCookie != null) {
                    Cookie decodedCookie = decodeCookie(encodedCookie);
                    if (decodedCookie != null) {
                        if (!cookies.containsKey(entry.getKey())) {
                            cookies.put(entry.getKey(), new ConcurrentHashMap<String, Cookie>());
                        }
                        cookies.get(entry.getKey()).put(name, decodedCookie);
                    }
                }
            }
        }
    }


    public static WXCookieStore getInstance(Context context) {
        if (mInstance == null) {
            synchronized (WXCookieStore.class) {
                if (mInstance == null) {
                    mInstance = new WXCookieStore(context);
                }
            }
        }
        return mInstance;
    }

    public List<Cookie> get(HttpUrl url) {
        List<Cookie> cookieList = new ArrayList<>();
        if (cookies.containsKey(url.host())) {
            cookieList.addAll(cookies.get(url.host()).values());
        }
        return cookieList;
    }

    public void add(HttpUrl httpUrl, Cookie cookie) {
        String name = getCookieToken(cookie);
        if (cookies.containsKey(httpUrl.host())) {
            cookies.get(httpUrl.host()).put(name, cookie);
            //本地判断cookie是否过期,如果过期，就重置
            if (cookie.persistent() && (cookie.expiresAt() >= HttpDate.MAX_DATE)) {
                cookies.remove(httpUrl.host());
            }
        } else {
            cookies.put(httpUrl.host(), new ConcurrentHashMap<String, Cookie>());
        }

        //将cookies持久化到本地
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        if (cookies.containsKey(httpUrl.host())) {
            prefsWriter.putString(httpUrl.host(), TextUtils.join(",", cookies.get(httpUrl.host()).keySet()));
            prefsWriter.putString(name, encodeCookie(new SerializableOkHttpCookies(cookie)));
            prefsWriter.apply();
        }
    }

    private String getCookieToken(Cookie cookie) {
        return cookie.name() + "@" + cookie.domain();
    }

    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    private String encodeCookie(SerializableOkHttpCookies cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((SerializableOkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookie;
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    private byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }

}
