package com.example.commonlibrary.http;


import com.example.commonlibrary.ContentValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zlc on 2016/10/19.
 * Retrofit帮助类
 */

public class RetrofitHelper {

    private  final OkHttpClient mClient;
    private volatile Retrofit mRetrofit;
    private static final Gson gson = new GsonBuilder().serializeNulls().create();

    private RetrofitHelper() {

        mClient = OkHttpClientHelper.getInstance().getOkHttpClient();
    }

    private static RetrofitHelper helper;

    //单例 保证对象唯一
    public static RetrofitHelper getInstance() {
        if (helper == null) {
            synchronized (RetrofitHelper.class) {
                if (helper == null) {
                    helper = new RetrofitHelper();
                }
            }
        }
        return helper;
    }

    //获取Retrofit对象
    public Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(ContentValue.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))  //添加Gson支持
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //添加RxJava支持
                    .client(mClient)                                            //关联okhttp
                    .build();
        }
        return mRetrofit;
    }

    //获取服务对象
    public static <T> T getService(Class<T> classz) {
        return RetrofitHelper.getInstance()
                .getRetrofit()
                .create(classz);
    }

//    private static class CustomGsonConverterFactory extends Converter.Factory {
//        private Gson gson;
//
//        public CustomGsonConverterFactory(Gson gson) {
//            this.gson = gson;
//        }
//
//        public static CustomGsonConverterFactory create(Gson gson) {
//            return new CustomGsonConverterFactory(gson);
//        }
//
//        @Override
//        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
//                                                                Retrofit retrofit) {
//            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//            return new CustomGsonResponseBodyConverter<>(gson, adapter);
//        }
//
//        @Override
//        public Converter<?, RequestBody> requestBodyConverter(Type type,
//                                                              Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
//            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
//            try {
//                Class<?> cls = Class.forName("retrofit2.converter.gson.GsonRequestBodyConverter");
//                Constructor constructor = cls.getDeclaredConstructor(gson.getClass(), adapter.getClass());
//                return (Converter<?, RequestBody>) constructor.newInstance(gson, adapter);
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } catch (NoSuchMethodException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (InvocationTargetException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }
//
//    private static class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
//        private final Gson gson;
//        private final TypeAdapter<T> adapter;
//
//        CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
//            this.gson = gson;
//            this.adapter = adapter;
//        }
//
//        @Override
//        public T convert(ResponseBody value) throws IOException, JsonParseException {
//            Reader reader = value.charStream();
//            JsonReader jsonReader = gson.newJsonReader(reader);
//
//            String jsonStr = jsonReader.toString();
//            JsonObject jsonObject = gson.getAdapter(JsonObject.class)
//                    .read(jsonReader);
//            try {
//               JsonReader jsonReader2 = gson.newJsonReader(
//                       new CharArrayReader(jsonObject.toString().toCharArray()));
//              return adapter.read(jsonReader2);
//            } finally {
//                value.close();
//            }
//        }
//    }
}
