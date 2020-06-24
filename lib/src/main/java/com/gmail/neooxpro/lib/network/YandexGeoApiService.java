package com.gmail.neooxpro.lib.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class YandexGeoApiService {

    private static YandexGeoApiService mInstance;
    private static final String BASE_URL = "https://geocode-maps.yandex.ru/1.x/";
    private Retrofit mRetrofit;

    private YandexGeoApiService(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    private static YandexGeoApiService  getInstance(){
        if (mInstance == null){
            mInstance = new YandexGeoApiService();
        }
        return mInstance;
    }

    public JSONYandexGeoApi getJSONApi() {
        return mRetrofit.create(JSONYandexGeoApi.class);
    }


}
