package com.gmail.neooxpro.di.module;

import com.gmail.neooxpro.lib.BuildConfig;
import com.gmail.neooxpro.lib.network.directions.GoogleDirectionsApi;
import com.gmail.neooxpro.lib.network.geocode.YandexGeoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gmail.neooxpro.lib.network.directions.GoogleDirectionsApi.GOOGLE_BASE_URL;
import static com.gmail.neooxpro.lib.network.geocode.YandexGeoApi.GEO_BASE_URL;

@Module
public class NetworkModule {
    @Singleton
    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);
        return client.build();
    }

    @Singleton
    @Provides
    public YandexGeoApi provideGeoCodeApi(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GEO_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(YandexGeoApi.class);
    }

    @Singleton
    @Provides
    public GoogleDirectionsApi provideGoogleDirectionsApi(Gson gson, OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(GoogleDirectionsApi.class);
    }
}
