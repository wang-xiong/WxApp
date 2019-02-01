package com.example.app_mvvm_livedata.http;

import android.text.TextUtils;

import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.auth.x500.X500Principal;

import static okhttp3.internal.Util.verifyAsIpAddress;

public class WXHostnameVerifier implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;

    private final String host;

    public WXHostnameVerifier(String host) {
        this.host = host;
    }

    @Override
    public boolean verify(String hostname, SSLSession session) {
        try {
            Certificate[] certificates = session.getPeerCertificates();
            X509Certificate x509Certificate = (X509Certificate) certificates[0];
            if (verifyAsIpAddress(host)) {
                return verifyIpAddress(host, x509Certificate);
            } else {
                return verifyHostname(host, x509Certificate);
            }

        } catch (SSLPeerUnverifiedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (TextUtils.isEmpty(host) || !(obj instanceof WXHostnameVerifier)) {
            return false;
        }
        String thatHost = ((WXHostnameVerifier) obj).host;
        if (TextUtils.isEmpty(thatHost)) {
            return false;
        }
        return host.equals(thatHost);
    }

    private boolean verifyIpAddress(String ipAddress, X509Certificate certificate) {
        List<String> altNames = getSubjectAltNames(certificate, ALT_IPA_NAME);
        for (int i = 0, size = altNames.size(); i < size; i++) {
            if (ipAddress.equalsIgnoreCase(altNames.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyHostname(String hostname, X509Certificate certificate) {
        hostname = hostname.toLowerCase(Locale.US);
        boolean hasDns = false;
        List<String> altNames = getSubjectAltNames(certificate, ALT_DNS_NAME);
        for (int i = 0, size = altNames.size(); i < size; i++) {
            hasDns = true;
            if (verifyHostname(hostname, altNames.get(i))) {
                return true;
            }
        }

        if (!hasDns) {
            X500Principal principal = certificate.getSubjectX500Principal();
            // RFC 2818 advises using the most specific name for matching.
            String cn = new WXDistinguishedNameParser(principal).findMostSpecific("cn");
            if (cn != null) {
                return verifyHostname(hostname, cn);
            }
        }

        return false;
    }

    private static List<String> getSubjectAltNames(X509Certificate certificate, int type) {
        List<String> result = new ArrayList<>();
        try {
            Collection<?> subjectAltNames = certificate.getSubjectAlternativeNames();
            if (subjectAltNames == null) {
                return Collections.emptyList();
            }
            for (Object subjectAltName : subjectAltNames) {
                List<?> entry = (List<?>) subjectAltName;
                if (entry == null || entry.size() < 2) {
                    continue;
                }
                Integer altNameType = (Integer) entry.get(0);
                if (altNameType == null) {
                    continue;
                }
                if (altNameType == type) {
                    String altName = (String) entry.get(1);
                    if (altName != null) {
                        result.add(altName);
                    }
                }
            }
            return result;
        } catch (CertificateParsingException e) {
            return Collections.emptyList();
        }
    }

    private boolean verifyHostname(String hostname, String pattern) {
        // Basic sanity checks
        // Check length == 0 instead of .isEmpty() to support Java 5.
        if ((hostname == null) || (hostname.length() == 0) || (hostname.startsWith("."))
                || (hostname.endsWith(".."))) {
            // Invalid domain name
            return false;
        }
        if ((pattern == null) || (pattern.length() == 0) || (pattern.startsWith("."))
                || (pattern.endsWith(".."))) {
            // Invalid pattern/domain name
            return false;
        }

        // Normalize hostname and pattern by turning them into absolute domain names if they are not
        // yet absolute. This is needed because server certificates do not normally contain absolute
        // names or patterns, but they should be treated as absolute. At the same time, any hostname
        // presented to this method should also be treated as absolute for the purposes of matching
        // to the server certificate.
        //   www.android.com  matches www.android.com
        //   www.android.com  matches www.android.com.
        //   www.android.com. matches www.android.com.
        //   www.android.com. matches www.android.com
        if (!hostname.endsWith(".")) {
            hostname += '.';
        }
        if (!pattern.endsWith(".")) {
            pattern += '.';
        }
        // hostname and pattern are now absolute domain names.

        pattern = pattern.toLowerCase(Locale.US);
        // hostname and pattern are now in lower case -- domain names are case-insensitive.

        if (!pattern.contains("*")) {
            // Not a wildcard pattern -- hostname and pattern must match exactly.
            return hostname.equals(pattern);
        }
        // Wildcard pattern

        // WILDCARD PATTERN RULES:
        // 1. Asterisk (*) is only permitted in the left-most domain name label and must be the
        //    only character in that label (i.e., must match the whole left-most label).
        //    For example, *.example.com is permitted, while *a.example.com, a*.example.com,
        //    a*b.example.com, a.*.example.com are not permitted.
        // 2. Asterisk (*) cannot match across domain name labels.
        //    For example, *.example.com matches test.example.com but does not match
        //    sub.test.example.com.
        // 3. Wildcard patterns for single-label domain names are not permitted.

        if ((!pattern.startsWith("*.")) || (pattern.indexOf('*', 1) != -1)) {
            // Asterisk (*) is only permitted in the left-most domain name label and must be the only
            // character in that label
            return false;
        }

        // Optimization: check whether hostname is too short to match the pattern. hostName must be at
        // least as long as the pattern because asterisk must match the whole left-most label and
        // hostname starts with a non-empty label. Thus, asterisk has to match one or more characters.
        if (hostname.length() < pattern.length()) {
            // hostname too short to match the pattern.
            return false;
        }

        if ("*.".equals(pattern)) {
            // Wildcard pattern for single-label domain name -- not permitted.
            return false;
        }

        // hostname must end with the region of pattern following the asterisk.
        String suffix = pattern.substring(1);
        if (!hostname.endsWith(suffix)) {
            // hostname does not end with the suffix
            return false;
        }

        // Check that asterisk did not match across domain name labels.
        int suffixStartIndexInHostname = hostname.length() - suffix.length();
        if ((suffixStartIndexInHostname > 0)
                && (hostname.lastIndexOf('.', suffixStartIndexInHostname - 1) != -1)) {
            // Asterisk is matching across domain name labels -- not permitted.
            return false;
        }

        // hostname matches pattern
        return true;
    }

}
