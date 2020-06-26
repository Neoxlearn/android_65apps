package com.gmail.neooxpro.lib.mapper;

import com.gmail.neooxpro.lib.network.YandexGeoResponse;

import javax.inject.Inject;

public class YandexGeoResponseToString implements Mapper<YandexGeoResponse,String> {
    @Override

    public String map(YandexGeoResponse response) {
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
                //.getAddressLine();
    }
}
