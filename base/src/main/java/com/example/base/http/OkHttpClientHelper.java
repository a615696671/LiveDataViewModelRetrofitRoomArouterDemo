package com.example.base.http;


import okhttp3.OkHttpClient;

/**
 * Created by zlc on 2016/10/19.
 * Okhttp帮助类
 */

public class OkHttpClientHelper {


    public OkHttpClient.Builder builder;

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
    public OkHttpClient.Builder getOkHttpClientBuilder() {
        if(builder==null){
            builder = new OkHttpClient.Builder();
        }

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
        return builder;
    }


    public OkHttpClient getOkHttpClient(){
        return builder.build();
    }
}
