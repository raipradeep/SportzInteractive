package com.tw.sportstest.retrofitClient;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit = null;
    private final com.tw.sportstest.retrofitClient.ApiService ApiService;

    public ApiClient(ApiService apiService) {
        this.ApiService = apiService;
    }

    public static ApiService getInterface() {
        return getClient().create(ApiService.class);
    }

    private static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl("https://demo.sportz.io/")
                    //.client(getHttpLoggingInterceptor().build())
                    .build();
        }
        return retrofit;
    }

}
