package com.gmail.neooxpro.lib.mapper;

import androidx.annotation.NonNull;

import com.gmail.neooxpro.lib.network.geocode.YandexGeoResponse;

public class YandexGeoResponseToString implements Mapper<YandexGeoResponse, String> {

    @NonNull
    @Override
    public String map(@NonNull YandexGeoResponse response) {
        try {
            return response
                    .getResponse()
                    .getGeoObjectCollection()
                    .getFeatureMember()
                    .get(0)
                    .getGeoObject()
                    .getMetaDataProperty()
                    .getGeocoderMetaData()
                    .getAddress()
                    .getFormatted();
        } catch (IndexOutOfBoundsException e) {
            return "";
        }
    }
}
