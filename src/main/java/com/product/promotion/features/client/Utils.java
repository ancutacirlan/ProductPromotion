package com.product.promotion.features.client;

import org.modelmapper.PropertyMap;

public class Utils {

    private Utils() {
    }

    public static PropertyMap<ClientDto, Client> clientMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().getLocation_id().setId(source.getLocation_id());
        }
    };

    public static PropertyMap<Client, ClientDto> clientFieldMapping = new PropertyMap<>() {
        @Override
        protected void configure() {
            map().setLocation_id(source.getLocation_id().getId());
            map().setCity(source.getLocation_id().getCity());
            map().setDistrict(source.getLocation_id().getDistrict());
            map().setCountry(source.getLocation_id().getCountry());
            map().setNo(source.getLocation_id().getNo());
            map().setStreet(source.getLocation_id().getStreet());
            map().setVillage(source.getLocation_id().getVillage());
            map().setBloc(source.getLocation_id().getBloc());
            map().setStair(source.getLocation_id().getStair());
        }
    };

}
