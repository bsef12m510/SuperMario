package com.example.zeeshan.supermario.api;

import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ApiClient {

    public static final String BASE_URL = "https://segmentnext.com/wp-json/wp/v2/";
    public static final String BASE_URL2 = "http://sms.sslwireless.com/pushapi/dynamic/";
    //    public static final String BASE_URL = "http://192.168.1.2:43648/api/";
    private static Retrofit retrofit = null;
    private static Retrofit retrofit2 = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    /*.addInterceptor(new NetworkConnectionInterceptor() {
                        @Override
                        public boolean isInternetAvailable() {
                            return App.this.isInternetAvailable();
                        }

                        @Override
                        public void onInternetUnavailable() {
                            if (mInternetConnectionListener != null) {
                                mInternetConnectionListener.onInternetUnavailable();
                            }
                        }
                    })*/
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }

    public static Retrofit getClient2() {
        if (retrofit2==null) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5, TimeUnit.MINUTES)
                    .readTimeout(5, TimeUnit.MINUTES)
                    /*.addInterceptor(new NetworkConnectionInterceptor() {
                        @Override
                        public boolean isInternetAvailable() {
                            return App.this.isInternetAvailable();
                        }

                        @Override
                        public void onInternetUnavailable() {
                            if (mInternetConnectionListener != null) {
                                mInternetConnectionListener.onInternetUnavailable();
                            }
                        }
                    })*/
                    .build();
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BASE_URL2)
                    .addConverterFactory(SimpleXmlConverterFactory.createNonStrict(new Persister(new AnnotationStrategy())))
                    .client(okHttpClient)
                    .build();
        }
        return retrofit2;
    }


}


abstract class NetworkConnectionInterceptor implements Interceptor {

    public abstract boolean isInternetAvailable();

    public abstract void onInternetUnavailable();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isInternetAvailable()) {
            onInternetUnavailable();
        }
        return chain.proceed(request);
    }
}

