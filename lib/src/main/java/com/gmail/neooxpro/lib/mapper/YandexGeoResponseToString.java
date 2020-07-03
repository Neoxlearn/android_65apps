package com.gmail.neooxpro.lib.mapper;

import com.gmail.neooxpro.lib.network.geocode.YandexGeoResponse;

public class YandexGeoResponseToString implements Mapper<YandexGeoResponse, String> {

    @Override
    public String map(YandexGeoResponse response) {
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
        }
        catch (IndexOutOfBoundsException e){
            return "";
        }
    }
}
