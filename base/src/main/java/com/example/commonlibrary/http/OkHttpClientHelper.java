package com.example.commonlibrary.http;


import com.example.commonlibrary.BuildConfig;
import com.example.commonlibrary.ContentValue;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by zlc on 2016/10/19.
 * Okhttp帮助类
 */

public class OkHttpClientHelper {

    private final Cache cache;
    private OkHttpClient mClient;
    private final static long TIMEOUT = 5000;  //超时时间

    private OkHttpClientHelper() {

        cache = CacheHelper.getInstance().getCache();
    }

    private static OkHttpClientHelper clientHelper;

    public static OkHttpClientHelper getInstance() {
        if (clientHelper == null) {
            synchronized (OkHttpClientHelper.class) {
                if (clientHelper == null) {
                    clientHelper = new OkHttpClientHelper();
                }
            }
        }
        return clientHelper;
    }

//    final static  X509TrustManager trustManager = new X509TrustManager() {
//        @Override
//        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//        }
//
//        @Override
//        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//
//        }
//
//        @Override
//        public X509Certificate[] getAcceptedIssuers() {
//            return new X509Certificate[0];
//        }
//    };

    //获取OKHttpClicent对象
    public OkHttpClient getOkHttpClient() {
        if (mClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }


//            SSLContext sslContext = null;
//            SSLSocketFactory sslSocketFactory;
//            try {
//                sslContext = SSLContext.getInstance("SSL");
//                sslContext.init(, new TrustManager[]{trustManager}, new SecureRandom());
//                sslSocketFactory = sslContext.getSocketFactory();
//            } catch (NoSuchAlgorithmException e) {
//                e.printStackTrace();
//            } catch (KeyManagementException e) {
//                e.printStackTrace();
//            }

            mClient = builder.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
//                    .sslSocketFactory(sslSocketFactory, trustManager).hostnameVerifier(new HostnameVerifier() {
//                        @Override
//                        public boolean verify(String hostname, SSLSession session) {
//                            return true;
//                        }
//                    })
                  .cache(cache)
//                    .cookieJar(new CookieJar() {
//                        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<String, List<Cookie>>();
//                        @Override
//                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//                            if(cookies.size()>=3){
//                                cookieStore.put(ContentValue.BASE_URL, cookies);
//                            }
//                        }
//                        @Override
//                        public List<Cookie> loadForRequest(HttpUrl url) {
//                            List<Cookie> cookies = new ArrayList<Cookie>();
//                            if (cookieStore != null && cookieStore.get(ContentValue.BASE_URL) != null){
//                                cookies = cookieStore.get(ContentValue.BASE_URL);
//                            }
//                            return cookies;
//                        }
//                    })      //设置缓存
                    .build();
        }
        return mClient;
    }
}