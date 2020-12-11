package com.product.promotion.features.client;

import org.modelmapper.PropertyMap;

public class Utils {

    private Utils() {
    }

    public static PropertyMap<ClientDto, Client> clientMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getLocationId().setId(source.getLocationId());
        }
    };

    public static PropertyMap<Client, ClientDto> clientFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setLocationId(source.getLocationId().getId());
            map().setCity(source.getLocationId().getCity());
            map().setDistrict(source.getLocationId().getDistrict());
            map().setCountry(source.getLocationId().getCountry());
            map().setNo(source.getLocationId().getNo());
            map().setStreet(source.getLocationId().getStreet());
            map().setVillage(source.getLocationId().getVillage());
            map().setBloc(source.getLocationId().getBloc());
            map().setStair(source.getLocationId().getStair());
        }
    };

}
