package com.aceplus.aceplusqrscanner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Dell on 12/10/2016.
 */

public class ApiServiceFactory {

    //public static String URL = "http://128.199.226.122:8082/api/";//test server
    public static String URL = "https://www.japanjobfair.com.mm/api/";//live server
    //public static String URL = "http://192.168.7.138:9999/api/";//hkl
    public static int timeOut = 60;
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().readTimeout(timeOut, TimeUnit.SECONDS).writeTimeout(timeOut, TimeUnit.SECONDS).connectTimeout(timeOut, TimeUnit.SECONDS);
    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <T> T createService(Class<T> serviceClass) {

        if(!httpClient.interceptors().isEmpty()) {
            httpClient.interceptors().clear();
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        httpClient.addInterceptor(loggingInterceptor);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request origial = chain.request();
                Request.Builder requstbuilder = origial.newBuilder();

                Request request = requstbuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

}
