package com.example.app_mvvm_livedata.http;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class WXSSlSocketFactory {

    private SSLSocketFactory mSslSocketFactory;
    private X509TrustManager mTrustManager;
    private Context mContext;
    private boolean mCaptureAble; //能否抓包，true为可以抓包；false为不能抓包

    public WXSSlSocketFactory(Context context) {
        this(context, false);
    }

    public WXSSlSocketFactory(Context context, boolean captureAble) {
        this.mContext = context.getApplicationContext();
        this.mCaptureAble = captureAble;
        initData();
    }

    private void initData() {
        if (mCaptureAble) {
            initCustomSSLSocketFactory();
        } else {
            initDefaultSSLSocketFactory();
        }
    }

    private void initCustomSSLSocketFactory() {
        InputStream rootCerInputStream = null;
        try {
            //服务端的证书验证
            rootCerInputStream = mContext.getAssets().open("wx_server.crt");
            //构造CertificateFactory对象
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //通过certificateFactory对象获得Certificate对象
            Certificate certificate = certificateFactory.generateCertificate(rootCerInputStream);
            //创建KeyStore对象
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            //将Certificate对象存到KeyStore对象中
            keyStore.setCertificateEntry("wx_server.cer", certificate);

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            //利用KeyStore初始化TrustManagerFactory对象
            trustManagerFactory.init(keyStore);

            //通过TrustManagerFactory对象获得trustManagers；
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (1 != trustManagers.length || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            //java平台添加客户端的证书
            //KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //clientKeyStore.load(application.getApplicationContext().getAssets().open("wx_client.jks"), "123456".toCharArray());
            //KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //keyManagerFactory.init(keyStore, "123456".toCharArray());
            //KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            //Android添加客户端的证书
            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(mContext.getAssets().open("wx_client.bks"), "123456".toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, "123456".toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            //构造SSLContext对象
            SSLContext sslContext = SSLContext.getInstance("TLS");
            //利用trustManagers初始化SSLContext对象
            sslContext.init(keyManagers, trustManagers, new SecureRandom());
            //得到我们需要的SSLSocketFactory
            mSslSocketFactory = sslContext.getSocketFactory();
            //得到我们需要的X509TrustManager对象
            mTrustManager = (X509TrustManager) trustManagers[0];

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (rootCerInputStream != null) {
                try {
                    rootCerInputStream.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void initDefaultSSLSocketFactory() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (1 != trustManagers.length || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            SSLContext sslDefaultContext = SSLContext.getInstance("TLS");
            sslDefaultContext.init(null, trustManagers, null);
            mSslSocketFactory = sslDefaultContext.getSocketFactory();
            mTrustManager = (X509TrustManager) trustManagers[0];
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // 获取这个SSLSocketFactory
    public SSLSocketFactory getSSLSocketFactory() {
        if (mSslSocketFactory == null) {
            initData();
        }
        return mSslSocketFactory;
    }

    // 获取TrustManager
    public X509TrustManager getTrustManager() {
        if (mTrustManager == null) {
            initData();
        }
        return mTrustManager;
    }
}
